package com.aki.goosinsa.asecurity.oauth.provider;

public interface OAuth2UserInfo {

    String getProviderId();
    String getProvider();
    String getEmail();
    String getName();

}
