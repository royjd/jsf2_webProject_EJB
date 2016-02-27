/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dao.ExperienceEntity;
import dao.PhysicalEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;
import javax.servlet.http.Part;

/**
 *
 * @author zakaridia
 */
@Local
public interface ProfileService {

    
    public boolean defineProfilePicture(Part file, Long userId,String context);
    
    public boolean defineCoverPicture(Part file, Long userId,String context);

    public String coverUrltmp(String username);
    
    public void editProfile(String firstName, String lastName, String phone, String city, String country, String briefDescription, Double height, Double weight, String gender, Long userId);

    public void createExperience(Long userID,String title, String description, Date realisationDate, String experienceCity, String experienceCityStat, String experienceCityStreet, Integer experienceCityZipcode);
    public void editExperience(Long experienceID,Long userID, String title, String description, Date realisationDate, String experienceCity, String experienceCityStat, String experienceCityStreet, Integer experienceCityZipcode);

    public void deleteExperience(Long id, Long experienceId);

    
}
