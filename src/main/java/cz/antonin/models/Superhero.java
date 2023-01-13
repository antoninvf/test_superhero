package cz.antonin.models;

public class Superhero {
    private int id;
    private int publisher_id;
    private String pseudonym;
    private String fullName;
    private String gender;
    private String race;
    private String alignment;

    public Superhero(int id, int publisher_id, String pseudonym, String fullName, String gender, String race, String alignment) {
        this.id = id;
        this.publisher_id = publisher_id;
        this.pseudonym = pseudonym;
        this.fullName = fullName;
        this.gender = gender;
        this.race = race;
        this.alignment = alignment;
    }

    public int getId() {
        return id;
    }

    public int getPublisher_id() {
        return publisher_id;
    }

    public String getPseudonym() {
        return pseudonym;
    }

    public String getFullName() {
        return fullName;
    }

    public String getGender() {
        return gender;
    }

    public String getRace() {
        return race;
    }

    public String getAlignment() {
        return alignment;
    }
}
