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
public class Program {

    private int programId;
    private String programName;
    private int channelId;
    private String description;
    private String programType;
    private String status;
    
    public Program(){}

    // Constructor without ID (Insert)
    public Program(String programName, int channelId, String description, String programType, String status) {
        this.programName = programName;
        this.channelId = channelId;
        this.description = description;
        this.programType = programType;
        this.status = status;
    }

    // Constructor with ID (Retrieve/Update)
    public Program(int programId, String programName, int channelId, String description, String programType, String status) {
        this.programId = programId;
        this.programName = programName;
        this.channelId = channelId;
        this.description = description;
        this.programType = programType;
        this.status = status;
    }

    // Getters & Setters
    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public String getProgramName() { return programName; }
    public void setProgramName(String programName) { this.programName = programName; }

    public int getChannelId() { return channelId; }
    public void setChannelId(int channelId) { this.channelId = channelId; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getProgramType() { return programType; }
    public void setProgramType(String programType) { this.programType = programType; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
public String toString() {
    return programName; // this will show program name in the combo box
}


}
