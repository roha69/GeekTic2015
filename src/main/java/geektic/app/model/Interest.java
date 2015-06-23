package geektic.app.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;

@Entity
@Table(name="interest")
public class Interest implements Serializable {
	
	@Id 
	@GeneratedValue
	private Long id;
	
	@JsonIgnore
	@ManyToMany(mappedBy = "interests") 
	private Set<Profile> profiles;
	
	private String nom;

	public Interest(){}
	
	public Interest(String nom) {
		super();
		this.nom = nom;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Set<Profile> getGeeks() {
		return profiles;
	}

	public void addGeek(Profile geek) {
		this.profiles.add(geek);
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	} 
	
}
