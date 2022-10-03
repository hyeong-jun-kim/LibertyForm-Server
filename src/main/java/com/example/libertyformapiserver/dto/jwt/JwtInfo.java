package com.example.libertyformapiserver.dto.jwt;

import lombok.*;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class JwtInfo {
    private int universityId;
    private int userId;
}
