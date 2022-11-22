package com.example.libertyformapiserver.dto.contact.get;

import com.example.libertyformapiserver.dto.contact.vo.ContactVO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class GetContactsRes {
    private List<ContactVO> contacts;
}
