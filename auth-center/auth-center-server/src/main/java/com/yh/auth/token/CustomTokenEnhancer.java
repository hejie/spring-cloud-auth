package com.yh.auth.token;

import com.yh.auth.provider.IRedisService;
import com.yh.hr.api.service.UserRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    private UserRemoteService userService;

    @Autowired
    private IRedisService redisService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        if (accessToken instanceof DefaultOAuth2AccessToken) {
            DefaultOAuth2AccessToken token = ((DefaultOAuth2AccessToken) accessToken);

            Map<String, String> requestParameters = authentication.getOAuth2Request().getRequestParameters();


            String userCode = requestParameters.get("username");
            String openId = redisService.get(userCode);
            String oauth = userCode + ":" + openId;
            Base64.Encoder encoder = Base64.getEncoder();
            String encodedStr = encoder.encodeToString(oauth.getBytes());

            String access_Token = token.getValue() + "-" + encodedStr;
            token.setValue(access_Token);

            Map<String, Object> additionalInformation = new HashMap<>();
            additionalInformation.put("client_id", authentication.getOAuth2Request().getClientId());
            token.setAdditionalInformation(additionalInformation);

            userService.updateUser(access_Token, userCode, openId);

            return token;
        }
        return accessToken;
    }


}