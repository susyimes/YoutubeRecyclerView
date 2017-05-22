package com.susyimes.youtuberecyclerview.rxbus;

/**
 *  on 2016/6/21 0021.
 */
public class SusAction {
    String action;
    int action2;
    int position;
    String title;

    public SusAction(String action) {
        this.action = action;
    }
    public SusAction(int action2){
        this.action2=action2;
    }

    public SusAction(int position, String title) {
        this.title = title;
        this.position = position;
    }

    public SusAction(String action, int position) {
        this.action = action;
        this.position = position;
    }

    public int getAction2() {
        return action2;
    }

    public void setAction2(int action2) {
        this.action2 = action2;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }


    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
