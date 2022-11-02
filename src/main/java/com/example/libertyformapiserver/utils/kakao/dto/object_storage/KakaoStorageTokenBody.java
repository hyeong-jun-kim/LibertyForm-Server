package com.example.libertyformapiserver.utils.kakao.dto.object_storage;

import java.util.ArrayList;
import java.util.List;

public class KakaoStorageTokenBody {
    public KakaoStorageTokenBody(String accessKey, String secretKey){
        auth.identity.methods.add("application_credential");
        auth.identity.application_credential.id = accessKey;
        auth.identity.application_credential.secret = secretKey;
    }
    private Auth auth = new Auth();

    class Auth{
        private Identity identity = new Identity();
        class Identity{
            private List<String> methods = new ArrayList<>();
            private Credential application_credential = new Credential();

            class Credential{
                private String id;
                private String secret;
            }
        }
    }
}
