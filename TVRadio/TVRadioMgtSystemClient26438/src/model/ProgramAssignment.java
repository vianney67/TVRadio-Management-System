package model;
import java.io.Serializable;
public class ProgramAssignment implements Serializable {
    private static final long serialVersionUID = 1L;
    // Plain DTO for client-side usage
    private int assignmentId;
    
    private int channelId;
    
    private int programId;
    
    private int employeeId;
    
    private String assignedRole;

    public ProgramAssignment() {}

    public ProgramAssignment(int channelId, int programId, int employeeId, String assignedRole) {
        this.channelId = channelId;
        this.programId = programId;
        this.employeeId = employeeId;
        this.assignedRole = assignedRole;
    }

    public int getAssignmentId() { return assignmentId; }
    public void setAssignmentId(int assignmentId) { this.assignmentId = assignmentId; }

    public int getChannelId() { return channelId; }
    public void setChannelId(int channelId) { this.channelId = channelId; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public int getEmployeeId() { return employeeId; }
    public void setEmployeeId(int employeeId) { this.employeeId = employeeId; }

    public String getAssignedRole() { return assignedRole; }
    public void setAssignedRole(String assignedRole) { this.assignedRole = assignedRole; }
}
