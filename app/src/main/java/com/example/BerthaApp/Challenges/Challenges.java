package com.example.BerthaApp.Challenges;



//CHALLENGE OBJECT
public class Challenges {

    private int id;
    private String name;
    private String description;
    private int tokens;
    private String created_at;
    private String end_date;
    private String score_min;
    private String route_from;
    private String route_to;
    private boolean myChallenge;

    public Challenges(int id, String name, String description, int tokens, String created_at, String end_date, String score_min, String route_from, String route_to, boolean myChallenge) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.tokens = tokens;
        this.created_at = created_at;
        this.end_date = end_date;
        this.score_min = score_min;
        this.route_from = route_from;
        this.route_to = route_to;
        this.myChallenge = myChallenge;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getTokens() {
        return tokens;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    public String getScore_min() {
        return score_min;
    }

    public void setScore_min(String score_min) {
        this.score_min = score_min;
    }

    public String getRoute_from() {
        return route_from;
    }

    public void setRoute_from(String route_from) {
        this.route_from = route_from;
    }

    public String getRoute_to() {
        return route_to;
    }

    public void setRoute_to(String route_to) {
        this.route_to = route_to;
    }

    public boolean isMyChallenge() {
        return myChallenge;
    }

    public void setMyChallenge(boolean myChallenge) {
        this.myChallenge = myChallenge;
    }
}
