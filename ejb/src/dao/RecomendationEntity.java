/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 *
 * @author Karl Lauret
 */
@Entity
@DiscriminatorValue("Recomendation")
public class RecomendationEntity extends PostEntity {
    
    /**
     *
     */
    public RecomendationEntity() {
        super();
    }

    /**
     *
     * @param title the title of the recommendation
     * @param body the body of the recommendation
     * @param author the author of the recommendation
     */
    public RecomendationEntity(String title, String body, UserEntity author) {
        super(title, body, author, author);
    }

}
