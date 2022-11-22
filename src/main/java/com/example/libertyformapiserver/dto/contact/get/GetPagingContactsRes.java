package com.example.libertyformapiserver.dto.contact.get;

import com.example.libertyformapiserver.dto.contact.vo.ContactVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetPagingContactsRes {
    private List<ContactVO> contacts;

    @ApiModelProperty(
            example = "1"
    )
    private long currentPage;

    @ApiModelProperty(
            example = "false"
    )
    private boolean isPrevMove;

    @ApiModelProperty(
            example = "true"
    )
    private boolean isNextMove;
}
