package model;
import java.io.Serializable;
public class Program implements Serializable {
    private static final long serialVersionUID = 1L;
    // Plain DTO for client-side usage
    private int id;
    
    private String name;
    private String duration;
    private Channel channel;
    private int channelId; // For easy DAO handling

    public Program() {}

    public Program(int id, String name, String duration, Channel channel) {
        this.id = id;
        this.name = name;
        this.duration = duration;
        this.channel = channel;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public Channel getChannel() { return channel; }
    public void setChannel(Channel channel) { this.channel = channel; }
    
    public int getChannelId() {
        if (channel != null) return channel.getId();
        return channelId;
    }
    public void setChannelId(int channelId) { this.channelId = channelId; }
    
    @Override
    public String toString() {
        return name;
    }
}
