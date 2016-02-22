/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun;

import dao.FriendEntity;
import dao.UserEntity;

/**
 *
 * @author Karl Lauret
 */
public class FriendOrNot {
    private UserEntity user;
    private FriendEntity friendEntity;
    private Boolean friend;

    public FriendOrNot(UserEntity ue, Boolean bool) {
        this.user=ue;
        this.friend = bool;
    }
    public FriendOrNot(FriendEntity f, Boolean bool) {
        this.friendEntity=f;
        this.friend = bool; 
    }
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public Boolean getFriend() {
        return friend;
    }

    public void setFriend(Boolean friend) {
        this.friend = friend;
    }

    public FriendEntity getFriendEntity() {
        return friendEntity;
    }

    public void setFriendEntity(FriendEntity friendEntity) {
        this.friendEntity = friendEntity;
    }
    
    
}
