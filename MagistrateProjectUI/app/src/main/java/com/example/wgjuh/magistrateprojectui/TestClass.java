package com.example.wgjuh.magistrateprojectui;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by wGJUH on 26.02.2017.
 */

public class TestClass {
    ArrayList<ArrayList<Answer>> answers = new ArrayList<>();
    ArrayList<Question> questions = new ArrayList<>();
    SqlHelper sqlHelper;
    Context context;
    int testId;
        public TestClass(int testId, Context context) {
            this.testId = testId;
            this.context = context;
            sqlHelper = SqlHelper.getInstance(context);
            setQuestionsFromDb();
            setAnswers();

        }

    public int getTestId() {
        return testId;
    }

    private void setAnswers(){
        answers = sqlHelper.getAnswersForQuestions(questions,this);
    }

    private void setQuestionsFromDb(){
        questions = sqlHelper.getQuestionsForTest(this);
    }


    public Question newQuestion(){
        return  new Question();
    }

    public float checkAnswers(ArrayList<Answer> answers){
        return 0f;
    }


    class Question{
        int id;
        String text;
        public void setId(int id) {
            this.id = id;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getId() {
            return id;
        }

        public String getText() {
            return text;
        }
        protected Answer newAnswer(){
            return new Answer();
        }
    }
    class Answer extends Question{

    }


}
