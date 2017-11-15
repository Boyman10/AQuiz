package com.ocr.john.topquiz.model;

import java.util.List;

/**
 * Created by John on 11/12/2017.
 */

public class Question {

    private String mQuestion;
    private List<String> mChoiceList;
    private int mAnswerIndex;

    public Question(String question, List<String> choiceList, int answerIndex) {
        mQuestion = question;

        // avons nous au moins une entrée dans la liste ? --- TODO
        mChoiceList = choiceList;

        // Vérifier sur l'index est compris entre 0 et la taille de la liste. -- TODO
        mAnswerIndex = answerIndex;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public List<String> getChoiceList() {
        return mChoiceList;
    }

    public int getAnswerIndex() {
        return mAnswerIndex;
    }
}
