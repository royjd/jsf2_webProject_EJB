/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.AlbumEntity;
import dao.PhotoDAO;
import dao.PhotoEntity;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author zakaridia
 */
@Stateless
public class PhotoServiceImpl implements PhotoService {

    @EJB
    PhotoDAO photoDao;

    /**
     *
     * @param p
     */
    @Override
    public void add(PhotoEntity p) {
        Long id = photoDao.save(p);
        p.setId(id);
    }

    /**
     *
     * @param photo
     */
    @Override
    public void update(PhotoEntity photo) {
        photoDao.update(photo);
    }

    /**
     *
     * @param id
     * @return
     */
    @Override
    public PhotoEntity find(Long id) {
        return photoDao.find(id);
    }


    /**
     *
     * @param photo
     */
    @Override
    public void delete(PhotoEntity photo) {
        photoDao.delete(photo);
    }

    @EJB
    ServletContext servletContext;
    
    /**
     *
     * @param file
     * @param username
     * @param album
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public PhotoEntity upload(File file , String username , AlbumEntity album) throws FileNotFoundException, IOException {

        String fileName = file.getName();
        
        if(!isValidExtension(fileName)){
            return null;
        }
        
        String rootPath = servletContext.getRealPath("/resources/img");
        String albumName = album.getTitle();
        if(!"DefaulAlbum".equals(albumName) && !"NewsAlbum".equals(albumName) && !"ProfileAlbum".equals(albumName)){
            albumName = "Album_"+album.getId();
        }
        String path = rootPath + File.separator +"Medias"+File.separator+username + File.separator + "Albums"+File.separator+albumName;
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.canWrite()){
            OutputStream out = new FileOutputStream(path + File.separator + fileName);

            InputStream filecontent =  new FileInputStream(file);
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            PhotoEntity photo = new PhotoEntity("/Medias/"+username+"/Albums/"+albumName+"/"+fileName);
            this.add(photo);
            return photo;
        }

        return null;
    }

    private String getFileName(final Part part) {
        final String partHeader = part.getHeader("content-disposition");
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    private String getFileExtension(String name) {
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private Boolean goodExtension(String ex) {
        if(ex.equals("")){
            return false;
        }
        String[] extensions = new String[]{"jpg", "jpeg", "png"};
        return Arrays.asList(extensions).contains(ex.toLowerCase());
    }


    private boolean isValidExtension(String filename){
        return goodExtension(getFileExtension(filename)) ;
    }

}
