/* Introduced by CargoRocket, based on Bike2WeightFlagEncoder.java
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
package com.graphhopper.routing.util;

import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.ev.EncodedValue;
import com.graphhopper.routing.ev.UnsignedDecimalEncodedValue;
import com.graphhopper.routing.weighting.CargoBikeIndexWeighting;
import com.graphhopper.util.PMap;
import java.util.List;

import static com.graphhopper.routing.util.EncodingManager.getKey;

/**
 * Specifies the settings for Cargobikes with rating from CargoRocket
 *
 * @author Henri Chilla
 */
public class CargobikeFlagEncoder extends BikeFlagEncoder {
    DecimalEncodedValue cargobikeindexEnc;

    public CargobikeFlagEncoder() {
        this(4, 2, 0);
    }

    public CargobikeFlagEncoder(PMap properties) {
        this(properties.getInt("speed_bits", 4),
                properties.getInt("speed_factor", 2),
                properties.getBool("turn_costs", false) ? 1 : 0);

        blockBarriersByDefault(properties.getBool("block_barriers", false));
        blockPrivate(properties.getBool("block_private", true));
        blockFords(properties.getBool("block_fords", false));
    }

    public CargobikeFlagEncoder(int speedBits, double speedFactor, int maxTurnCosts) {
        super();
        addPushingSection("footway");
        addPushingSection("pedestrian");
        addPushingSection("steps");

        setTrackTypeSpeed("grade1", 18); // paved
        setTrackTypeSpeed("grade2", 6); // now unpaved ...
        setTrackTypeSpeed("grade3", 4);
        setTrackTypeSpeed("grade4", 3);
        setTrackTypeSpeed("grade5", 2); // like sand/grass

        setSurfaceSpeed("cobblestone", 8);
        setSurfaceSpeed("cobblestone:flattened", 10);
        setSurfaceSpeed("concrete:lanes", 10);
        setSurfaceSpeed("concrete:plates", 10);
        setSurfaceSpeed("paving_stones", 18);
        setSurfaceSpeed("paving_stones:30", 12);
        setSurfaceSpeed("unpaved", 6);
        setSurfaceSpeed("compacted", 6);
        setSurfaceSpeed("dirt", 4);
        setSurfaceSpeed("earth", 4);
        setSurfaceSpeed("fine_gravel", 8);
        setSurfaceSpeed("grass", 4);
        setSurfaceSpeed("grass_paver", 4);
        setSurfaceSpeed("gravel", 4);
        setSurfaceSpeed("ground", 4);
        setSurfaceSpeed("pebblestone", 6);

        setHighwaySpeed("steps", 0);

        absoluteBarriers.add("kissing_gate");
        // TODO make depending on max width on barrier
        //potentialBarriers.add("bollard");
        //potentialBarriers.add("cycle_barrier");
        //setSpecificClassBicycle("touring");
    }

    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public boolean supports(Class<?> feature) {
        if (super.supports(feature))
            return true;

        return CargoBikeIndexWeighting.class.isAssignableFrom(feature);
    }

    @Override
    public void createEncodedValues(List<EncodedValue> registerNewEncodedValue, String prefix, int index) {
        super.createEncodedValues(registerNewEncodedValue, prefix, index);
        registerNewEncodedValue.add(cargobikeindexEnc = new UnsignedDecimalEncodedValue(getKey(prefix, "cargobikeindex"), 6, 0.1, Double.POSITIVE_INFINITY, false));
    }

    @Override
    public String toString() {
        return "cargobike";
    }
}
