/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Karl Lauret
 */
public class GroupListNewMessages {
    private String groupName;
    private Integer nbNewsMessages;
    private List<String> usernames;
    
    public GroupListNewMessages(String name,Integer nb){
        this.groupName = name;
        this.nbNewsMessages = nb;
        this.usernames = new ArrayList<>();
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getNbNewsMessages() {
        return nbNewsMessages;
    }

    public void setNbNewsMessages(Integer nbNewsMessages) {
        this.nbNewsMessages = nbNewsMessages;
    }

    public List<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
    public void addUsername(String username){
        this.usernames.add(username);
    }
    
    
}
