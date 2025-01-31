/*  Introduced by CargoRocket, based on OSMHorseRatingParser.java
 *
 *  Licensed to GraphHopper GmbH under one or more contributor
 *  license agreements. See the NOTICE file distributed with this work for
 *  additional information regarding copyright ownership.
 *
 *  GraphHopper GmbH licenses this file to you under the Apache License,
 *  Version 2.0 (the "License"); you may not use this file except in
 *  compliance with the License. You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.graphhopper.routing.util.parsers;

import com.graphhopper.reader.ReaderWay;
import com.graphhopper.routing.ev.*;
import com.graphhopper.storage.IntsRef;

import java.util.List;

/**
 * TODO: Add Explanation
 */
public class OSMCargoBikeIndexRatingParser implements TagParser {

    private final DecimalEncodedValue cargoBikeIndex;

    public OSMCargoBikeIndexRatingParser() {
        this.cargoBikeIndex = CargoBikeIndexRating.create();
    }

    public OSMCargoBikeIndexRatingParser(DecimalEncodedValue cbi) {
        this.cargoBikeIndex = cbi;
    }

    @Override
    public void createEncodedValues(EncodedValueLookup lookup, List<EncodedValue> link) {
        link.add(cargoBikeIndex);
    }

    @Override
    public IntsRef handleWayTags(IntsRef edgeFlags, ReaderWay readerWay, boolean ferry, IntsRef relationFlags) {
        // TODO: Check if still necessary
        String tag = readerWay.getTag("cbi");
        double rating = 0;
        if (tag != null) {
            rating = Double.parseDouble(tag);
        }
        if (rating != 0)
            cargoBikeIndex.setDecimal(false, edgeFlags, rating);
        return edgeFlags;
    }
}
