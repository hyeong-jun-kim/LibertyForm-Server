package com.example.libertyformapiserver.dto.login.post;

import com.example.libertyformapiserver.config.status.MemberType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PostLoginRes {

    @ApiModelProperty(
            example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjozNH0sImlhdCI6MTY2MTI5MTA1MCwiZXhwIjoxNjYyMTgwMDgzfQ.SpgTbYugx0e6oqSHxrEVKb9FghdTckY2Hu3YOQmlq94"
    )
    private String jwt;

    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    private String email;

    @ApiModelProperty(
            example = "김형준"
    )
    private String name;

    @ApiModelProperty(
            example = "GENERAL"
    )
    private MemberType memberType;
}
