package utils;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PinYinUtil {
    /**
     * 中文字符格式
     */
    private static final String CHINESE_PATTERN = "[\\u4E00-\\u9FA5]";//汉字的Unicode范围
    /**
     * 汉语 拼音格式化
     */
    private static final HanyuPinyinOutputFormat FORMAT =new HanyuPinyinOutputFormat();

    static{
        FORMAT.setCaseType(HanyuPinyinCaseType.LOWERCASE);//小写拼音
        FORMAT.setToneType(HanyuPinyinToneType.WITHOUT_TONE);//不带声调
        FORMAT.setVCharType(HanyuPinyinVCharType.WITH_V);//带v
    }

    /**
     * 判断name中是否包含中文
     * @param name
     * @return
     */
    public static boolean containsChinese(String name){
        //正则表达式
        return name.matches(".*"+CHINESE_PATTERN+".*");
    }

    /**
     * 通过文件名获取全拼+拼音首字母
     * 陕西科技大学---》shanxikejidaxue/sxkjdx(不考虑多音字）
     * @param name 文件名
     * @return 全拼+拼音首字母
     */
    public static String[] get(String name){
        String[] result = new String[2];
        StringBuilder all = new StringBuilder();//全部拼音
        StringBuilder first = new StringBuilder();//首字母

        for(char c : name.toCharArray()){//找出每一个中文字符，转换成拼音
            try {
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(c,FORMAT);
                if(pinyins == null || pinyins.length == 0){
                    all.append(c);
                    first.append(c);
                }else{
                    all.append(pinyins[0]);//取该中文字符拼音完整拼写
                    first.append(pinyins[0].charAt(0));//取该中文字符拼音首字母
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                badHanyuPinyinOutputFormatCombination.printStackTrace();
            }
        }
        result[0] = all.toString();
        result[1] = first.toString();
        return result;
    }

    /**
     *
     * @param name 文件名
     * @param fullspell true全拼 ， false为首字母
     * @return
     */
    public static String[][] get(String name, boolean fullspell){
        char[] chars = name.toCharArray();
        String[][] result = new String[chars.length][];
        for(int i = 0; i <chars.length; i++){
            try {
                //去重音调后会有重复  去重操作
                String[] pinyins = PinyinHelper.toHanyuPinyinStringArray(chars[i],FORMAT);
                if(pinyins == null ||pinyins.length == 0){//这种情况是全是英文的情况下
                    result[i] = new String[]{String.valueOf(chars[i])};
                }else{
                    result[i] = unique(pinyins,fullspell);
                }
            } catch (BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination) {
                result[i] = new String[]{String.valueOf(chars[i])};
            }
        }
        return result;
    }

    /**
     *
     * @param array 需要去重的数组
     * @param fullSpell
     * @return
     */
    public static String[] unique(String[] array,boolean fullSpell){
        Set<String> set = new HashSet<>();
        for(String s : array){
            if(fullSpell){
                //全拼
                set.add(s);
            }else{
                set.add(String.valueOf(s.charAt(0)));
            }
        }
        return set.toArray(new String[set.size()]);
    }

    /**
     * 每个中文字符返回的是字符串数组，每两个字符串数组合成一个字符串数组，以此类推
     * @param pinyinArray
     * @return
     */
    public static String[] compose(String[][] pinyinArray){
        if(pinyinArray == null || pinyinArray.length == 0){
            return null;
        }else if(pinyinArray.length == 1){
            return pinyinArray[0];
        }else{
            String result[] = pinyinArray[0];//第0行
            for(int i =1; i < pinyinArray.length; i++){//每次遍历一行的数据
                result = compose(result,pinyinArray[i]);
            }
            return result;
        }
    }
    public static String[] compose(String[] pinyin1,String[] pinyin2){
        String[] result = new String[pinyin1.length * pinyin2.length];
        int k = 0;
        for(int i = 0; i < pinyin1.length; i++){
            for(int j = 0; j < pinyin2.length; j++){
                result[k] = pinyin1[i]+pinyin2[j];
                k++;
            }
        }
        return result;
    }

    public static void main(String[] args) {
//        String name = "陕西科技大学";
//        String[] a = get(name);
//        System.out.println(a[0]);
//        System.out.println(a[1]);
//        System.out.println(containsChinese(name));
//        System.out.println(Arrays.toString(compose(get("abcdefg",true))));
        System.out.println(Arrays.toString(compose(get("陕西科技大学",true))));
        System.out.println(Arrays.toString(compose(get("陕西科技大学",false))));
    }
}
