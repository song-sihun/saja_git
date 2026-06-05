package org.diner.dinerreserve.config.status;

public enum RestaurantCategory {
    KOREAN("한식"),
    CHINESE("중식"),
    JAPANESE("일식"),
    WESTERN("양식"),
    SNACK("분식"),
    CAFE("카페"),
    MEAT("고기"),
    SEAFOOD("해산물"),
    OTHER("기타");

    private final String label;

    RestaurantCategory(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}