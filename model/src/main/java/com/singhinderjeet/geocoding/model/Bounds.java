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

/**
 * The Bounds class
 *
 * @author Inderjeet Singh
 */
public class Bounds {

    private final Location northeast;
    private final Location southwest;

    public Bounds(Location northeast, Location southwest) {
        this.northeast = northeast;
        this.southwest = southwest;
    }

    public Location getNortheast() {
        return northeast;
    }

    public Location getSouthwest() {
        return southwest;
    }
}
