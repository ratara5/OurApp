package com.misiontic.habit_tracker.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Habits implements Parcelable {

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

    //Hacer parcelable
    private Habits(Parcel in) {
        name = in.readString();
        description = in.readString();
        category=in.readString();
    }

    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return name + " ("+category+"): " + description;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeString(name);
        out.writeString(category);
        out.writeString(description);
    }

    public static final Parcelable.Creator<Habits> CREATOR = new Parcelable.Creator<Habits>() {
        public Habits createFromParcel(Parcel in) {
            return new Habits(in);
        }

        public Habits[] newArray(int size) {
            return new Habits[size];
        }
    };
    //
}
