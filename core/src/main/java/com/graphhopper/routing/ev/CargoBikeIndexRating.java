package com.graphhopper.routing.ev;

public class CargoBikeIndexRating {
    public static final String KEY = "cbi";

    public static DecimalEncodedValue create() {
        return new UnsignedDecimalEncodedValue(KEY, 6, 0.1, Double.POSITIVE_INFINITY, false);
    }
}
