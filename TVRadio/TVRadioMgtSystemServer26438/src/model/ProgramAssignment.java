package model;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "program_assignments")
public class ProgramAssignment implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private int assignmentId;
    
    @Column(name = "channel_id")
    private int channelId;
    
    @Column(name = "program_id")
    private int programId;
    
    @Column(name = "employee_id")
    private int employeeId;
    
    @Column(name = "assigned_role")
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
