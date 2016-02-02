/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 *
 * @author Karl Lauret
 */
@Entity
@Table(name = "friends")
public class FriendEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_owner_id")
    private UserEntity owner;

    @ManyToOne
    @JoinColumn(name = "user_friend_id")
    private UserEntity friend;

    @Column(name = "accepted")
    private Boolean accepted;
    
    /**
     *
     */
    public FriendEntity(){
        this.accepted = false;
    }
    
    /**
     *
     * @param owner the user who invited the other
     * @param friend the user who got invited by the owner
     */
    public FriendEntity(UserEntity owner, UserEntity friend) {
        this.owner = owner;
        this.friend = friend;
        this.accepted =false;
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
        if (!(object instanceof FriendEntity)) {
            return false;
        }
        FriendEntity other = (FriendEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.User[ id=" + id + " ]";
    }

    /**
     *
     * @return
     */
    public UserEntity getOwner() {
        return owner;
    }

    /**
     *
     * @param owner
     */
    public void setOwner(UserEntity owner) {
        this.owner = owner;
    }

    /**
     *
     * @return
     */
    public UserEntity getFriend() {
        return friend;
    }

    /**
     *
     * @param friend
     */
    public void setFriend(UserEntity friend) {
        this.friend = friend;
    }

    /**
     *
     * @return
     */
    public Boolean getAccepted() {
        return accepted;
    }

    /**
     *
     * @param accepted
     */
    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

}
