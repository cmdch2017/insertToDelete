import java.io.*;

public class Main2 {

    public static void readTxtFile(String filePath,String outPutFilePath,String encoding) {
        try {
            File file = new File(filePath);
            if (file.isFile() && file.exists()) {
                InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                String apis = "";
                String recordSql = "";
                String fileLineTxt="";
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    lineTxt = new String(lineTxt.getBytes(), encoding);
                    fileLineTxt+=lineTxt+"\n";
                    if ((lineTxt.contains("insert into")||lineTxt.contains("INSERT INTO")) && lineTxt.contains(";")) {
                        recordSql = lineTxt;
                    } else if ((lineTxt.contains("insert into")||lineTxt.contains("INSERT INTO"))) {
                        Boolean recordFlag = true;
                        recordSql += lineTxt;
                        while (recordFlag && (lineTxt = bufferedReader.readLine()) != null) {
                            fileLineTxt+=lineTxt+"\n";
                            recordSql += lineTxt;
                            if (lineTxt.contains(";")) {
                                recordFlag = false;
                            }
                        }
                    }
                    if(!recordSql.equals("")) {
                        apis+=convert(recordSql)+"\n";
                        recordSql="";
                    }
                }
                outPutFile(apis+"\n"+fileLineTxt,outPutFilePath,encoding);
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

    private static void outPutFile(String apis,String outPutFilePath,String encoding) throws IOException {
        OutputStreamWriter fos = new OutputStreamWriter(new FileOutputStream(outPutFilePath), encoding);
        fos.write(apis);
        fos.close();
    }

    public static void main(String argv[]) {
        String encoding="UTF-8";
        String filePath = "D:\\insertToDelete\\1.sql";
        String outPutFilePath="D:\\insertToDelete\\result.sql";
        readTxtFile(filePath,outPutFilePath,encoding);
    }

    public static String convert(String sql) {
        String result = "DELETE FROM ";
        sql = sql.toUpperCase();
        sql = sql.replace("insert into ", "");
        sql = sql.replace("INSERT INTO ", "");
        sql = sql.replace("insert into", "");
        sql = sql.replace("INSERT INTO", "");
        String table = sql.substring(0, sql.indexOf(" "));
        sql = sql.replace(table, "").replace("'", "");
        String[] values = sql.split("VALUES");
        values[1]=values[1].replaceAll(" \\(","");
        values[1]=values[1].replaceAll(", ",",");
        values[1]=values[1].replaceAll(";","");

        String[] k = values[0].replace("(", "").replace(")", "").split(",");
        String[] v = values[1].replace("(", "").replace(")", "").split(",");
        result += table + " WHERE ";
        int length = k.length;
        for (int i = 0; i < length; i++) {
            if(v[i].equalsIgnoreCase("null")){
                result += k[i] + "=" + v[i] ;
            }else {
                result += k[i] + "=" + "'" + v[i] + "'";
            }
            if (i < length - 1) {
                result += " AND ";
            }else{
                result+=";";
            }
        }
        return result;
    }
}
