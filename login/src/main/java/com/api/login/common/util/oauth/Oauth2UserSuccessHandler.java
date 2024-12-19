package com.api.login.common.util.oauth;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Oauth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final UserService userService;

    public Oauth2UserSuccessHandler(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        OAuth2User oAuth2User = (OAuth2User) authentication.getPrincipal();
        String registrationId = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        String userNameAttributeName = "name";

        // OAuthAttributes를 통해 사용자 정보 추출
        OAuthAttributes attributes = OAuthAttributes.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes()
        );

        // 회원가입 또는 로그인 처리
        if (isNewUser(attributes)) {
            // 회원가입 처리
            signUpOAuth(attributes);
        } else {
            // 로그인 처리
            loginOAuth(attributes);
        }

        // JWT 생성 후, React 애플리케이션으로 리디렉션
        String targetUrl = "http://localhost:3000"; // React 애플리케이션 URL
        String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }

    private boolean isNewUser(OAuthAttributes attributes) {
        // 사용자 정보로 새로운 사용자 여부 판단
        // 예시로 로그인/회원가입 여부를 판단
        ResultDTO result = userService.checkUserExists(attributes.getEmail());
        return result.getResult() != Status.SUCCESS;
    }

    private void signUpOAuth(OAuthAttributes attributes) {
        UserDTO userDTO = UserDTO.builder()
                .type("2")
                .id(attributes.getEmail())
                .email(attributes.getEmail())
                .name(attributes.getName())
                .build();

        ResultDTO result = userService.signUp(userDTO);

        if (result.getResult() == Status.SUCCESS) {
            // 회원가입 성공
            log.info("회원가입 성공: {}", attributes.getEmail());
        } else {
            throw new OAuth2AuthenticationException("회원가입에 실패했습니다.");
        }
    }

    private void loginOAuth(OAuthAttributes attributes) {
        UserDTO userDTO = UserDTO.builder()
                .type("2")
                .id(attributes.getEmail())
                .build();

        ResultDTO result = userService.login(userDTO);

        if (result.getResult() == Status.SUCCESS) {
            // 로그인 성공
            log.info("로그인 성공: {}", attributes.getEmail());
        } else {
            throw new OAuth2AuthenticationException("로그인에 실패했습니다.");
        }
    }
}

