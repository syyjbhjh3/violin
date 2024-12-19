package com.api.login.common.util.oauth;

import com.api.login.common.model.dto.ResultDTO;
import com.api.login.common.model.enums.Status;
import com.api.login.user.model.dto.UserDTO;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserService userService;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attributes = OAuthAttributes.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes()
        );

        log.info("Generated JWT for user: {}", attributes.getEmail());

        return new DefaultOAuth2User(
                Collections.emptySet(),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private OAuth2User signUpOAuth(OAuthAttributes attributes) {
        UserDTO userDTO = UserDTO.builder()
                .type("2")
                .id(attributes.getEmail())
                .email(attributes.getEmail())
                .name(attributes.getName())
                .build();

        ResultDTO result = userService.signUp(userDTO);

        if (result.getResult() == Status.SUCCESS) {
            return new DefaultOAuth2User(
                    Collections.emptySet(),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey()
            );
        } else {
            throw new OAuth2AuthenticationException("회원가입에 실패했습니다.");
        }
    }

    private OAuth2User loginOAuth(OAuthAttributes attributes) {
        UserDTO userDTO = UserDTO.builder()
                .type("2")
                .id(attributes.getEmail())
                .build();

        ResultDTO result = userService.login(userDTO);

        if (result.getResult() == Status.SUCCESS) {
            return new DefaultOAuth2User(
                    Collections.emptySet(),
                    attributes.getAttributes(),
                    attributes.getNameAttributeKey()
            );
        } else {
            throw new OAuth2AuthenticationException("로그인에 실패했습니다.");
        }
    }
}
