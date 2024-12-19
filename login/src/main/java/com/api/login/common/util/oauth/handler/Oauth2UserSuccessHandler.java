package com.api.login.common.util.oauth.handler;

import com.api.login.common.util.oauth.info.CustomOAuth2User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;

@Component
public class Oauth2UserSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();

        String qString = null;

        if (oAuth2User.isSignUp()) {
            qString = "";
        } else {
            qString = "" + oAuth2User.getEmail();
        }

        String targetUrl = "http://localhost:3000/auth/signIn" + "?" + qString;
        String redirectUrl = UriComponentsBuilder.fromUriString(targetUrl)
                .build().toUriString();
        getRedirectStrategy().sendRedirect(request, response, redirectUrl);
    }
}

