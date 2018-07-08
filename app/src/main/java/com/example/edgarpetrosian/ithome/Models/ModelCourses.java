package com.example.edgarpetrosian.ithome.Models;

public class ModelCourses {
    private int coursImagePath;
    private String coursesName;

    public ModelCourses(int coursImagePath, String coursesName) {
        this.coursImagePath = coursImagePath;
        this.coursesName = coursesName;
    }

    public String getCoursesName() {
        return coursesName;
    }

    public int getCoursImagePath() {
        return coursImagePath;
    }
}
