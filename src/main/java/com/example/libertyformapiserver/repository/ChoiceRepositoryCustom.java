package com.example.libertyformapiserver.repository;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.example.libertyformapiserver.domain.QChoice.choice;
import static com.example.libertyformapiserver.domain.QQuestion.question;

@Repository
@RequiredArgsConstructor
public class ChoiceRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    // 질문에 있는 단일 선택 문항 번호, 이름들 반환
    public List<Choice> findChoiceWithJoinSingleChoiceByQuestion(Question q){
        return queryFactory
                .select(choice)
                .from(choice)
                .join(choice.question, question)
                .where(choice.question.eq(q))
                .fetch();
    }
}
