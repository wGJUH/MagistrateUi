package com.example.wgjuh.magistrateprojectui;

/**
 * Created by wGJUH on 19.02.2017.
 */

public class ArticleThemeValue {

    String name;
    int id;
    ArticleThemeValue(String name, int id){
        setName(name);
        setId(id);
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getArticleName() {
        return name;
    }

    public int getId() {
        return id;
    }
}
