/*
 * Copyright (C) 2016 Gson Type Adapter Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

/*
 * Imported from https://github.com/google-gson/typeadapters/tree/master/jsr310/src
 * and repackaged to avoid the default package.
 */
package org.syphr.sleepiq.api.impl.typeadapters;

import java.time.Year;

/**
 * Type adapter for jsr310 {@link Year} class.
 *
 * @author Christophe Bornet
 */
public class YearTypeAdapter extends TemporalTypeAdapter<Year> {

  public YearTypeAdapter() {
    super(Year::parse);
  }
}
