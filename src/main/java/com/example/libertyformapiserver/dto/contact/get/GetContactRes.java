package com.example.libertyformapiserver.dto.contact.get;

import com.example.libertyformapiserver.dto.contact.ContactVO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class GetContactRes {
    private List<ContactVO> contacts;

    private long totalPage;

    private long currentPage;

    private boolean isPrevMove;

    private boolean isNextMove;
}
