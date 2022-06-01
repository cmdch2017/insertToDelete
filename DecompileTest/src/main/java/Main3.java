import learn.Study2;
import learn.Study3;

import java.awt.*;

import javax.swing.JButton;

import javax.swing.JFileChooser;

import javax.swing.JFrame;

import javax.swing.JTextField;

import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * @author 小虫
 */

class TestFrame extends JFrame {
    public static String encoding="UTF-8";

    static JTextField textField;

    static TestFrame testFrame;

    static JTextField textFieldKeyPosition;


    public static void main(String args[]) {
        testFrame = new TestFrame();
        textField = new JTextField("D:\\insertToDelete\\1.sql");
        JButton button = new JButton("选择文件");
        JButton confirmButton = new JButton("完整删除");
        JButton confirmButton1 = new JButton("主键删除");
        JButton confirmButton2 = new JButton("alter table add转为TRYADDTABCOLUMN");
        JButton confirmButton3 = new JButton("create table转为TRYDROPTABLE");


        JButton utf8Button = new JButton("UTF-8");
        JButton gbkButton = new JButton("GBK");
        textField = new JTextField("D:\\insertToDelete\\1.sql");
        textFieldKeyPosition =new JTextField("0");

        button.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser("./");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("sql文件或文本文件", "sql","txt");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(testFrame);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                textField.setText(chooser.getSelectedFile().getAbsolutePath());
            }
        });
        confirmButton.addActionListener(e -> {
            String outFilePath= textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
            System.out.println(outFilePath);
            Main2.readTxtFile(textField.getText(),outFilePath+"\\resultDetail.sql",encoding);
        });
        confirmButton1.addActionListener(e -> {
            String outFilePath= textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
            System.out.println(outFilePath);
            Main.readTxtFile(textField.getText(),outFilePath+"\\result.sql",encoding,textFieldKeyPosition.getText());
        });
        confirmButton2.addActionListener(e -> {
            String outFilePath= textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
            System.out.println(outFilePath);
            Study2.readTxtFile(textField.getText(),outFilePath+"\\tryAddColumn.sql",encoding);
        });
        confirmButton3.addActionListener(e -> {
            String outFilePath= textField.getText().substring(0, textField.getText().lastIndexOf("\\"));
            System.out.println(outFilePath);
            Study3.readTxtFile(textField.getText(),outFilePath+"\\TRYDROPTABLE.sql",encoding);
        });
        utf8Button.addActionListener(e -> {
            encoding="UTF-8";
        });
        gbkButton.addActionListener(e -> {
            encoding="GBK";
        });
        Panel panel = new Panel();
        panel.add(button);
        panel.add(textField);
        panel.add(confirmButton);
        panel.add(confirmButton1);
        panel.add(textFieldKeyPosition);
        panel.add(utf8Button);
        panel.add(gbkButton);
        panel.add(confirmButton2);
        panel.add(confirmButton3);
        testFrame.add(panel);
        testFrame.setLocation(200,200);
        testFrame.setSize(400, 180);
        testFrame.setVisible(true);
    }

}