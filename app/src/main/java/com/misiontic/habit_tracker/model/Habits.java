package com.misiontic.habit_tracker.model;

public class Habits {

    private int id;
    private String name;
    private String description;
    private String category;
    private boolean selected;

    public Habits(String name, String description, String category) {
        this.name = name;
        this.description = description;
        this.category = category;
        selected=false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
