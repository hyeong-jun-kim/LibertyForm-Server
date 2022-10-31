package com.example.libertyformapiserver.dto.jwt;

import lombok.*;

import javax.servlet.http.HttpServletRequest;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private long memberId;

    static public long getMemberId(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        return jwtInfo.getMemberId();
    }
}
