package com.example.libertyformapiserver.dto.member.post;

import com.example.libertyformapiserver.config.status.EmailValidStatus;
import com.example.libertyformapiserver.config.type.MemberType;
import com.example.libertyformapiserver.domain.Member;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostRegisterReq {
    @ApiModelProperty(
            example = "forceTlight@gmail.com"
    )
    @Pattern(regexp = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$",
            message = "이메일 양식에 맞게 입력해 주시기 바랍니다.")
    private String email;

    @ApiModelProperty(
            example = "1q2w3e4r!"
    )
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String password;

    @ApiModelProperty(
            example = "1q2w3e4r!"
    )
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String checkPassword;

    @ApiModelProperty(
            example = "김형준"
    )
    @Size(min = 1, max = 30)
    private String name;

    public Member toEntity(String email, String password, String name){
        return Member.builder()
                .email(getEmail())
                .password(getPassword())
                .name(getName())
                .member_type(MemberType.GENERAL)
                .email_valid_status(EmailValidStatus.NOT_VALID)
                .build();
    }
}
