/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Time;

/**
 *
 * @author admin
 */
public class ProgramSchedule {

    private int scheduleId;
    private int programId;
    private String channelName; // NEW FIELD
    private String day;
    private Time startTime;
    private Time endTime;



    // Constructor without ID (Insert)
    public ProgramSchedule(int programId, String channelName, String day, Time startTime, Time endTime) {
        this.programId = programId;
        this.channelName = channelName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Constructor with ID (Retrieve/Update)
    public ProgramSchedule(int scheduleId, int programId, String channelName, String day, Time startTime, Time endTime) {
        this.scheduleId = scheduleId;
        this.programId = programId;
        this.channelName = channelName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters & Setters
    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
