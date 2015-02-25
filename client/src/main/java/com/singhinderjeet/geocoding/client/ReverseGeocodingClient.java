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
package com.singhinderjeet.geocoding.client;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.singhinderjeet.geocoding.model.Location;
import com.singhinderjeet.geocoding.model.ReverseGeocodingResponse;

/**
 * A client to access Reverse Geocoding API.
 *
 * @author Inderjeet Singh
 */
public class ReverseGeocodingClient {
  private static final Gson gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .setPrettyPrinting()
    .create();

  public static void main(String[] args) throws Exception {
    ReverseGeocodingClient client = new ReverseGeocodingClient();
    ReverseGeocodingResponse response = client.lookup(new Location(37.42156911970850, -122.0867701802915));
    System.out.println(gson.toJson(response));
  }

  private ReverseGeocodingResponse lookup(Location location)
      throws MalformedURLException, IOException {
    // Based on
    // https://developers.google.com/maps/documentation/geocoding/#ReverseGeocoding
    // Also on:
    // https://www.kerstner.at/en/2013/08/convert-gps-coordinates-to-address-using-google-geocoding-api-in-java/
    double lng = location.getLng();
    double lat = location.getLat();

    String query = "latlng=" + URLEncoder.encode(String.valueOf(lat) + "," + String.valueOf(lng), "UTF-8")
            + "&sensor=true";
    URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?" + query);

    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = url.openStream();
      String result = Util.readStream(in);
      return gson.fromJson(result, ReverseGeocodingResponse.class);
    } finally {
      urlConnection.disconnect();
    }
  }
}
