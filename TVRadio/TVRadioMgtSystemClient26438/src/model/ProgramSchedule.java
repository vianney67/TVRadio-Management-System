package model;
import java.io.Serializable;
import java.sql.Time;
public class ProgramSchedule implements Serializable {
    private static final long serialVersionUID = 1L;
    // Plain DTO for client-side usage
    private int scheduleId;
    
    private int programId;
    
    private String channelName;
    
    private String day;
    
    private Time startTime;
    
    private Time endTime;

    public ProgramSchedule() {}

    public ProgramSchedule(int programId, String channelName, String day, Time startTime, Time endTime) {
        this.programId = programId;
        this.channelName = channelName;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getScheduleId() { return scheduleId; }
    public void setScheduleId(int scheduleId) { this.scheduleId = scheduleId; }

    public int getProgramId() { return programId; }
    public void setProgramId(int programId) { this.programId = programId; }

    public String getChannelName() { return channelName; }
    public void setChannelName(String channelName) { this.channelName = channelName; }

    public String getDay() { return day; }
    public void setDay(String day) { this.day = day; }

    public Time getStartTime() { return startTime; }
    public void setStartTime(Time startTime) { this.startTime = startTime; }

    public Time getEndTime() { return endTime; }
    public void setEndTime(Time endTime) { this.endTime = endTime; }
}
