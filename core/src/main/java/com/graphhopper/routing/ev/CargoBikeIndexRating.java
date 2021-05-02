package com.graphhopper.routing.ev;

public class CargoBikeIndexRating {
    public static final String KEY = "cargobikeindex";

    public static IntEncodedValue create() {
        return new UnsignedIntEncodedValue(KEY, 3, true);
    }
}
