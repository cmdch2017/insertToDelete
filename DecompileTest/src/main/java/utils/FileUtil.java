package utils;

import javafx.stage.FileChooser;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FileUtil {

    public static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("sql Files", "*.sql"),
                new FileChooser.ExtensionFilter("txt Files", "*.txt")
        );
    }

    public  static void openFile(File file) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(file);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    public static void outPutFile(String apis,String filePath,String encoding) throws IOException {
        String outFilePath= filePath.substring(0, filePath.lastIndexOf("\\"));
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(outFilePath+"\\result.sql"), encoding);
        if(apis!=null) {
            fos.write(apis);
        }else{
            System.out.println("[WARNING] 选择主键后才能生成文件");
        }
        fos.close();
    }
}
