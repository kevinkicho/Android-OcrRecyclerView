package com.example.kevin.myocrapp120517;

import java.io.Serializable;

/**
 * Created by kevin on 12/5/2017.
 */

public class ListItem implements Serializable {
    private String title;

    public ListItem(String title){
        this.title = title;
    }

    public String getTitle(){ return this.title; }
}
