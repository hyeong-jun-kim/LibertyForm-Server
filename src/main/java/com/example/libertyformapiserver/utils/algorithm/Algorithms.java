package com.example.libertyformapiserver.utils.algorithm;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;

import java.util.Collections;
import java.util.List;

public class Algorithms {

    // 1부터 numbers.size 만큼 중복되는 번호가 있는지 검사하기
    public static void checkDuplicateNumber(List<Integer> numbers){
        Collections.sort(numbers);
        for(int i = 1; i <= numbers.size(); i++){
            if(numbers.get(i - 1 ) != i)
                throw new BaseException(BaseResponseStatus.NOT_SEQUENCE_QUESTION_NUMBER);
        }
    }
}
