package com.example.easymapua;

public class Schedule {
    private String professor, coursecode, task, date, startTime, endTime, room;

    public Schedule(){

    }

    public Schedule(String professor, String coursecode, String task, String date, String startTime, String endTime, String room) {
        this.professor = professor;
        this.coursecode = coursecode;
        this.task = task;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public String getProfessor() {
        return professor;
    }

    public String getCoursecode() {
        return coursecode;
    }

    public String getTask() {
        return task;
    }

    public String getDate() {
        return date;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return room;
    }
}
