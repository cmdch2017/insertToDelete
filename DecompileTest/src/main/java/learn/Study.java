package learn;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Study {
    public static List<Data> readTxtFile(File file, String encoding) {
        try {
            List<Data> dataList = new ArrayList<>();
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    Data data = new Data();
                    Boolean flag = false;
                    while (lineTxt != null && !(lineTxt.contains("insert into") || lineTxt.contains("INSERT INTO"))) {
                        lineTxt = bufferedReader.readLine();
                    }
                    if (lineTxt == null) {
                        break;
                    }
                    if (lineTxt.contains("insert into") || lineTxt.contains("INSERT INTO")) {
                        data = convertInsert(lineTxt, data);
                        flag = true;
                    }
                    while (lineTxt != null && !(lineTxt.contains("values") || lineTxt.contains("VALUES"))) {
                        lineTxt = bufferedReader.readLine();
                    }
                    if (lineTxt == null) {
                        break;
                    }
                    if (flag && (lineTxt.contains("values") || lineTxt.contains("VALUES"))) {
                        data = convertValues(lineTxt, data);
                        flag = false;
                    }
                    if (data.getTableName() != null) {
                        dataList.add(data);
                    }
                }
                read.close();
                return dataList;
            } else {
                System.out.println("\u627E\u4E0D\u5230\u6307\u5B9A\u7684\u6587\u4EF6");
            }
        } catch (Exception e) {
            System.out.println("\u8BFB\u53D6\u6587\u4EF6\u5185\u5BB9\u51FA\u9519");
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String argv[]) {
        String filePath = "D:\\insertToDelete\\1.sql";
        List<Data> list = readTxtFile(new File(filePath), "UTF-8");
        System.out.println(list);
    }

    public static Data convertInsert(String sql,  Data data) {
        sql = sql.toUpperCase();
        sql = sql.replace("INSERT INTO ", "");
        sql = sql.replace("insert into ", "");
        String table = sql.substring(0, sql.indexOf("(")).replaceAll(" ","");
        sql = sql.replace(table, "").replace("'", "");
        String[] values = sql.split("VALUES");
        String[] k = values[0].replace("(", "").replace(")", "").split(",");
        data.setTableName(table);
        data.setColumnNameList(k);
        return data;
    }

    public static Data convertValues(String sql, Data data) {
        String[] values;
        if (sql.contains("VALUES")) {
            values = sql.split("VALUES");
        } else {
            values = sql.split("values");
        }
        String[] v = values[1].replace("(", "").replace(")", "").split(",");
        v[v.length - 1] = v[v.length - 1].substring(0, v[v.length - 1].length() - 1);
        data.setValueList(v);
        return data;
    }
}
