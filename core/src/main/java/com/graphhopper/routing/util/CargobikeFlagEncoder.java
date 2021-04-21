/*
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

import com.graphhopper.util.PMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Specifies the settings for cycletouring/trekking
 *
 * @author ratrun
 * @author Peter Karich
 */
public class CargobikeFlagEncoder extends Bike2WeightFlagEncoder {
    public CargobikeFlagEncoder() {
        this(4, 2, 0);
    }

    private final Map<String, Integer> surfaceSpeeds = new HashMap<>();

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
        addPushingSection("platform");

        setTrackTypeSpeed("grade1", 18); // paved
        setTrackTypeSpeed("grade2", 8); // now unpaved ...
        setTrackTypeSpeed("grade3", 4);
        setTrackTypeSpeed("grade4", 4);
        setTrackTypeSpeed("grade5", 4); // like sand/grass

        setSurfaceSpeed("cobblestone", 8);
        setSurfaceSpeed("cobblestone:flattened", 10);
        setSurfaceSpeed("concrete:lanes", 10);
        setSurfaceSpeed("concrete:plates", 10);
        setSurfaceSpeed("paving_stones", 18);
        setSurfaceSpeed("paving_stones:30", 12);
        setSurfaceSpeed("unpaved", 14);
        setSurfaceSpeed("compacted", 16);
        setSurfaceSpeed("dirt", 8);
        setSurfaceSpeed("earth", 8);
        setSurfaceSpeed("fine_gravel", 10);
        setSurfaceSpeed("grass", 6);
        setSurfaceSpeed("grass_paver", 6);
        setSurfaceSpeed("gravel", 6);
        setSurfaceSpeed("ground", 8);
        setSurfaceSpeed("pebblestone", 8);

        setHighwaySpeed("steps", 0);

        avoidHighwayTags.add("trunk");
        avoidHighwayTags.add("trunk_link");
        avoidHighwayTags.add("primary");
        avoidHighwayTags.add("primary_link");
        avoidHighwayTags.add("secondary");
        avoidHighwayTags.add("secondary_link");

        // preferHighwayTags.add("road");
        preferHighwayTags.add("service");
        preferHighwayTags.add("tertiary");
        preferHighwayTags.add("tertiary_link");
        preferHighwayTags.add("residential");
        preferHighwayTags.add("unclassified");

        absoluteBarriers.add("kissing_gate");
        // TODO make depending on max width on barrier
        potentialBarriers.add("bollard");
        potentialBarriers.add("cycle_barrier");
        setSpecificClassBicycle("touring");
    }

    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    public String toString() {
        return "cargobike";
    }
}
