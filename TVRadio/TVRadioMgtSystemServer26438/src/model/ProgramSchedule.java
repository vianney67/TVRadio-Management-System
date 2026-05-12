package model;

import java.io.Serializable;
import java.sql.Time;
import javax.persistence.*;

@Entity
@Table(name = "program_schedules")
public class ProgramSchedule implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "schedule_id")
    private int scheduleId;
    
    @Column(name = "program_id")
    private int programId;
    
    @Column(name = "channel_name")
    private String channelName;
    
    private String day;
    
    @Column(name = "start_time")
    private Time startTime;
    
    @Column(name = "end_time")
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
