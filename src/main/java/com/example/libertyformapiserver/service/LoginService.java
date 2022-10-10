package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.login.post.PostLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginRes;
import com.example.libertyformapiserver.repository.MemberRepository;
import com.example.libertyformapiserver.utils.encrypt.SHA256;
import com.example.libertyformapiserver.utils.jwt.JwtService;
import com.example.libertyformapiserver.utils.kakao.KakaoToken;
import com.example.libertyformapiserver.utils.kakao.KakaoUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

    // 일반 로그인
    public PostLoginRes login(PostLoginReq dto){
        Member member = memberRepository.findMemberByEmail(dto.getEmail())
                .orElseThrow(() -> new BaseException(NOT_EXIST_EMAIL));

        String password = SHA256.encrypt(dto.getPassword());

        if(!password.equals(member.getPassword())){
            throw new BaseException(INVALID_PASSWORD);
        }

        if(member.getStatus().equals(BaseStatus.INACTIVE)){
            throw new BaseException(INACTIVE_STATUS);
        }

        JwtInfo jwtInfo = JwtInfo.builder()
                .userId(member.getId())
                .build();

        String jwt = jwtService.createJwt(jwtInfo);

        return PostLoginRes.builder()
                .jwt(jwt)
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }

    // get kakao accessToken to request login using by code
    public String getKakaoAccessToken(String code){
        String accessToken = "";
        String refreshToken = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=96bef9ac5262aa72ce0e53dcb7f99e5f"); // REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost:8080/login/kakao/accessToken"); // redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("response body : " + result);

            // JSON 파싱 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoToken kakaoToken = objectMapper.readValue(result, KakaoToken.class);

            System.out.println("access_token : " + kakaoToken.getAccess_token());
            System.out.println("refresh_token : " + kakaoToken.getRefresh_token());

            br.close();
            bw.close();

        } catch (IOException e) {
            // TODO 오류 코드 수정
            throw new RuntimeException(e);
        }
        return accessToken;
    }

    // get user's information using by kakao access_token
    public void getKakaoUserInfo(String accessToken){
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            // 결과 코드 200이면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while((line = br.readLine()) != null){
                result += line;
            }
            System.out.println("response body : " + result);

            ObjectMapper objectMapper = new ObjectMapper();
            KakaoUser kakaoUser = objectMapper.readValue(result, KakaoUser.class);

            Long id = kakaoUser.getId();;
            String email = kakaoUser.getAccount_email();
            String name = kakaoUser.getProfile_nickname();

            System.out.println("id : " + id);
            System.out.println("email : " + email);
            System.out.println("name : " + name);

            br.close();

        } catch (IOException e) {
            // TODO 오류 코드 수정
            throw new RuntimeException(e);
        }
    }
}
