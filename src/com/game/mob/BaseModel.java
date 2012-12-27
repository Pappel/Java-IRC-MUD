/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.game.mob;

import java.io.Serializable;
import javax.persistence.*;



@Entity
public class BaseModel implements Serializable {
    
    public static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BMID")
    private Integer BMID;
    
        
    	public static enum Gender {
		MALE("he", "him", "his", "male"),
		FEMALE("she", "her", "her", "female"),
		IT("it", "it", "its", "asexual");
		
		private String subject;
		private String objective;
		private String possessive;
		private String noun;
		
		Gender(String subject, String objective, String possessive, String noun) {
			this.subject = subject;
			this.objective = objective;
			this.possessive = possessive;
			this.noun = noun;
		}
		
		public String getNoun() { return noun; }
		public String getSubject() { return subject; }
		public String getObject() { return objective; }
		public String getPossessive() { return possessive; }
	};
    
    /*
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    * 
    */
    
    
    private int level = 1;
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    
    @Enumerated(EnumType.STRING)
    private Gender gender;
    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    
    @OneToOne(cascade= CascadeType.ALL)
    private Body body = new Body();
    public Body getBody() { return body; }
    public void setBody(Body body) { this.body = body; }
    
    
    // -- STATS BELOW --

    public Integer getBMID() {
        return BMID;
    }

    public void setBMID(Integer BMID) {
        this.BMID = BMID;
    }

    
    
}
