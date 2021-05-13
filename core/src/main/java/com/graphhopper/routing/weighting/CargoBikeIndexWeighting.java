/*  Introduce by CargoRocket, based on PriorityWeighting
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
package com.graphhopper.routing.weighting;

import com.graphhopper.routing.ev.DecimalEncodedValue;
import com.graphhopper.routing.util.EncodingManager;
import com.graphhopper.routing.util.FlagEncoder;
import com.graphhopper.util.EdgeIteratorState;
import com.graphhopper.util.PMap;

/**
 * Support for CargoBikeIndex
 *
 * @author Henri Chilla
 */
public class CargoBikeIndexWeighting extends FastestWeighting {

    private final double minFactor;
    private final DecimalEncodedValue cargobikability;

    public CargoBikeIndexWeighting(FlagEncoder encoder, PMap pMap, TurnCostProvider turnCostProvider) {
        super(encoder, pMap, turnCostProvider);
        String key = EncodingManager.getKey(encoder, "cbi");
        cargobikability = encoder.getDecimalEncodedValue(key);

        double maxPriority = 5.0;
        minFactor = 0.0;
        double UNSET = -1.0;
    }

    @Override
    public double getMinWeight(double distance) {
        return minFactor * super.getMinWeight(distance);
    }

    @Override
    public double calcEdgeWeight(EdgeIteratorState edgeState, boolean reverse) {
        double cbi_value = edgeState.get(cargobikability);
        double weight = super.calcEdgeWeight(edgeState, reverse);
        if (Double.isInfinite(weight))
            return Double.POSITIVE_INFINITY;
        if ( !Double.isInfinite(cbi_value)) {
            double cargofactor = -Math.log(cbi_value) + 2.2;
            weight = weight * cargofactor;
        }
        return weight;
    }

    @Override
    public String getName() {
        return "cbi";
    }
}
