package com.api.login.common.util.oauth.info;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final boolean isSignUp;
    private final Map<String, Object> attributes;
    private final String nameAttributeKey;
    private final String email;

    public CustomOAuth2User(boolean isSignUp, Map<String, Object> attributes, String nameAttributeKey, String email) {
        this.isSignUp = isSignUp;
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.email = email;
    }

    public boolean isSignUp() {
        return isSignUp;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getName() {
        return (String) attributes.get(nameAttributeKey);
    }
}

