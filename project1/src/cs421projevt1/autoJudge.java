package cs421projevt1;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
import javax.swing.text.StyledEditorKit.ItalicAction;  
  
public class autoJudge {  
    private String judgeString; // 随机出来的字符串  
    private List<String> wrongChar = null;// 收集猜错的字母  
    private List<String> correctChar = null;// 收集猜对的字母  
    private ParseToken parseToken = null;// 初始的涵盖单词位数的字符串集合  
    private List<String> nextAllString = null;// 结果筛选之后的字符串集合  
    private char[] test = null;// 测试字符串，记录猜对的字母和字母所在的位置  
    private int[] num = null; // 记录字母在字符串匹配中的个数和位置  
    private int count = 0;// 记录猜错的次数 最大为8；  
    boolean resu = false;// 结果为true 即为猜出结果否则为没猜出来  
  
    // 传入需要匹配的字符串，和原始单词位数的集合  
    autoJudge(String str, ParseToken parseToken) {  
        judgeString = str;  
        this.parseToken = parseToken;  
    }  
  
    public void getResult() {  
        correctChar = new ArrayList<String>();  
        wrongChar = new ArrayList<String>();  
        test = new char[judgeString.length() + 5];  
        for (int i = 0; i < judgeString.length() + 5; i++) {  
            test[i] = ' ';  
        }  
  
        // 猜第一个字母;  
        char c1 = getFirst();  
        // 判断是否正确，错误返回-1， 正确返回正确的位数，并更新num 数组  
        /* 
         * 在一个单词中因为存在重复的字母出现,比如 hello,当猜字母 l 时，那么返回的结果就是2，num中记录2个l 所在的位置 
         */  
        int isHas = containKey(c1);  
        // begin  
        // 拿到第一个符合字母  
        if (isHas == -1) {  
            count++;// 猜错计数器加 1  
            wrongChar.add(c1 + "");  
            // 将包含错误字母的单词全删除，更新所在位数的单词库  
            deleteDoc(c1);  
            // 猜下一个字母  
            char c2 = getNext();  
            isHas = containKey(c2);  
            if (isHas == -1) {  
                /* 循环猜，直到找到第一个匹配的单词 */  
                while (isHas == -1) {  
                    count++;  
                    wrongChar.add(c2 + "");  
                    // 单字符删除  
                    deleteDoc2(c2);  
                    c2 = getNext();  
                    isHas = containKey(c2);  
                    if (count == 8)  
                        break;  
                }  
                correctChar.add(c2 + "");  
                for (int i = 0; i < isHas; i++) {  
                    test[num[i]] = c2;  
                }  
            } else {  
                correctChar.add(c2 + "");  
                for (int i = 0; i < isHas; i++) {  
                    test[num[i]] = c2;  
                }  
            }  
        } else {  
            correctChar.add(c1 + "");  
            for (int i = 0; i < isHas; i++) {  
                test[num[i]] = c1;  
            }  
            nextAllString = parseToken.tokenString[judgeString.length()];  
        }// end  
  
        if (count < 8) {  
            // 猜对了第一个字母之后，做全匹配删除  
            deleteDoc3();  
            nextBack();  
        }  
        if (resu) {  
            System.out.println("随机出来的字符串是：" + judgeString);  
            System.out.println("错误匹配的次数为：" + count);  
            System.out.print(" 错误匹配的字符/字符串分别为：");  
            for (int i = 0; i < wrongChar.size(); i++)  
                System.out.print(wrongChar.get(i) + " ");  
            System.out.println();  
            System.out.print("正确匹配的字符为：");  
            for (int i = 0; i < correctChar.size(); i++) {  
                System.out.print(correctChar.get(i) + " ");  
            }  
            System.out.println();  
        }  
    }  
  
    private void nextBack() {  
        if (resu)  
            return;  
        if (count > 8)  
            return;  
        /* 
         * getNext2() 函数会判断这一次取的字母不是已经在正确匹配字符串中的字母 
         */  
        char c = getNext2();  
        int isHas = containKey(c);  
        if (isHas == -1) {  
            count++;  
            wrongChar.add(c + "");  
            deleteDoc2(c);// 单字符匹配删除  
        } else {  
            correctChar.add(c + "");  
            // 更新test  
            for (int i = 0; i < isHas; i++) {  
                test[num[i]] = c;  
            }  
            deleteDoc3();// 全匹配删除  
        }  
        // 剩余单词分析  
        if (!parseRemain() || !isSuccess()) {  
            nextBack();  
        } else  
            return;  
    }  
  
    // 测试输出结果----调试用  
    // private void put() {  
    // System.out.println(judgeString);  
    // for(int i=0; i < test.length; i++)  
    // System.out.print(test[i]);  
    // System.out.println();  
    // System.out.println(nextAllString.size());  
    // System.out.println("count = " + count);  
    // Iterator<String> it = nextAllString.iterator();  
    // while(it.hasNext()){  
    // System.out.println(it.next());  
    // }  
    //  
    // }  
  
    // 剩余的字符串分析，如果找到则返回true 否则返回false;  
    /* 
     * 如果剩余的字符小于还可以错误的次数则可以保证得出正确结果. 
     * */  
    private boolean parseRemain() {  
        if (nextAllString.size() <= (8 - count)) {  
            Iterator<String> it = nextAllString.iterator();  
            while (it.hasNext()) {  
                String tmp = it.next();  
                if (tmp.equals(judgeString)) {  
                    resu = true;  
                    System.out.println("_________________在剩余分析中找到结果是：" + tmp);  
                    return true;  
                } else {  
                    wrongChar.add(tmp);  
                    count++;  
                }  
            }  
        }  
        return false;  
    }  
  
    private char getNext2() {  
        boolean[] ch = new boolean[100];  
        Iterator<String> it = nextAllString.iterator();  
        // 统计出现的字母  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // 剔除不常用的符号 比如'-'  
                if (tmp.charAt(i) < 'A')  
                    break;  
                ch[tmp.charAt(i) - 'A'] = true;  
            }  
        }  
        // 拿到出现的字母中最大的单词包含量  
        int[] count = new int[100];  
        int max = 0, maxPos = -1;  
        for (int i = 0; i < ch.length; i++) {  
            if (ch[i]) {  
                if (isExist((char) ('A' + i)))  
                    continue;  
                count[i] = getNextCount((char) ('A' + i));  
                if (count[i] > max) {  
                    max = count[i];  
                    maxPos = i;  
                }  
            }  
        }  
        char c = (char) (maxPos + 'A');  
        return c;  
    }  
/* 
 * 判断当前的单词是否已经出现过 
 * 出现过返回true 
 * */  
    private boolean isExist(char c) {  
        Iterator<String> it = correctChar.iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            if (tmp.charAt(0) == c)  
                return true;  
        }  
        return false;  
    }  
  
    // 匹配成功返回 true 否则 false;  
    private boolean isSuccess() {  
        int i;  
        for (i = 0; i < judgeString.length(); i++) {  
            if (judgeString.charAt(i) != test[i])  
                break;  
        }  
        if (i != judgeString.length())  
            return false;  
        // 直接打印结果  
        System.out.print("当前分析中找到的结果__________:");  
        for (i = 0; i < test.length; i++)  
            System.out.print(test[i]);  
        System.out.println();  
        resu = true;  
        return true;  
    }  
/* 
 * 全字符匹配删除 
 * */  
    private void deleteDoc3() {  
        List<String> tmpString = new ArrayList<String>();  
        Iterator<String> it = nextAllString.iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            int j;  
            for (j = 0; j < test.length; j++) {  
                if (test[j] != ' ') {  
                    if (tmp.charAt(j) != test[j])  
                        break;  
                }  
            }  
            if (j == test.length) {  
                tmpString.add(tmp);  
            }  
        }  
        nextAllString = tmpString;  
    }  
/* 
 * 单字符匹配删除 
 * */  
    private void deleteDoc2(char c2) {  
        List<String> tmpString = new ArrayList<String>();  
        Iterator<String> it = nextAllString.iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            int i;  
            for (i = 0; i < tmp.length(); i++) {  
                if (tmp.charAt(i) == c2)  
                    break;  
            }  
            if (i == tmp.length()) {  
                tmpString.add(tmp);  
            }  
        }  
        nextAllString = tmpString;  
    }  
/* 
 * 猜下一个可能出现的字母 
 * */  
    private char getNext() {  
  
        boolean[] ch = new boolean[100];  
        Iterator<String> it = nextAllString.iterator();  
        // 统计出现的字母  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // 剔除不常用的符号 比如'-'  
                if (tmp.charAt(i) < 'A')  
                    break;  
                ch[tmp.charAt(i) - 'A'] = true;  
            }  
        }  
        // 拿到出现的字母中最大的单词包含量  
        int[] count = new int[100];  
        int max = 0, maxPos = -1;  
        for (int i = 0; i < ch.length; i++) {  
            if (ch[i]) {  
                count[i] = getNextCount((char) ('A' + i));  
                if (count[i] > max) {  
                    max = count[i];  
                    maxPos = i;  
                }  
            }  
        }  
        char c = (char) (maxPos + 'A');  
        return c;  
    }  
/* 
 * 拿到字母在当前所有字符串中出现的个数 
 * */  
    private int getNextCount(char c) {  
        Iterator<String> it = nextAllString.iterator();  
        int count = 0;  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                if (tmp.charAt(i) == c) {  
                    count++;  
                    break;  
                }  
            }  
        }  
        return count;  
    }  
  
    private void deleteDoc(char c1) {  
        nextAllString = new ArrayList<String>();  
        int length = judgeString.length();  
        Iterator<String> it = parseToken.tokenString[length].iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            int i;  
            for (i = 0; i < tmp.length(); i++) {  
                if (tmp.charAt(i) == c1)  
                    break;  
            }  
            if (i == tmp.length()) {  
                nextAllString.add(tmp);  
            }  
        }  
    }  
/* 
 * 判断猜的字母是否在匹配的字符串中，如果 
 * 存在那么返回存在的位数，否则返回-1 
 * */  
    private int containKey(char c1) {  
        int tmp[] = new int[10];  
        int length = 0;  
        int i;  
        for (i = 0; i < judgeString.length(); i++) {  
            if (judgeString.charAt(i) == c1) {  
                tmp[length++] = i;  
            }  
        }  
        num = tmp;  
        if (length > 0)  
            return length;  
        else  
            return -1;  
    }  
/* 
 * 第一次猜 
 * */  
    private char getFirst() {  
        int length = judgeString.length();  
  
        boolean[] ch = new boolean[100];  
        Iterator<String> it = parseToken.tokenString[length].iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // 剔除不常用的符号 比如'-'  
                if (tmp.charAt(i) < 'A')  
                    break;  
                ch[tmp.charAt(i) - 'A'] = true;  
            }  
        }  
        int[] count = new int[100];  
        int max = 0, maxPos = -1;  
        for (int i = 0; i < ch.length; i++) {  
            if (ch[i]) {  
                count[i] = getCount((char) ('A' + i));  
                if (count[i] > max) {  
                    max = count[i];  
                    maxPos = i;  
                }  
            }  
        }  
        char c = (char) (maxPos + 'A');  
        return c;  
    }  
  
    private int getCount(char c) {  
        int length = judgeString.length();  
        Iterator<String> it = parseToken.tokenString[length].iterator();  
        int count = 0;  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                if (tmp.charAt(i) == c) {  
                    count++;  
                    break;  
                }  
            }  
        }  
        return count;  
    }  
}  