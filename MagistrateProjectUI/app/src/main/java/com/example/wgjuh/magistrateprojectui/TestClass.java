package com.example.wgjuh.magistrateprojectui;

import android.content.Context;
import android.util.Log;

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
            Log.d(Constants.TAG,"wait");
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

    public int size(){
        return questions.size();
    }

    public Question newQuestion(){
        return  new Question();
    }

    public float checkAnswers(ArrayList<Answer> answers){
        return 0f;
    }


    class Question{
        int id;
        int type;
        String text;

        public void setType(int type) {
            this.type = type;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setText(String text) {
            this.text = text;
        }

        public int getType() {
            return type;
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
