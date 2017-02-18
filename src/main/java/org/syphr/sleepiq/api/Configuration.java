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
package org.syphr.sleepiq.api;

import java.net.URI;

public class Configuration
{
    private String username;
    private String password;

    private URI baseUri = URI.create("https://api.sleepiq.sleepnumber.com/rest");

    private boolean logging = false;
    private String logLevel = "INFO";
    private int logMax = 104857600; // 100kb

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public Configuration withUsername(String username)
    {
        setUsername(username);
        return this;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public Configuration withPassword(String password)
    {
        setPassword(password);
        return this;
    }

    public URI getBaseUri()
    {
        return baseUri;
    }

    public void setBaseUri(URI baseUri)
    {
        this.baseUri = baseUri;
    }

    public Configuration withBaseUri(URI baseUri)
    {
        setBaseUri(baseUri);
        return this;
    }

    public boolean isLogging()
    {
        return logging;
    }

    public void setLogging(boolean logging)
    {
        this.logging = logging;
    }

    public Configuration withLogging(boolean logging)
    {
        setLogging(logging);
        return this;
    }

    public String getLogLevel()
    {
        return logLevel;
    }

    public void setLogLevel(String logLevel)
    {
        this.logLevel = logLevel;
    }

    public Configuration withLogLevel(String logLevel)
    {
        setLogLevel(logLevel);
        return this;
    }

    public int getLogMax()
    {
        return logMax;
    }

    public void setLogMax(int logMax)
    {
        this.logMax = logMax;
    }

    public Configuration withLogMax(int logMax)
    {
        setLogMax(logMax);
        return this;
    }
}
