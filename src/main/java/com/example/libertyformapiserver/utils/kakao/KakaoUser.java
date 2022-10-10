package com.example.libertyformapiserver.utils.kakao;

import lombok.Getter;

// TODO JSON 매핑 오류 고쳐야 함
@Getter
public class KakaoUser {
    Long id;
    String connected_at;
    Properties properties;
    String profile_nickname;
    String account_email;
    KakaoAcount kakao_account;
}

class Properties{
    String nickname;
}

@Getter
class KakaoAcount{
    String profile_nickname_needs_agreement;
    Profile profile;
    String has_email;
    String email_needs_agreement;
    String is_email_valid;
    String is_email_verified;
    String email;
}

class Profile{
    String nickname;
}
