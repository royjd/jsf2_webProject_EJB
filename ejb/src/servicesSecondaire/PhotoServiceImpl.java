/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servicesSecondaire;

import dao.AlbumEntity;
import dao.MediaEntity;
import dao.PhotoDAO;
import dao.PhotoEntity;
import dao.PostDAO;
import dao.PostEntity;
import dao.UserEntity;
import java.io.FileNotFoundException;
import java.io.File;
import java.util.List;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.servlet.http.Part;
import services.PostServiceImpl;

/**
 *
 * @author zakaridia
 */
@Stateless
public class PhotoServiceImpl implements PhotoService {

    @EJB
    PhotoDAO photoDao;

    @EJB
    PostDAO postDao;

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

    /*@EJB
     ServletContext servletContext;*/
    /**
     *
     * @param fileName
     * @param inputstream
     * @param username
     * @param album
     * @param contextPath
     * @return
     * @throws FileNotFoundException
     * @throws IOException
     */
    @Override
    public PhotoEntity upload(String fileName, InputStream inputstream, String username, AlbumEntity album, String contextPath) throws FileNotFoundException, IOException {

        if (!isValidExtension(fileName)) {
            return null;
        }

        String rootPath = contextPath;
        String albumName = album.getTitle();
        if (!"DefaulAlbum".equals(albumName) && !"NewsAlbum".equals(albumName) && !"ProfileAlbum".equals(albumName)) {
            albumName = "Album_" + album.getId();
        }
        String path = rootPath + File.separator + "Medias" + File.separator + username + File.separator + "Albums" + File.separator + albumName;
        File dir = new File(path);

        if (!dir.exists()) {
            dir.mkdirs();
        }

        if (dir.canWrite()) {
            OutputStream out = new FileOutputStream(path + File.separator + fileName);

            InputStream filecontent = inputstream;
            int read;
            final byte[] bytes = new byte[1024];
            while ((read = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
            out.close();
            PhotoEntity photo = new PhotoEntity("Medias/" + username + "/Albums/" + albumName + "/" + fileName);
            this.add(photo);
            return photo;
        }

        return null;
    }

    private String getFileExtension(String name) {
        try {
            return name.substring(name.lastIndexOf(".") + 1);
        } catch (Exception e) {
            return "";
        }
    }

    private String getFileName(final Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(
                        content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return "";
    }

    private Boolean goodExtension(String ex) {
        if (ex.equals("")) {
            return false;
        }
        String[] extensions = new String[]{"jpg", "jpeg", "png"};
        return Arrays.asList(extensions).contains(ex.toLowerCase());
    }

    private boolean isValidExtension(String filename) {
        return goodExtension(getFileExtension(filename));
    }

    @Override
    public PostEntity createPhoto(AlbumEntity album, UserEntity author, String fileName, InputStream inputstream, String contextPath) {
        MediaEntity media = null;
        try {
            PhotoEntity photo = this.upload(fileName, inputstream, author.getUsername(), album, contextPath);
            if (photo != null) {
                media = new MediaEntity();
                if ("DefaulAlbum".equals(album.getTitle())) {
                    media = new MediaEntity(album.getTitle(), album.getBody(), author);
                }
                media.setMediaType(photo);
                media.setAlbum(album);
                return media;
                //post = postService2.createPost(media, author, author, display);
                //this.setAlbumCover(album, post);
            }
        } catch (IOException ex) {
            Logger.getLogger(PostServiceImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return media;
    }


    @Override
    public PostEntity createPhoto(AlbumEntity album, UserEntity author, Part file, String contextPath) {
        try {
            PostEntity p = this.createPhoto(album, author, this.getFileName(file),file.getInputStream(), contextPath); 
            return p;
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void setAlbumCover(AlbumEntity album, MediaEntity m) {
        if (album == null || m == null) {
            return;
        }
        album.setCover(m);
        postDao.update(album);
    }

    @Override
    public MediaEntity createDefaultPhoto(UserEntity u,String mediaName,String photoName, String path) {
        //Profile picture
        PhotoEntity photo = new PhotoEntity(photoName, path);
        this.photoDao.save(photo);
        MediaEntity m = new MediaEntity(mediaName, "", u);
        m.setMediaType(photo);
        return m;
    }

}
