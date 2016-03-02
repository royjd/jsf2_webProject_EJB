/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Karl Lauret
 */
@Entity
@Table(name = "users")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    @NotNull
    //@Pattern( regexp = "([^.@]+)(\\.[^.@]+)*@([^.@]+\\.)+([^.@]+)", message = "E-mail not valid" )
    private String email;

    @Column
    @NotNull
    //@Pattern(regexp = ".*(?=.{8,})(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).*", message = "Your pass word isn't secured")
    private String password;

    @Column(unique = true)
    @NotNull
    @Size( min = 3, message = "Username must contain at least 3 letter" )
    private String username;
   
    @OneToOne
    @JoinColumn(name = "profile_id")
    private ProfileEntity profile;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")//was super heavy changed to lazy
    //@Fetch(FetchMode.SELECT)//Fix for BUG DE HIBERNATE maybe :D
    private List<MessageUserEntity> messageR = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sendBy")//was super heavy changed to lazy
    //@Fetch(FetchMode.SELECT)//Fix for BUG DE HIBERNATE maybe :D
    private List<MessageEntity> messageS = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "owner")//was super heavy changed to lazy
    private List<FriendEntity> friends = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "friend")//was super heavy changed to lazy
    private List<FriendEntity> friendedBy = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author")//was super heavy changed to lazy
    //@Fetch(FetchMode.SELECT)//Fix for BUG DE HIBERNATE maybe :D
    private List<PostEntity> postsS = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "target" )//was super heavy changed to lazy
    //@Fetch(FetchMode.SELECT)//Fix for BUG DE HIBERNATE maybe :D//was a fix for the list in eager
    private List<PostEntity> postsR = new ArrayList<>();

    /**
     *
     */
    public UserEntity() {
        this.profile = new ProfileEntity();
    }

    /**
     * 
     * @param email
     * @param username
     * @param password
     * @param firstName
     * @param lastName 
     */
    public UserEntity(String email, String username, String password, String firstName, String lastName){
        this.email = email;
        this.username = username;
        this.password = password;
        this.profile = new ProfileEntity(lastName,firstName);
        /*this.postsS.add(new AlbumEntity("DefaultAlbum", "Default album", this));
        this.postsS.add(new AlbumEntity("NewsAlbum", "News album", this));
        this.postsS.add(new AlbumEntity("ProfileAlbum", "Profile album", this));*/
    }
    /**
     *
     * @param email
     * @param password
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public UserEntity(String email, String password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        this.email = email;
        this.password = password;//PasswordManager.createHash(password);
    }

   

    /**
     *
     * @param fe
     */
    public void addFriend(FriendEntity fe) {
        this.friends.add(fe);
    }

    /**
     *
     * @param pe
     */
    public void addPost(PostEntity pe) {
        if (pe instanceof NewsEntity) {
            if (pe.getAuthor().getId().equals(this.getId())) {
                this.postsS.add(pe);
            } else {
                this.postsR.add(pe);
            }
        } else{
            this.postsS.add( pe);
        }
        
    }

    /**
     *
     * @param fe
     */
    public void addFriendedBy(FriendEntity fe) {
        this.friendedBy.add(fe);
    }

    /**
     *
     * @param fe
     */
    public void removeFriend(FriendEntity fe) {
        this.friends.remove(fe);
    }

    /**
     *
     * @param fe
     */
    public void removeFriendedBy(FriendEntity fe) {
        this.friendedBy.remove(fe);
    }

    /**
     *
     * @param mue
     */
    public void addMessageR(MessageUserEntity mue) {
        this.messageR.add(mue);
    }

    /**
     *
     * @param me
     */
    public void addMessageS(MessageEntity me) {
        this.messageS.add(me);
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
        if (!(object instanceof UserEntity)) {
            return false;
        }
        UserEntity other = (UserEntity) object;
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
    public List<MessageUserEntity> getMessageR() {
        return messageR;
    }

    /**
     *
     * @param messageR
     */
    public void setMessageR(List<MessageUserEntity> messageR) {
        this.messageR = messageR;
    }

    /**
     *
     * @return
     */
    public List<MessageEntity> getMessageS() {
        return messageS;
    }

    /**
     *
     * @param messageS
     */
    public void setMessageS(List<MessageEntity> messageS) {
        this.messageS = messageS;
    }

    /**
     *
     * @return
     */
    public List<FriendEntity> getFriends() {
        return friends;
    }

    /**
     *
     * @param friends
     */
    public void setFriends(List<FriendEntity> friends) {
        this.friends = friends;
    }

    /**
     *
     * @return
     */
    public List<FriendEntity> getFriendedBy() {
        return friendedBy;
    }

    /**
     *
     * @param friendedBy
     */
    public void setFriendedBy(List<FriendEntity> friendedBy) {
        this.friendedBy = friendedBy;
    }

    /**
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     * @param password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return
     */
    public ProfileEntity getProfile() {
        return profile;
    }

    /**
     *
     * @param profile
     */
    public void setProfile(ProfileEntity profile) {
        this.profile = profile;
    }

    /**
     *
     * @return
     */
    public List<UserEntity> getFriendToAccept() {
        List<UserEntity> lue = new ArrayList<>();
        for (FriendEntity fe : this.friendedBy) {
            if (!fe.getAccepted()) {
                lue.add(fe.getOwner());
            }
        }
        return lue;

    }

    /**
     *
     * @param friend
     * @return
     */
    public FriendEntity getFriend(UserEntity friend) {
        for (FriendEntity fe : this.friends) {
            if (fe.getFriend().equals(friend)) {
                return fe;
            }
        }
        return null;
    }

    /**
     *
     * @return
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     * @param username
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     * @return
     */
    public List<PostEntity> getPostsS() {
        return postsS;
    }

    /**
     *
     * @param postsS
     */
    public void setPostsS(List<PostEntity> postsS) {
        this.postsS = postsS;
    }

    /**
     *
     * @return
     */
    public List<PostEntity> getPostsR() {
        return postsR;
    }

    /**
     *
     * @param postsR
     */
    public void setPostsR(List<PostEntity> postsR) {
        this.postsR = postsR;
    }


}
