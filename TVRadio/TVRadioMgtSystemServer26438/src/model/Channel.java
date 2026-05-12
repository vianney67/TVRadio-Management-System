package model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "channels")
public class Channel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private int id;

    private String name;
    private String frequency;
    @Column(name = "channel_type")
    private String type; // TV or Radio
    private String language;

    @OneToMany(mappedBy = "channel", cascade = CascadeType.ALL, orphanRemoval = false)
    private List<Program> programs;

    public Channel() {
    }

    public Channel(int id, String name, String frequency, String type, String language) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.type = type;
        this.language = language;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrequency() {
        return frequency;
    }

    public void setFrequency(String frequency) {
        this.frequency = frequency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Program> getPrograms() {
        return programs;
    }

    public void setPrograms(List<Program> programs) {
        this.programs = programs;
    }

    @Override
    public String toString() {
        return name;
    }
}
