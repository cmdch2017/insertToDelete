import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Window;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class Controller implements Initializable {

    @FXML
    private GridPane rootPane;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<FileMeta> fileTable;

    @FXML
    private ChoiceBox outBhEncodingCbx;

    private Thread task;

    public void initialize(URL location, ResourceBundle resources){
        //界面初始化，需要初始化数据和表
//        DBInit.init();
        //添加搜索框监听器，内容改变时添加监听时间
        outBhEncodingCbx.getItems().addAll("1");
        searchField.textProperty().addListener((observable, oldValue, newValue) -> freshTable());
    }
    /**
     * 点击选择文件
     * @param event
     */
    public void choose(Event event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose File");
        Window window = rootPane.getScene().getWindow();
        fileChooser.getExtensionFilters().addAll( new FileChooser.ExtensionFilter("Sql Files", "*.sql"));
        List<File> file = fileChooser.showOpenMultipleDialog(window);
        if (file != null) {
            List<String> list=file.stream().map(m->m.getAbsolutePath()).collect(Collectors.toList());
            String fileListStr="";
            if(list==null||list.size()==0){

            }else {
                for (int i = 0; i < list.size(); i++) {
                    if (i != list.size() - 1) {
                        fileListStr += list.get(i) + ";";
                    } else {
                        fileListStr += list.get(i);
                    }
                }
            }
            searchField.setText(fileListStr);
        }
    }

    private void freshTable(){
        ObservableList<FileMeta> metas = fileTable.getItems();
        metas.clear();
        String dir = searchField.getText();
        if (dir != null && dir.trim().length() != 0){
            List<FileMeta> fileMetas = new ArrayList<>();
            String[] dirArray=dir.split(";");
            for(String a : dirArray){
                fileMetas.add(new FileMeta(new File(a)));
            }
            metas.addAll(fileMetas);
        }
    }

    public void deletePrimary(MouseEvent mouseEvent) {
        System.out.println(outBhEncodingCbx.getValue());
        if(searchField.getText().isEmpty()){
            return;
        }
        for(String a:searchField.getText().split(";")){
            String outFilePath= a.substring(0, a.lastIndexOf("\\"));
            String name=a.substring(a.lastIndexOf("\\")).replace(".sql","");
            Main.readTxtFile(a,outFilePath+"\\"+name+"_"+"result.sql","UTF-8","0");
        }

    }
}
