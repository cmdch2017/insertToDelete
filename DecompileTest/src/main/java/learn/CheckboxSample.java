package learn;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static utils.FileUtil.configureFileChooser;
import static utils.FileUtil.outPutFile;


public class CheckboxSample extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane gd = new GridPane();
        gd.setBackground(new Background(new BackgroundFill(Color.LIGHTPINK, CornerRadii.EMPTY, Insets.EMPTY)) );
        Scene scene = new Scene(gd);
        FileChooser fileChooser = new FileChooser();
        Button openButton = new Button("请选择sql或txt文件");

        openButton.setOnAction(
                (final ActionEvent event) -> {
                    gd.getChildren().clear();
                    configureFileChooser(fileChooser);
                    File file = fileChooser.showOpenDialog(primaryStage);
                    List<Data> list = Study.readTxtFile(file, "UTF-8");
                    list=duplicateRemoval(list);
                    for (int j = 0; j < list.size(); j++) {
                        FlowPane flowPane = new FlowPane();
                        flowPane.setOrientation(Orientation.VERTICAL);
                        flowPane.setHgap(10);
                        flowPane.setVgap(20);
                        flowPane.setPrefHeight(700);
                        flowPane.setPadding(new Insets(15, 15, 15, 15));
                        String tableName = list.get(j).getTableName();
                        String[] columnNameList = list.get(j).getColumnNameList();
                        int length = columnNameList.length;
                        Button button = new Button(tableName);
                        flowPane.getChildren().add(button);
                        for (int i = 0; i < length; i++) {
                            CheckBox checkBox = new CheckBox(columnNameList[i]);
                            if(i==0){
                                checkBox.setSelected(true);
                            }
                            flowPane.getChildren().add(checkBox);
                        }
                        button.setOnAction(e -> {
                                    List<Integer> returnList = new ArrayList<>();
                                    for (int i = 1; i < length + 1; i++) {
                                        CheckBox b = (CheckBox) (flowPane.getChildren().get(i));
                                        if (b.isSelected()) {
                                            returnList.add(i - 1);
                                        }
                                    }
                                }
                        );
                        gd.add(flowPane, j, 0);
                    }
                    Button createButton = new Button("一键生成按钮");
                    createButton.setPrefHeight(100);
                    List<Data> finalList = list;
                    createButton.setOnAction(e -> {
                                Map<String, String> map = new HashMap<>();
                                for (int i = 0; i < gd.getChildren().size() - 2; i++) {
                                    FlowPane flowPane = (FlowPane) (gd.getChildren().get(i));
                                    for (int j = 1; j < flowPane.getChildren().size(); j++) {
                                        CheckBox checkBox = (CheckBox) flowPane.getChildren().get(j);
                                        if (checkBox.isSelected()) {
                                            Button b = (Button) (flowPane.getChildren().get(0));
                                            if (map.containsKey(b.getText())) {
                                                map.put(b.getText(), map.get(b.getText()).concat(",").concat(Integer.toString(j - 1)));
                                            } else {
                                                map.put(b.getText(), Integer.toString(j - 1));
                                            }
                                        }
                                    }
                                }
                                String sql=convertFun(map, finalList);
                                try {
                                    outPutFile(sql,file.getAbsolutePath(),"UTF-8");
                                } catch (IOException ioException) {
                                    ioException.printStackTrace();
                                }
                            }
                    );
                    gd.add(createButton, 0, 1);
                    gd.add(openButton, 1, 1);
                });
        gd.add(openButton, 1, 1);
//        checkBox1.selectedProperty().addListener((observable, oldValue, newValue) -> System.out.println("c1状态" + newValue));

        primaryStage.setScene(scene);
        primaryStage.setTitle("Insert to Delete");
        primaryStage.setWidth(800);
        primaryStage.setHeight(700);
        primaryStage.show();
    }

    private String convertFun(Map<String, String> map, List<Data> finalList) {
        if(map.size()!=finalList.size()){
            System.out.println("请选择主键");
            return null;
        }else{
            String resultSql="";
            for(int i=0;i<finalList.size();i++){
                if(map.containsKey(finalList.get(i).getTableName())){
                    String index=map.get(finalList.get(i).getTableName());
                    List<String[]> allValueList=finalList.get(i).getAllValueList();
                    for(int j=0;j<allValueList.size();j++) {
                        String sql = "DELETE FROM " + finalList.get(i).getTableName() + " WHERE ";
                        String[] k = finalList.get(i).getColumnNameList();
                        String[] v=allValueList.get(j);
                        String clause= setKVByIndex(k,v,index);
                        sql+=clause;
                        resultSql+=sql;
                        System.out.println(sql);
                    }
                }
            }
            return resultSql;
        }
    }

    private String setKVByIndex(String[] k, String[] v, String index) {
        String sql="";
        if(index!=null){
            String[] indexArray=index.split(",");
            for(int i=0;i<indexArray.length;i++){
                sql+=k[Integer.parseInt(indexArray[i])]+" = "+v[Integer.parseInt(indexArray[i])];
                if(i!=indexArray.length-1) {
                    sql += " AND ";
                }else{
                    sql+=";\n";
                }
            }
        }
        return sql;
    }

    private List<Data> duplicateRemoval(List<Data> list) {
        List<Data> resultList=new ArrayList<>();
        Map<String,List<Data>> map=list.stream().collect(Collectors.groupingBy(Data::getTableName));
        for (String key : map.keySet()) {
           List<Data> dataList=map.get(key);
           Data data=combine(dataList);
           resultList.add(data);
        }
        return resultList;
    }

    private Data combine(List<Data> dataList) {
        Data data=new Data();
        data.setTableName(dataList.get(0).getTableName());
        data.setColumnNameList(dataList.get(0).getColumnNameList());
        for(Data a:dataList) {
            if(data.getAllValueList()!=null) {
                data.getAllValueList().add(a.getValueList());
            }else{
                List<String[]> list =new ArrayList<>();
                list.add(a.getValueList());
                data.setAllValueList(list);
            }
        }
        return data;
    }


}
