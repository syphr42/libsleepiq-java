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

import java.util.List;

import org.syphr.sleepiq.api.impl.SleepIQImpl;
import org.syphr.sleepiq.api.model.Bed;
import org.syphr.sleepiq.api.model.Sleeper;

public interface SleepIQ
{
    public void login() throws LoginException;

    public List<Bed> getBeds();

    public List<Sleeper> getSleepers();

    //    public void getFamilyStatus();

    public static SleepIQ create(Configuration config)
    {
        return new SleepIQImpl(config);
    }
}
