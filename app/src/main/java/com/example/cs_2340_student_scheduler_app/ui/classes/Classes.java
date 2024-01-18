package com.example.cs_2340_student_scheduler_app.ui.classes;

import java.io.Serializable;

public class Classes implements Serializable {
    private String courseName;
    private String time;

    private String instructor;

    public Classes() {
    }

    public Classes(String courseName, String time, String instructor) {
        this.courseName = courseName;
        this.time = time;
        this.instructor = instructor;
    }

    public String getTime() {
        return time;
    }

    public String getCourseName() {
        return courseName;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != getClass()) return false;
        Classes other = (Classes) o;
        return other.courseName.equals(courseName) && other.time == time && other.instructor.equals(instructor);
    }
}
