/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.Date;
/**
 *
 * @author admin
 */
public class Schedule {
    private int id;
    private int channelId;
    private int programId;
    private Integer employeeId; // nullable
    private Date startTime;
    private Date endTime;

    private String channelName;   // For displaying in JTable
    private String programName;
    private String employeeName;

    public Schedule() {}

    public Schedule(int id, int channelId, int programId, Integer employeeId,
                    Date startTime, Date endTime,
                    String channelName, String programName, String employeeName) {
        this.id = id;
        this.channelId = channelId;
        this.programId = programId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.channelName = channelName;
        this.programName = programName;
        this.employeeName = employeeName;
    }
    
    // Constructor for creating a new schedule (without names)
public Schedule(int id, int channelId, int programId, Integer employeeId, Date startTime, Date endTime) {
    this.id = id;
    this.channelId = channelId;
    this.programId = programId;
    this.employeeId = employeeId;
    this.startTime = startTime;
    this.endTime = endTime;
}


    // Getters and setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getChannelId() { return channelId; }
    public void setChannelId(int channelId) { this.channelId = channelId; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public Integer getEmployeeId() { return employeeId; }
    public void setEmployeeId(Integer employeeId) { this.employeeId = employeeId; }

    public Date getStartTime() { return startTime; }
    public void setStartTime(Date startTime) { this.startTime = startTime; }

    public Date getEndTime() { return endTime; }
    public void setEndTime(Date endTime) { this.endTime = endTime; }

    public String getChannelName() { return channelName; }
    public void setChannelName(String channelName) { this.channelName = channelName; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public String getEmployeeName() { return employeeName; }
    public void setEmployeeName(String employeeName) { this.employeeName = employeeName; }

    @Override
    public String toString() {
        return programName + " (" + startTime + ")";
    }
}
