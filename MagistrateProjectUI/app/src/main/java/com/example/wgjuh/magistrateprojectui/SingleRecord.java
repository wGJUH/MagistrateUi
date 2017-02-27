package com.example.wgjuh.magistrateprojectui;

/**
 * Created by wGJUH on 25.02.2017.
 */

public class SingleRecord {
    int testId;
    float record;
    String name;

    SingleRecord(){
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRecord(float record) {
        this.record = record;
    }

    public int getTestId() {
        return testId;
    }

    public String getName() {
        return name;
    }

    public float getRecord() {
        return record;
    }
}
