package tv.superawesome.sdk.advertiser.utils;

public enum SAdvConfiguration {
    PRODUCTION(0),
    STAGING(1);

    private final int value;

    private SAdvConfiguration(int value) {
        this.value = value;
    }

    public static SAdvConfiguration fromValue(int configuration) {
        return configuration == 0?PRODUCTION:STAGING;
    }

    public int getValue() {
        return this.value;
    }
}
