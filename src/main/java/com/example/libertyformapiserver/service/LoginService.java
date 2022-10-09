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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.*;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class LoginService {
    private final MemberRepository memberRepository;
    private final JwtService jwtService;

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
}
