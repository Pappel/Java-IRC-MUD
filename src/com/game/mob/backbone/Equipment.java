package com.game.mob.backbone;

import java.io.Serializable;
import javax.persistence.*;



public class Equipment implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="EQID")
    private Integer EQID;
    
    
    
}
