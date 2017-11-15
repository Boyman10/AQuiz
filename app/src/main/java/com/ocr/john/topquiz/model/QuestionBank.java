package com.ocr.john.topquiz.model;

import java.util.Collections;
import java.util.List;

/**
 * Created by John on 11/12/2017.
 */
public class QuestionBank {

    private List<Question> mQuestionList;
    private int mNextQuestionIndex;

    public QuestionBank(List<Question> questionList) {
        // Shuffle the question list before storing it
        //https://openclassrooms.com/courses/java-et-les-collections/l-interface-list-e
        Collections.shuffle(questionList);
        mQuestionList = questionList;

        mNextQuestionIndex = 0;
    }

    public Question getQuestion() {
        // Ensure we loop over the questions
        if (mNextQuestionIndex == mQuestionList.size()) {
            mNextQuestionIndex = 0;
        }
        // Loop over the questions and return a new one at each call
        return mQuestionList.get(mNextQuestionIndex++);
    }
}