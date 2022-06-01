import java.io.*;

public class Main {
    public static void readTxtFile(String filePath, String outFilePath, String encoding, String keyPosition) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                String apis = "";
                String fileLineTxt = "";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    fileLineTxt += lineTxt + "\n";
                    String param = lineTxt;
                    if (lineTxt.contains("insert into") || lineTxt.contains("INSERT INTO")) {
                        apis += (convertInsert(lineTxt, keyPosition));
                    }
                    lineTxt = param;
                    if (lineTxt.contains("values") || lineTxt.contains("VALUES")) {
                        apis += (convertValues(lineTxt, keyPosition));
                    }
                }
                outPutFile(apis + "\n" + fileLineTxt, outFilePath, encoding);
                System.out.println(apis);
                read.close();
            } else {
                System.out.println("\u627E\u4E0D\u5230\u6307\u5B9A\u7684\u6587\u4EF6");
            }
        } catch (Exception e) {
            System.out.println("\u8BFB\u53D6\u6587\u4EF6\u5185\u5BB9\u51FA\u9519");
            e.printStackTrace();
        }
    }

    private static void outPutFile(String apis, String outFilePath, String encoding) throws IOException {
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(outFilePath), encoding);
        fos.write(apis);
        fos.close();
    }

    public static void main(String argv[]) {
        String filePath = "D:\\insertToDelete\\1.sql";
        String outPutFilePath = "D:\\insertToDelete\\result.sql";
        readTxtFile(filePath, outPutFilePath, "UTF-8", "12");
    }

    public static String convertInsert(String sql, String keyPosition) {
        sql = sql.toUpperCase();
        sql = sql.replace("INSERT INTO ", "");
        String table = sql.substring(0, sql.indexOf(" "));
        sql = sql.replace(table, "").replace("'", "");
        String[] values = sql.split("VALUES");
        String[] k = values[0].replace("(", "").replace(")", "").split(",");
        return "DELETE FROM " + table + " where " + k[Integer.parseInt(keyPosition)] + "=";
    }

    public static String convertValues(String sql, String keyPosition) {
        String[] values;
        if (sql.contains("VALUES")) {
            values = sql.split("VALUES");
        } else {
            values = sql.split("values");
        }
        String[] v = values[1].replace("(", "").replace(")", "").split(",");
        //删除最后一个分号
        v[v.length - 1] = v[v.length - 1].substring(0, v[v.length - 1].length() - 1);
        return v[Integer.parseInt(keyPosition)] + ";" + "\n";
    }
}
