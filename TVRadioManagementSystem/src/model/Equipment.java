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
public class Equipment {
    
    private int id;
    private String name;
    private String type;
    private String status;        // Available, In Use, Under Maintenance
    private String conditionNote; // Optional notes

    public Equipment() {}

    public Equipment(int id, String name, String type, String status, String conditionNote) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.status = status;
        this.conditionNote = conditionNote;
    }

    // --- Getters and Setters ---
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getConditionNote() {
        return conditionNote;
    }

    public void setConditionNote(String conditionNote) {
        this.conditionNote = conditionNote;
    }

    @Override
    public String toString() {
        return name + " (" + type + ")";
    }    
}
