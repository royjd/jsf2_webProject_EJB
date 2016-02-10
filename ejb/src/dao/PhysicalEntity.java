/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 *
 * @author zdiawara
 */
@Entity
@Table(name="physicales")
public class PhysicalEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private Double height;
    
    private Double weight;
    
    private String gender;
    
    /**
     *
     */
    public PhysicalEntity(){
        
    }
    
    /**
     *
     * @param p
     */
    public void setData(PhysicalEntity p){
        this.gender = p.getGender();
        this.height = p.getHeight();
        this.weight = p.getWeight();
    }

    /**
     *
     * @return
     */
    public Double getHeight() {
        return height;
    }

    /**
     *
     * @param height
     */
    public void setHeight(Double height) {
        this.height = height;
    }

    /**
     *
     * @return
     */
    public Double getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     */
    public String getGender() {
        return gender;
    }

    /**
     *
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PhysicalEntity)) {
            return false;
        }
        PhysicalEntity other = (PhysicalEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.PhysicalEntity[ id=" + id + " ]";
    }
    
}
