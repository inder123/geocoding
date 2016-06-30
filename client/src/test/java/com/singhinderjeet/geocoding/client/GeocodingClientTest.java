/*
 * Copyright (C) 2016 Inderjeet Singh
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
package com.singhinderjeet.geocoding.client;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.singhinderjeet.geocoding.model.GeocodingResponse;
import com.singhinderjeet.geocoding.model.Location;

public class GeocodingClientTest {

  @Test
  public void testZipCodeToLatLong() throws Exception {
    GeocodingClient client = new GeocodingClient();

    GeocodingResponse geocode = client.lookup("94043");
    Location location = client.findRandomLatLong(geocode);
    assertNotNull(location);
  }
}
