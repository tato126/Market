package com.market.core.user.domain;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.net.URI;
import java.util.Objects;

@Embeddable
public class UserPicture implements Serializable {

    private URI uri;

    public UserPicture(URI uri) {
        setUri(uri);
    }

    // for Hibernate
    public UserPicture() {
    }

    // Getter
    public URI getUri() {
        return uri;
    }

    // Setter
    public void setUri(URI uri) {
        this.uri = uri;
    }

    @Override
    public boolean equals(Object obj) {

        if (this == obj) {
            return true;
        }

        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return Objects.equals(uri, ((UserPicture) obj).uri);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uri);
    }

    @Override
    public String toString() {
        return "UserPicture{" +
                "uri=" + uri +
                '}';
    }
}
