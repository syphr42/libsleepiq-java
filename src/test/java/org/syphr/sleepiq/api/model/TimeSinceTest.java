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
package org.syphr.sleepiq.api.model;

import static org.junit.Assert.assertEquals;

import java.time.Duration;

import org.junit.Test;

public class TimeSinceTest
{
    @Test
    public void testToString()
    {
        assertEquals("00:00:00", new TimeSince(Duration.parse("PT00H00M00S")).toString());
        assertEquals("02:03:04", new TimeSince(Duration.parse("PT02H03M04S")).toString());
        assertEquals("12:34:56", new TimeSince(Duration.parse("PT12H34M56S")).toString());
        assertEquals("1 d 02:03:04", new TimeSince(Duration.parse("P1DT02H03M04S")).toString());
        assertEquals("1 d 23:45:59", new TimeSince(Duration.parse("P1DT23H45M59S")).toString());
    }

    @Test
    public void testParse()
    {
        assertEquals(Duration.parse("PT00H00M00S"), TimeSince.parse("00:00:00").getDuration());
        assertEquals(Duration.parse("PT2H3M4S"), TimeSince.parse("02:03:04").getDuration());
        assertEquals(Duration.parse("PT12H34M56S"), TimeSince.parse("12:34:56").getDuration());
        assertEquals(Duration.parse("P1DT2H3M4S"), TimeSince.parse("1 d 02:03:04").getDuration());
        assertEquals(Duration.parse("P1DT23H45M59S"),
                     TimeSince.parse("1 d 23:45:59").getDuration());
    }
}
