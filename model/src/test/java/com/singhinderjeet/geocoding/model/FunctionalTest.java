/*
 * Copyright (C) 2015 Inderjeet Singh
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
package com.singhinderjeet.geocoding.model;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;

import org.junit.Test;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

/**
 * Functional tests for Geocoding and Reverse geocoding model classes.
 * Validates if the classes can be serialized/deserialized to JSON.
 *
 * @author Inderjeet Singh
 */
public class FunctionalTest {
  private final Gson gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create();
  private final JsonParser parser = new JsonParser();

  @Test
  public void testGeocodingJson() throws Exception {
    validateGeocodingResponse("/geocoding-response-1.json");
    validateGeocodingResponse("/geocoding-response-2.json");
    validateGeocodingResponse("/geocoding-response-3.json");
    validateGeocodingResponse("/geocoding-response-4.json");
    validateGeocodingResponse("/geocoding-response-5.json");
    validateGeocodingResponse("/geocoding-response-6.json");
    validateGeocodingResponse("/geocoding-response-7.json");
    validateGeocodingResponse("/geocoding-response-8.json");
  }

  @Test
  public void testReverseGeocodingJson() throws Exception {
    validateReverseGeocodingResponse("/reverse-geocoding-response.json");
  }

  private void validateGeocodingResponse(String resourcePath) throws IOException {
    String inputJson = readResourceAsString(resourcePath);
    GeocodingResponse response = gson.fromJson(inputJson, GeocodingResponse.class);
    String outputJson = gson.toJson(response);
    assertEquals(parser.parse(inputJson), parser.parse(outputJson));
  }

  private void validateReverseGeocodingResponse(String resourcePath) throws IOException {
    String inputJson = readResourceAsString(resourcePath);
    ReverseGeocodingResponse response = gson.fromJson(inputJson, ReverseGeocodingResponse.class);
    String outputJson = gson.toJson(response);
    assertEquals(parser.parse(inputJson), parser.parse(outputJson));
  }

  private String readResourceAsString(String resourcePath) throws IOException {
    InputStream is = FunctionalTest.class.getResourceAsStream(resourcePath);
    StringWriter writer = new StringWriter();
    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
    while (reader.ready()) {
      writer.append(reader.readLine()).append("\n");
    }
    reader.close();
    writer.close();
    return writer.toString();
  }
}
