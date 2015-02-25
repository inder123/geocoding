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
package com.singhinderjeet.geocoding.generator;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.singhinderjeet.json2java.ClassDefCollection;
import com.singhinderjeet.json2java.CustomMappings;
import com.singhinderjeet.json2java.Json2Java;

public class GeocodingModelGenerator {

  public static void main(String[] args) throws Exception {
    File outputDir = new File("../model/src/main/java");
    String pkgName = "com.singhinderjeet.geocoding.model";
    String fileCopyrightNotice = readAsString(new InputStreamReader(
        GeocodingModelGenerator.class.getResourceAsStream("/class-file-copyright-notice.txt")));
    String defaultClassComment = readAsString(new InputStreamReader(
        GeocodingModelGenerator.class.getResourceAsStream("/default-class-comment.txt")));
    GeocodingModelGenerator generator = new GeocodingModelGenerator(outputDir, pkgName, fileCopyrightNotice, defaultClassComment);
    generator.generateClasses();
  }

  public static String readAsString(Reader reader) {
    StringBuilder sb = new StringBuilder();
    try (BufferedReader bufReader = new BufferedReader(reader)) {
      while (bufReader.ready()) {
        sb.append(bufReader.readLine()).append("\n");
      }
    } catch (IOException ignored) { }
    return sb.toString();
  }

  private final File outputDir;
  private final String pkgName;
  private final String fileCopyrightNotice;
  private final String defaultClassComment;
  private final Json2Java converter = new Json2Java();

  public GeocodingModelGenerator(File outputDir, String pkgName, String fileCopyrightNotice, String defaultClassComment) {
    this.outputDir = outputDir;
    this.pkgName = pkgName;
    this.fileCopyrightNotice = fileCopyrightNotice;
    this.defaultClassComment = defaultClassComment;
  }

  public void generateClasses() throws Exception {

    processJson("/geocoding-response-1.json", "GeocodingResponse", null);
    processJson("/geocoding-response-2.json", "GeocodingResponse", null);
    processJson("/geocoding-response-3.json", "GeocodingResponse", null);
    processJson("/geocoding-response-4.json", "GeocodingResponse", null);
    processJson("/geocoding-response-5.json", "GeocodingResponse", null);
    processJson("/geocoding-response-6.json", "GeocodingResponse", null);
    processJson("/geocoding-response-7.json", "GeocodingResponse", null);
    processJson("/geocoding-response-8.json", "GeocodingResponse", null);
    processJson("/reverse-geocoding-response.json", "ReverseGeocodingResponse", null);

    ClassDefCollection classes = converter.getClasses();
    classes.setFileCopyrightNotice(fileCopyrightNotice);
    classes.setDefaultClassComment(defaultClassComment);
    CustomMappings mappings = new CustomMappings()
      .mapType("Northeast", "Location")
      .mapType("Northwest", "Location")
      .mapType("Southeast", "Location")
      .mapType("Southwest", "Location")
      .mapToArrayType("Results", "Result")
      .mapToArrayType("AddressComponents", "AddressComponent");
    classes.transform(mappings);
    classes.generateClasses(outputDir, "    ");
  }

  private void processJson(String file, String rootClass, CustomMappings mappings) throws Exception {
    InputStream json = GeocodingModelGenerator.class.getResourceAsStream(file);
    InputStreamReader reader = new InputStreamReader(json, "UTF-8");
    converter.processJson(reader, pkgName, rootClass, mappings);
  }
}
