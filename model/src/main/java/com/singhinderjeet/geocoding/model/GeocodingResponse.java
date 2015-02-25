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

import java.util.List;

/**
 * The GeocodingResponse class
 *
 * @author Inderjeet Singh
 */
public class GeocodingResponse {

    private final List<Result> results;
    private final String status;

    public GeocodingResponse(List<Result> results, String status) {
        this.results = results;
        this.status = status;
    }

    public List<Result> getResults() {
        return results;
    }

    public String getStatus() {
        return status;
    }
}
