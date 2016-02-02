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
@Table(name = "messages_users")
public class MessageUserEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "message_id")
    private MessageEntity message;

    @Column(name = "new_message")
    private boolean newMessage;

    /**
     *
     */
    public MessageUserEntity() {

    }

    /**
     *
     * @param m Message sent
     * @param receiver of the message
     */
    public MessageUserEntity(MessageEntity m, UserEntity receiver) {
        this.user = receiver;
        this.message = m;
        this.newMessage = true;
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
        if (!(object instanceof MessageUserEntity)) {
            return false;
        }
        MessageUserEntity other = (MessageUserEntity) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dao.MessageUser[ id=" + id + "message=" + message.getContent() + " ]";
    }

    /**
     *
     * @return
     */
    public UserEntity getUser() {
        return user;
    }

    /**
     *
     * @param user
     */
    public void setUser(UserEntity user) {
        this.user = user;
    }

    /**
     *
     * @return
     */
    public MessageEntity getMessage() {
        return message;
    }

    /**
     *
     * @param message
     */
    public void setMessage(MessageEntity message) {
        this.message = message;
    }

    /**
     *
     * @return
     */
    public boolean isNewMessage() {
        return newMessage;
    }

    /**
     *
     * @param newMessage
     */
    public void setNewMessage(boolean newMessage) {
        this.newMessage = newMessage;
    }


}
