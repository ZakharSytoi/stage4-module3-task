package com.mjc.school.service.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.mjc.school.service.security.Permission.*;

public enum Role {
    ADMIN(java.util.Set.of(
            NEWS_READ,
            NEWS_CREATE,
            NEWS_UPDATE,
            NEWS_DELETE,

            COMMENTS_READ,
            COMMENTS_CREATE,
            COMMENTS_UPDATE,
            COMMENTS_DELETE,

            TAGS_READ,
            TAGS_CREATE,
            TAGS_UPDATE,
            TAGS_DELETE,

            AUTHORS_READ,
            AUTHORS_CREATE,
            AUTHORS_UPDATE,
            AUTHORS_DELETE)),
    USER(java.util.Set.of(
            NEWS_READ,
            NEWS_CREATE,

            COMMENTS_READ,
            COMMENTS_CREATE
    ));

    private Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }

    public Set<SimpleGrantedAuthority> getAuthorities(){
        return permissions.stream()
                .map(permission -> new SimpleGrantedAuthority(permission.toString()))
                .collect(Collectors.toSet());
    }
}
