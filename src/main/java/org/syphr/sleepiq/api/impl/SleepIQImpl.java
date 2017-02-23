/*
 * Copyright 2017 Gregory Moyer
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.syphr.sleepiq.api.impl;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.ClientRequestContext;
import javax.ws.rs.client.ClientRequestFilter;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.StatusType;

import org.glassfish.jersey.filter.LoggingFilter;
import org.syphr.sleepiq.api.BedNotFoundException;
import org.syphr.sleepiq.api.Configuration;
import org.syphr.sleepiq.api.LoginException;
import org.syphr.sleepiq.api.SleepIQ;
import org.syphr.sleepiq.api.UnauthorizedException;
import org.syphr.sleepiq.api.model.Bed;
import org.syphr.sleepiq.api.model.BedsResponse;
import org.syphr.sleepiq.api.model.Failure;
import org.syphr.sleepiq.api.model.FamilyStatus;
import org.syphr.sleepiq.api.model.LoginInfo;
import org.syphr.sleepiq.api.model.LoginRequest;
import org.syphr.sleepiq.api.model.PauseMode;
import org.syphr.sleepiq.api.model.Sleeper;
import org.syphr.sleepiq.api.model.SleepersResponse;

import com.eclipsesource.jaxrs.provider.gson.GsonProvider;

public class SleepIQImpl extends AbstractClient implements SleepIQ
{
    private static final String PARAM_KEY = "_k";

    private final Configuration config;

    private volatile LoginInfo loginInfo;

    public SleepIQImpl(Configuration config)
    {
        this.config = config;
    }

    @Override
    public LoginInfo login() throws LoginException
    {
        if (loginInfo == null)
        {
            synchronized (this)
            {
                if (loginInfo == null)
                {
                    Response response = getClient().target(config.getBaseUri())
                                                   .path(Endpoints.login())
                                                   .request(MediaType.APPLICATION_JSON_TYPE)
                                                   .put(Entity.json(new LoginRequest().withLogin(config.getUsername())
                                                                                      .withPassword(config.getPassword())));

                    StatusType status = response.getStatusInfo();
                    if (Status.UNAUTHORIZED.getStatusCode() == status.getStatusCode())
                    {
                        throw new UnauthorizedException(response.readEntity(Failure.class));
                    }
                    if (!Status.Family.SUCCESSFUL.equals(status.getFamily()))
                    {
                        throw new LoginException(response.readEntity(Failure.class));
                    }

                    // add the received cookies to all future requests
                    getClient().register(new ClientRequestFilter()
                    {
                        @Override
                        public void filter(ClientRequestContext requestContext) throws IOException
                        {
                            List<Object> cookies = response.getCookies()
                                                           .values()
                                                           .stream()
                                                           .map(newCookie -> newCookie.toCookie())
                                                           .collect(Collectors.toList());
                            requestContext.getHeaders().put("Cookie", cookies);
                        }
                    });

                    loginInfo = response.readEntity(LoginInfo.class);
                }
            }
        }

        return loginInfo;
    }

    @Override
    public List<Bed> getBeds()
    {
        // TODO handle session timeout
        try
        {
            LoginInfo login = login();
            return getClient().target(config.getBaseUri())
                              .path(Endpoints.bed())
                              .queryParam(PARAM_KEY, login.getKey())
                              .request(MediaType.APPLICATION_JSON_TYPE)
                              .get(BedsResponse.class)
                              .getBeds();
        }
        catch (LoginException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public List<Sleeper> getSleepers()
    {
        // TODO handle session timeout
        try
        {
            LoginInfo login = login();
            return getClient().target(config.getBaseUri())
                              .path(Endpoints.sleeper())
                              .queryParam(PARAM_KEY, login.getKey())
                              .request(MediaType.APPLICATION_JSON_TYPE)
                              .get(SleepersResponse.class)
                              .getSleepers();
        }
        catch (LoginException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public FamilyStatus getFamilyStatus()
    {
        // TODO handle session timeout
        try
        {
            LoginInfo login = login();
            return getClient().target(config.getBaseUri())
                              .path(Endpoints.bed())
                              .path(Endpoints.familyStatus())
                              .queryParam(PARAM_KEY, login.getKey())
                              .request(MediaType.APPLICATION_JSON_TYPE)
                              .get(FamilyStatus.class);
        }
        catch (LoginException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public PauseMode getPauseMode(String bedId) throws BedNotFoundException
    {
        // TODO handle session timeout
        try
        {
            LoginInfo login = login();
            Response response = getClient().target(config.getBaseUri())
                                           .path(Endpoints.bed())
                                           .path(bedId)
                                           .path(Endpoints.pauseMode())
                                           .queryParam(PARAM_KEY, login.getKey())
                                           .request(MediaType.APPLICATION_JSON_TYPE)
                                           .get();

            if (!Status.Family.SUCCESSFUL.equals(response.getStatusInfo().getFamily()))
            {
                throw new BedNotFoundException(response.readEntity(Failure.class));
            }

            return response.readEntity(PauseMode.class);
        }
        catch (LoginException e)
        {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    protected Client createClient()
    {
        ClientBuilder builder = ClientBuilder.newBuilder();

        // setup Gson (de)serialization
        GsonProvider<Object> gsonProvider = new GsonProvider<>();
        gsonProvider.setGson(getGson());
        builder.register(gsonProvider);

        // turn on logging if requested
        if (config.isLogging())
        {
            builder.register(new LoggingFilter(Logger.getLogger(SleepIQImpl.class.getName()),
                                               config.getLogMax()));
        }

        return builder.build();
    }
}
