package com.morethan.game.dto;


/**
 * 描述:
 *
 * @outhor anthony
 * @create 2019-05-10 下午4:35
 */
public class Lottery {

    private String title;
    private String type;
    private String[] items;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String[] getItems() {
        return items;
    }

    public void setItems(String[] items) {
        this.items = items;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}

