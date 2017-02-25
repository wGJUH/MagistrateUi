package com.example.wgjuh.magistrateprojectui;

/**
 * Created by wGJUH on 25.02.2017.
 */

public class SingleRecord {
    float record;
    String name;

    SingleRecord(){
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setRecord(float record) {
        this.record = record;
    }

    public String getName() {
        return name;
    }

    public float getRecord() {
        return record;
    }
}
