package com.graphhopper.routing.ev;

public class CargoBikeIndexRating {
    public static final String KEY = "cargobikeindex";

    public static DecimalEncodedValue create() {
        return new UnsignedDecimalEncodedValue(KEY, 6, 0.1, false);
    }
}
