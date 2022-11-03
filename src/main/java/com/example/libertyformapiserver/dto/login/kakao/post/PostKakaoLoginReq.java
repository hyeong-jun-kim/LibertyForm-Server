package com.example.libertyformapiserver.dto.login.kakao.post;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostKakaoLoginReq {
    @ApiModelProperty(
            example = "Q23i3hYdO5s8SJojz_x9WZWpoMfuqze6Hv0r6o88CisM0gAAAYPEarnj"
    )
    String accessToken;
}
