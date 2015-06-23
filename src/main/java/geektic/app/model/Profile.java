package geektic.app.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "PROFILE")
public class Profile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private EnumGender gender;

    private String lastname;

    private String forename;

    private int age;

    private int profilViews;

    @ManyToMany
    @JoinTable(name = "INTEREST",
            joinColumns = @JoinColumn(name = "ID_PROFILE"),
            inverseJoinColumns = @JoinColumn(name = "ID_INTEREST"))
    public List<Interest> interests;

    public Profile() {
        super();
    }

    public Profile(String username, String passwordDigest, String email, EnumGender gender, String lastname, String forename, int age, List<Interest> interests) {

        this.gender = gender;
        this.lastname = lastname;
        this.forename = forename;
        this.age = age;
        this.interests = interests;
    }

    public EnumGender getGenre() {
        return gender;
    }

    public void setGenre(EnumGender genre) {
        this.gender = genre;
    }

    public String getNom() {
        return lastname;
    }

    public void setNom(String nom) {
        this.lastname = nom;
    }

    public String getPrenom() {
        return forename;
    }

    public void setPrenom(String prenom) {
        this.forename = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<Interest> getInterets() {
        return interests;
    }

    public void setInterets(List<Interest> interets) {
        this.interests = interets;
    }

    public int getVues() {
        return profilViews;
    }

    public void setVues(int views) {
        this.profilViews = views;
    }
}
