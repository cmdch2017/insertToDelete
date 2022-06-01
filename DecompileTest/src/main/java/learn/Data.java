package learn;

import java.util.Arrays;
import java.util.List;

public class Data {
    private String tableName;
    private String[] ColumnNameList;
    private String[] valueList;
    private List<String[]> allValueList;

    public List<String[]> getAllValueList() {
        return allValueList;
    }

    public void setAllValueList(List<String[]> allValueList) {
        this.allValueList = allValueList;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String[] getColumnNameList() {
        return ColumnNameList;
    }

    public void setColumnNameList(String[] columnNameList) {
        ColumnNameList = columnNameList;
    }

    public String[] getValueList() {
        return valueList;
    }

    public void setValueList(String[] valueList) {
        this.valueList = valueList;
    }

    public Data() {
    }

    @Override
    public String toString() {
        return "Data{" +
                "tableName='" + tableName + '\'' +
                ", ColumnNameList=" + Arrays.toString(ColumnNameList) +
                ", valueList=" + Arrays.toString(valueList) +
                '}';
    }
}
