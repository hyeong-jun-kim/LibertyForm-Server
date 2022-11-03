package com.example.libertyformapiserver.dto.member.kakao.post;

import com.example.libertyformapiserver.config.status.EmailValidStatus;
import com.example.libertyformapiserver.config.status.MemberType;
import com.example.libertyformapiserver.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostKakaoRegisterReq {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",
            message = "이메일 양식에 맞게 입력해 주시기 바랍니다.")
    private String email;

    @ApiModelProperty(
            example = "김형준"
    )
    @Size(min = 1, max = 30)
    private String name;

    public Member toEntity(String email, String name){
        return Member.builder()
                .email(getEmail())
                .name(getName())
                .member_type(MemberType.KAKAO)
                .email_valid_status(EmailValidStatus.NOT_VALID)
                .build();
    }
}

