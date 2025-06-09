package com.market.core.user.domain;

public enum Role {

    SELLER("ROLE_SELLER", "판매자"),
    CUSTOMER("ROLE_USER", "구매자");

    private final String key;
    private final String title;

    Role(String key, String title) {
        this.key = key;
        this.title = title;
    }


    public String getKey() {
        return key;
    }

    public String getTitle() {
        return title;
    }
}
