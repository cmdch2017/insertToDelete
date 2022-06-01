package learn;

import java.io.*;

/**
 * alter table to TRYADDTABCOLUMN
 */
public class Study2 {

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
                    if ((lineTxt.contains("alter table")||lineTxt.contains("ALTER TABLE"))&& (lineTxt.contains("add")||lineTxt.contains("ADD")) && lineTxt.contains(";")) {
                        recordSql = lineTxt;
                    } else if ((lineTxt.contains("alter table")||lineTxt.contains("ALTER TABLE"))&& (lineTxt.contains("add")||lineTxt.contains("ADD"))) {
                        Boolean recordFlag = true;
                        recordSql += lineTxt;
                        while (recordFlag && (lineTxt = bufferedReader.readLine()) != null) {
                            recordSql += lineTxt;
                            if (lineTxt.contains(";")) {
                                recordFlag = false;
                            }
                        }
                    }else{
                        lineTxt = new String(lineTxt.getBytes(), encoding);
                        fileLineTxt+=lineTxt+"\n";
                    }
                    if(!recordSql.equals("")) {
                        String convertReq=convert(recordSql)+"\n";
                        apis+=convertReq;
                        fileLineTxt+=convertReq;
                        recordSql="";
                    }
                }
                outPutFile(fileLineTxt,outPutFilePath,encoding);
                System.out.println(fileLineTxt);
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
        String filePath = "D:\\insertToDelete\\3.sql";
        String outPutFilePath="D:\\insertToDelete\\result3.sql";
        readTxtFile(filePath,outPutFilePath,encoding);
    }

    public static String convert(String sql) {
        String result = "CALL TRYADDTABCOLUMN('";
        sql = sql.replace("alter table ", "");
        sql = sql.replace("ALTER TABLE ", "");
        String table = sql.substring(0, sql.indexOf(" "));
        sql = sql.replace(table+" ", "");
        sql =sql.replace("add ","");
        sql =sql.replace("ADD ","");
        String column = sql.substring(0,sql.indexOf(" "));
        sql = sql.replace(column+" ", "");
        String type=sql.substring(0,sql.indexOf(";"));
        result += table + "','"+column+"','"+ type+"')";
        result+='\n'+"/";
        return result;
    }
}
