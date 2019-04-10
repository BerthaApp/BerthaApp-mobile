package com.example.prueba1.Challenges;

public class Challenges {

    private int id;
    private String name;
    private String expiration_date;
    private String group_general;

    public Challenges(int id, String name, String expiration_date, String group_general) {
        this.id = id;
        this.name = name;
        this.expiration_date = expiration_date;
        this.group_general = group_general;
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

    public String getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(String expiration_date) {
        this.expiration_date = expiration_date;
    }

    public String getGroup_general() {
        return group_general;
    }

    public void setGroup_general(String group_general) {
        this.group_general = group_general;
    }
}
