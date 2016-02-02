/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Karl Lauret
 */
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "messagetype", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("Message")
@Table(name = "messages")
public class MessageEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String content;

    @Column
    private String subject;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "message")
    private List<MessageUserEntity> target = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity sendBy;

    @Column(name = "group_name")
    private String groupName;

    /**
     *
     */
    public MessageEntity() {

    }

    /**
     *
     * @param content the content of the message
     * @param subject the subject of the message 
     * @param sendBy the author of the message
     */
    public MessageEntity(String content, String subject, UserEntity sendBy) {
        this.content = content;
        this.subject = subject;
        this.sendBy = sendBy;
    }

    /**
     *
     * @param mue
     */
    public void addTarget(MessageUserEntity mue) {
        this.target.add(mue);
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
        if (!(object instanceof MessageEntity)) {
            return false;
        }
        MessageEntity other = (MessageEntity) object;
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
    public String getContent() {
        return content;
    }

    /**
     *
     * @param content
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     *
     * @return
     */
    public String getSubject() {
        return subject;
    }

    /**
     *
     * @param subject
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }

    /**
     *
     * @return
     */
    public List<MessageUserEntity> getTarget() {
        return target;
    }

    /**
     *
     * @param target
     */
    public void setTarget(List<MessageUserEntity> target) {
        this.target = target;
    }

    /**
     *
     * @return
     */
    public UserEntity getSendBy() {
        return sendBy;
    }

    /**
     *
     * @param sendBy
     */
    public void setSendBy(UserEntity sendBy) {
        this.sendBy = sendBy;
    }

    /**
     *
     * @return
     */
    public String getGroupName() {
        return groupName;
    }

    /**
     *
     * @param groupName
     */
    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    /**
     *
     */
    public void updateGroupName() {
        this.setGroupName("");
        String tmp = "";
        for (MessageUserEntity ue : this.getTarget()) {
            tmp += ue.getUser().getId();
        }
        this.setGroupName(tmp);
    }

}
