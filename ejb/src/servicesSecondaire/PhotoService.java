/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.AlbumEntity;
import dao.PhotoEntity;
import dao.PostEntity;
import dao.ProfileEntity;
import dao.UserEntity;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.ejb.Local;
import javax.servlet.http.Part;

/**
 *
 * @author zakaridia
 */
@Local
public interface PhotoService{

    /**
     * upload the file
     * @param file
     * @param username
     * @param album
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    public PhotoEntity upload(Part file ,String username , AlbumEntity album,String contextPath)throws FileNotFoundException, IOException;

    /**
     * create a photo
     * @param p
     */
    public void add(PhotoEntity p);

    /**
     * update the photo
     * @param photo
     */
    public void update(PhotoEntity photo);

    /**
     * return a photo matching the given photo id
     * 
     * @param id
     * @return PhotoEntity
     */
    public PhotoEntity find(Long id);


    /**
     * delete the given photo
     * @param photo
     */
    public void delete(PhotoEntity photo);
    
    public void createDefaultProfilePhotos(UserEntity p);


    public PostEntity createPhoto(AlbumEntity album, UserEntity author, Part file,String contextPath,Boolean display);
    
}
