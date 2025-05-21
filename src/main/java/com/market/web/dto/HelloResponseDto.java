package com.market.web.dto;

public class HelloResponseDto {

    private final String name;
    private final int amount;

    public HelloResponseDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }
}
