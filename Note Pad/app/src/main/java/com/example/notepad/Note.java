package com.example.notepad;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Note implements Serializable {



    private String title;
    private String text;
    private String date;


    public Note(String aTitle, String aDate, String aText) {
        this.title = aTitle;
        this.date = aDate;
        this.text = aText;
    }


    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }


    public void setTitle(String aTitle) {
        this.title = aTitle;
    }

    public void setText(String aText) {
        this.text = aText;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String aDate) {
        this.date = aDate;
    }

    @Override
    public String toString() {
        return "Note{" +
                "titleEd='" + title + '\'' +
                ", dateEd='" + date + '\'' +
                ", textEd='" + text + '\'' +

                '}';
    }
}