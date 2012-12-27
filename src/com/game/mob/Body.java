package com.game.mob;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;


@Entity
public class Body implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="BOID")
    private Integer BOID;

    
    @OneToMany(cascade= CascadeType.ALL, fetch= FetchType.EAGER)
    private List<BodyPart> bodyParts = new ArrayList<BodyPart>();
    
    public Body() {
	bodyParts = new ArrayList<BodyPart>(10);
        
        bodyParts.add(new BodyPart(BodyPart.PartType.HEAD));
        bodyParts.add(new BodyPart(BodyPart.PartType.SHOULDERS));
        bodyParts.add(new BodyPart(BodyPart.PartType.TORSO));
        bodyParts.add(new BodyPart(BodyPart.PartType.ARMS));
        bodyParts.add(new BodyPart(BodyPart.PartType.HANDS));
        bodyParts.add(new BodyPart(BodyPart.PartType.LEGS));
        bodyParts.add(new BodyPart(BodyPart.PartType.FEET));
        
        bodyParts.add(new BodyPart(BodyPart.PartType.LEFT_HAND));
        bodyParts.add(new BodyPart(BodyPart.PartType.RIGHT_HAND));
  
    }

    /**
     * @return the bodyParts
     */
    public List<BodyPart> getBodyParts() {
        return bodyParts;
    }

    /**
     * @param bodyParts the bodyParts to set
     */
    public void setBodyParts(List<BodyPart> bodyParts) {
        this.bodyParts = bodyParts;
    }
 
    
    

    
}
