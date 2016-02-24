/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package commun;
import java.io.InputStream;
/**
 *
 * @author zdiawara
 */
public class Files {
    String name;
    InputStream content;

    public Files(String name, InputStream content) {
        this.name = name;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public InputStream getContent() {
        return content;
    }

}
