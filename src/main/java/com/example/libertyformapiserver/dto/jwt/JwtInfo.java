package com.example.libertyformapiserver.dto.jwt;

import lombok.*;

import javax.servlet.http.HttpServletRequest;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private Long memberId;

    static public Long getMemberId(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        if(jwtInfo == null)
            return null;

        return jwtInfo.getMemberId();
    }
}
