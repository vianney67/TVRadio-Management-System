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
public class Channel {
     private int id;
    private String name;
    private String frequency;
    private String type;
    private String language;

    // Constructors
    public Channel() {
    }

    public Channel(int id, String name, String frequency, String type, String language) {
        this.id = id;
        this.name = name;
        this.frequency = frequency;
        this.type = type;
        this.language = language;
    }

    public Channel(String name, String frequency, String type, String language) {
        this.name = name;
        this.frequency = frequency;
        this.type = type;
        this.language = language;
    }

    // Getters and Setters
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

//    @Override
//    public String toString() {
//        return "Channel{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", frequency='" + frequency + '\'' +
//                ", type='" + type + '\'' +
//                ", language='" + language + '\'' +
//                '}';
//    }
    
    @Override
public String toString() {
    return this.getName(); // or program name
}
    
}
