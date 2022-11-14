package com.example.libertyformapiserver.dto.survey.patch;

import com.example.libertyformapiserver.domain.Choice;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class PatchSurveyModifyRes {
    private Survey survey;

    private List<Question> questions;

    private List<Question> extraQuestions;

    private List<Choice> extraChoices;

    // 질문 번호 확인 용
    private List<Integer> questionNumbers;

    private HashMap<Question, ArrayList<Integer>> choiceNumberMap;

    public PatchSurveyModifyRes(Survey survey, List<Question> questions){
        this.survey = survey;
        this.questions = questions;
        this.extraQuestions = new ArrayList<>();
        this.extraChoices = new ArrayList<>();
        this.questionNumbers = new ArrayList<>();
        this.choiceNumberMap = new HashMap<>();
    }

    public void addExtraQuestion(Question question){
        extraQuestions.add(question);
        addQuestionNumber(question);
    }

    public void addExtraChoice(Choice choice){
        extraChoices.add(choice);
        addChoiceNumber(choice);
    }

    // 번호 중복 확인을 위한 번호 추가
    public void addQuestionNumber(Question question){
        questionNumbers.add(question.getNumber());
    }

    public void addChoiceNumber(Choice choice){
        ArrayList choiceNumList = choiceNumberMap.get(choice.getQuestion());
        if(choiceNumList == null){
            choiceNumList = new ArrayList<Integer>();
        }
        choiceNumList.add(choice.getNumber());

        choiceNumberMap.put(choice.getQuestion(), choiceNumList);
    }
}
