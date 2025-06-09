package com.market.core.user.domain;

import java.util.Objects;

/**
 * 유저 일련번호(identify).
 *
 * @author chan
 */
public class UserId {

    private String value;

    public UserId(String value) {

        if (value == null || value.isBlank()) {
            throw new RuntimeException("User-Id value must not be null or Blank");
        }

        this.value = value;
    }

    public static UserId of(String value) {
        return new UserId(value);
    }

    // for Hibernate
    public UserId() {
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(value, ((UserId) obj).value);
    }

    @Override
    public String toString() {
        return value;
    }
}
