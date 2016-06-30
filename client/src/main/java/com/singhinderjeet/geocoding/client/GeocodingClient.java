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
import com.singhinderjeet.geocoding.model.Bounds;
import com.singhinderjeet.geocoding.model.GeocodingResponse;
import com.singhinderjeet.geocoding.model.Location;
import com.singhinderjeet.geocoding.model.Result;

/**
 * A client to access Geocoding API.
 *
 * @author Inderjeet Singh
 */
public class GeocodingClient {
  private static final Gson gson = new GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .setPrettyPrinting()
    .create();

  public static void main(String[] args) throws Exception {
    GeocodingClient client = new GeocodingClient();
    GeocodingResponse response = client.lookup("94043");
    System.out.println(gson.toJson(response));
  }

  public Location findRandomLatLong(String address) {
    try {
      return findRandomLatLong(lookup(address));
    } catch (Exception e) {
      return null;
    }
  }

  public Location findRandomLatLong(GeocodingResponse response) {
    try {
      Location location = null;
      for (Result result : response.getResults()) {
        if (result.getGeometry() == null || result.getGeometry().getBounds() == null) {
          continue;
        }
        Bounds bounds = result.getGeometry().getBounds();
        double latMax = bounds.getNortheast().getLat();
        double latMin = bounds.getSouthwest().getLat();

        double lngMax = bounds.getNortheast().getLng();
        double lngMin = bounds.getSouthwest().getLng();

        // Find a random location in the zipcode
        int scaleLat = Util.random.nextInt(100);
        int scaleLng = Util.random.nextInt(100);

        double lat = latMin + (scaleLat * (latMax-latMin))/100;
        double lng = lngMin + (scaleLng * (lngMax-lngMin))/100;

        location = new Location(lat, lng);
        break;
      }
      return location;
    } catch (Exception e) {
      return null;
    }
  }

  public GeocodingResponse lookup(String address) throws MalformedURLException, IOException {
    // Based on http://stackoverflow.com/questions/5585957/get-latlng-from-zip-code-google-maps-api
    // http://maps.googleapis.com/maps/api/geocode/json?address=95014

    URL url = new URL("http://maps.googleapis.com/maps/api/geocode/json?address=" + URLEncoder.encode(address, "UTF-8"));

    HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    try {
      InputStream in = url.openStream();
      String result = Util.readStream(in);
      return gson.fromJson(result, GeocodingResponse.class);
    } finally {
      urlConnection.disconnect();
    }
  }
}
