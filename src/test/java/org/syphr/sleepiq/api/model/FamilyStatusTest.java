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
import static org.junit.Assert.assertNotNull;

import java.io.FileReader;
import java.util.Arrays;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;
import org.syphr.sleepiq.api.impl.GsonGenerator;
import org.syphr.sleepiq.api.test.AbstractTest;

import com.google.gson.Gson;

public class FamilyStatusTest extends AbstractTest
{
    private static Gson gson;

    @BeforeClass
    public static void setUpBeforeClass()
    {
        gson = GsonGenerator.create(true);
    }

    @Test
    public void testSerializeAllFields() throws Exception
    {
        FamilyStatus familyStatus = new FamilyStatus().withBeds(Arrays.asList(new BedStatus().withStatus(1L)));
        assertEquals(readJson("family-status.json"), gson.toJson(familyStatus));
    }

    @Test
    public void testDeserializeAllFields() throws Exception
    {
        try (FileReader reader = new FileReader(getTestDataFile("family-status.json")))
        {
            FamilyStatus familyStatus = gson.fromJson(reader, FamilyStatus.class);

            List<BedStatus> beds = familyStatus.getBeds();
            assertNotNull(beds);
            assertEquals(1, beds.size());
            assertEquals(Long.valueOf(1L), beds.get(0).getStatus());
        }
    }
}
