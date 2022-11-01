package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.status.BaseStatus;
import com.example.libertyformapiserver.domain.Member;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.login.kakao.post.PostKakaoLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginReq;
import com.example.libertyformapiserver.dto.login.post.PostLoginRes;
import com.example.libertyformapiserver.dto.member.kakao.post.PostKakaoRegisterReq;
import com.example.libertyformapiserver.repository.MemberRepository;
import com.example.libertyformapiserver.utils.encrypt.SHA256;
import com.example.libertyformapiserver.jwt.JwtService;
import com.example.libertyformapiserver.utils.kakao.dto.KakaoTokenDTO;
import com.example.libertyformapiserver.utils.kakao.dto.KakaoUserDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final MemberService memberService;
    private final JwtService jwtService;

    // 일반 로그인
    public PostLoginRes login(PostLoginReq dto) {
        Member member = memberRepository.findMemberByEmail(dto.getEmail())
                .orElseThrow(() -> new BaseException(NOT_EXIST_EMAIL));

        String password = SHA256.encrypt(dto.getPassword());

        if (!password.equals(member.getPassword())) {
            throw new BaseException(INVALID_PASSWORD);
        }

        checkUserActive(member);

        JwtInfo jwtInfo = JwtInfo.builder()
                .memberId(member.getId())
                .build();

        String jwt = jwtService.createJwt(jwtInfo);

        return PostLoginRes.builder()
                .jwt(jwt)
                .email(member.getEmail())
                .name(member.getName())
                .memberType(member.getMember_type())
                .build();
    }

    // access_token을 사용해서 카카오 로그인
    @Transactional(readOnly = false)
    public PostLoginRes kakaoLogin(PostKakaoLoginReq dto) {
        // access_token request
        String accessToken = dto.getAccessToken();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
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

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoUserDTO kakaoUserDTO = objectMapper.readValue(result, KakaoUserDTO.class);
            br.close();

            String email = kakaoUserDTO.getKakao_account().getEmail();
            String name = kakaoUserDTO.getKakao_account().getProfile().getNickname();
            PostKakaoRegisterReq kakaoRegisterReqDTO = new PostKakaoRegisterReq(email, name);
            // login
            // 사용자가 존재하지 않으면 회원가입 후 멤버 반환
            Member member = memberRepository.findMemberByEmail(kakaoUserDTO.getKakao_account().getEmail())
                    .orElseGet(() -> memberService.registerKakaoMember(kakaoRegisterReqDTO));

            checkUserActive(member);

            JwtInfo jwtInfo = JwtInfo.builder()
                    .memberId(member.getId())
                    .build();

            String jwt = jwtService.createJwt(jwtInfo);

            return PostLoginRes.builder()
                    .jwt(jwt)
                    .email(member.getEmail())
                    .name(member.getName())
                    .memberType(member.getMember_type())
                    .build();
        } catch (IOException e) {
            throw new BaseException(KAKAO_LOGIN_ERROR);
        }
    }

    // get kakao accessToken to request login using by code
    public String getKakaoAccessToken(String code) {
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

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            // JSON 파싱 객체 생성
            ObjectMapper objectMapper = new ObjectMapper();
            KakaoTokenDTO kakaoTokenDTO = objectMapper.readValue(result, KakaoTokenDTO.class);

            System.out.println("access_token : " + kakaoTokenDTO.getAccess_token());
            System.out.println("refresh_token : " + kakaoTokenDTO.getRefresh_token());

            br.close();
            bw.close();
        } catch (IOException e) {
            throw new BaseException(KAKAO_TOKEN_ERROR);
        }
        return accessToken;
    }

    // Validation method
    public void checkUserActive(Member member) {
        if (member.getStatus().equals(BaseStatus.INACTIVE)) {
            throw new BaseException(INACTIVE_STATUS);
        }
    }
}
