package com.turf.playarena.pojo;

public enum TimeSlot {
    SLOT1("6 AM - 7 AM"),
    SLOT2("7 AM - 8 AM"),
    SLOT3("8 AM - 9 AM"),
    SLOT4("9 AM - 10 AM"),
    SLOT5("10 AM - 11 AM"),
    SLOT6("11 AM - 12 PM"),
    SLOT7("12 PM - 1 PM"),
    SLOT8("2 PM - 3 PM"),
    SLOT9("3 PM - 4 PM"),
    SLOT10("4 PM - 5 PM"),
    SLOT11("5 PM - 6 PM"),
    SLOT12("6 PM - 7 PM"),
    SLOT13("7 PM - 8 PM"),
    SLOT14("8 PM - 9 PM"),
    SLOT15("9 PM - 10 PM"),
    SLOT16("10 PM - 11 PM"),
    SLOT17("11 PM - 12 AM");

    private final String displayName;

    TimeSlot(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }


}

