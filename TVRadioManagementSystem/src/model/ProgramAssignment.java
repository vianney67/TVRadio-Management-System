/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author admin
 */
public class ProgramAssignment {


    private int assignmentId;
    private int channelId;
    private int programId;
    private int employeeId;
    private String assignedRole;

    // ✅ Constructor without ID (for inserts)
    public ProgramAssignment(int channelId, int programId, int employeeId, String assignedRole) {
        this.channelId = channelId;
        this.programId = programId;
        this.employeeId = employeeId;
        this.assignedRole = assignedRole;
    }

    // ✅ Constructor with ID (for retrieval/update)
    public ProgramAssignment(int assignmentId, int channelId, int programId, int employeeId, String assignedRole) {
        this.assignmentId = assignmentId;
        this.channelId = channelId;
        this.programId = programId;
        this.employeeId = employeeId;
        this.assignedRole = assignedRole;
    }

    // Getters & Setters
    public int getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(int assignmentId) {
        this.assignmentId = assignmentId;
    }

    public int getChannelId() {
        return channelId;
    }

    public void setChannelId(int channelId) {
        this.channelId = channelId;
    }

    public int getProgramId() {
        return programId;
    }

    public void setProgramId(int programId) {
        this.programId = programId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAssignedRole() {
        return assignedRole;
    }

    public void setAssignedRole(String assignedRole) {
        this.assignedRole = assignedRole;
    }

    @Override
    public String toString() {
        return "ProgramAssignment{" +
                "assignmentId=" + assignmentId +
                ", channelId=" + channelId +
                ", programId=" + programId +
                ", employeeId=" + employeeId +
                ", assignedRole='" + assignedRole + '\'' +
                '}';
    }    
}
