package cs421projevt1;

import java.util.ArrayList;  
import java.util.Iterator;  
import java.util.List;  
  
import javax.swing.text.StyledEditorKit.ItalicAction;  
  
public class autoJudge {  
    private String judgeString; // ����������ַ���  
    private List<String> wrongChar = null;// �ռ��´����ĸ  
    private List<String> correctChar = null;// �ռ��¶Ե���ĸ  
    private ParseToken parseToken = null;// ��ʼ�ĺ��ǵ���λ�����ַ�������  
    private List<String> nextAllString = null;// ���ɸѡ֮����ַ�������  
    private char[] test = null;// �����ַ�������¼�¶Ե���ĸ����ĸ���ڵ�λ��  
    private int[] num = null; // ��¼��ĸ���ַ���ƥ���еĸ�����λ��  
    private int count = 0;// ��¼�´�Ĵ��� ���Ϊ8��  
    boolean resu = false;// ���Ϊtrue ��Ϊ�³��������Ϊû�³���  
  
    // ������Ҫƥ����ַ�������ԭʼ����λ���ļ���  
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
  
        // �µ�һ����ĸ;  
        char c1 = getFirst();  
        // �ж��Ƿ���ȷ�����󷵻�-1�� ��ȷ������ȷ��λ����������num ����  
        /* 
         * ��һ����������Ϊ�����ظ�����ĸ����,���� hello,������ĸ l ʱ����ô���صĽ������2��num�м�¼2��l ���ڵ�λ�� 
         */  
        int isHas = containKey(c1);  
        // begin  
        // �õ���һ��������ĸ  
        if (isHas == -1) {  
            count++;// �´�������� 1  
            wrongChar.add(c1 + "");  
            // ������������ĸ�ĵ���ȫɾ������������λ���ĵ��ʿ�  
            deleteDoc(c1);  
            // ����һ����ĸ  
            char c2 = getNext();  
            isHas = containKey(c2);  
            if (isHas == -1) {  
                /* ѭ���£�ֱ���ҵ���һ��ƥ��ĵ��� */  
                while (isHas == -1) {  
                    count++;  
                    wrongChar.add(c2 + "");  
                    // ���ַ�ɾ��  
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
            // �¶��˵�һ����ĸ֮����ȫƥ��ɾ��  
            deleteDoc3();  
            nextBack();  
        }  
        if (resu) {  
            System.out.println("����������ַ����ǣ�" + judgeString);  
            System.out.println("����ƥ��Ĵ���Ϊ��" + count);  
            System.out.print(" ����ƥ����ַ�/�ַ����ֱ�Ϊ��");  
            for (int i = 0; i < wrongChar.size(); i++)  
                System.out.print(wrongChar.get(i) + " ");  
            System.out.println();  
            System.out.print("��ȷƥ����ַ�Ϊ��");  
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
         * getNext2() �������ж���һ��ȡ����ĸ�����Ѿ�����ȷƥ���ַ����е���ĸ 
         */  
        char c = getNext2();  
        int isHas = containKey(c);  
        if (isHas == -1) {  
            count++;  
            wrongChar.add(c + "");  
            deleteDoc2(c);// ���ַ�ƥ��ɾ��  
        } else {  
            correctChar.add(c + "");  
            // ����test  
            for (int i = 0; i < isHas; i++) {  
                test[num[i]] = c;  
            }  
            deleteDoc3();// ȫƥ��ɾ��  
        }  
        // ʣ�൥�ʷ���  
        if (!parseRemain() || !isSuccess()) {  
            nextBack();  
        } else  
            return;  
    }  
  
    // ����������----������  
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
  
    // ʣ����ַ�������������ҵ��򷵻�true ���򷵻�false;  
    /* 
     * ���ʣ����ַ�С�ڻ����Դ���Ĵ�������Ա�֤�ó���ȷ���. 
     * */  
    private boolean parseRemain() {  
        if (nextAllString.size() <= (8 - count)) {  
            Iterator<String> it = nextAllString.iterator();  
            while (it.hasNext()) {  
                String tmp = it.next();  
                if (tmp.equals(judgeString)) {  
                    resu = true;  
                    System.out.println("_________________��ʣ��������ҵ�����ǣ�" + tmp);  
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
        // ͳ�Ƴ��ֵ���ĸ  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // �޳������õķ��� ����'-'  
                if (tmp.charAt(i) < 'A')  
                    break;  
                ch[tmp.charAt(i) - 'A'] = true;  
            }  
        }  
        // �õ����ֵ���ĸ�����ĵ��ʰ�����  
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
 * �жϵ�ǰ�ĵ����Ƿ��Ѿ����ֹ� 
 * ���ֹ�����true 
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
  
    // ƥ��ɹ����� true ���� false;  
    private boolean isSuccess() {  
        int i;  
        for (i = 0; i < judgeString.length(); i++) {  
            if (judgeString.charAt(i) != test[i])  
                break;  
        }  
        if (i != judgeString.length())  
            return false;  
        // ֱ�Ӵ�ӡ���  
        System.out.print("��ǰ�������ҵ��Ľ��__________:");  
        for (i = 0; i < test.length; i++)  
            System.out.print(test[i]);  
        System.out.println();  
        resu = true;  
        return true;  
    }  
/* 
 * ȫ�ַ�ƥ��ɾ�� 
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
 * ���ַ�ƥ��ɾ�� 
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
 * ����һ�����ܳ��ֵ���ĸ 
 * */  
    private char getNext() {  
  
        boolean[] ch = new boolean[100];  
        Iterator<String> it = nextAllString.iterator();  
        // ͳ�Ƴ��ֵ���ĸ  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // �޳������õķ��� ����'-'  
                if (tmp.charAt(i) < 'A')  
                    break;  
                ch[tmp.charAt(i) - 'A'] = true;  
            }  
        }  
        // �õ����ֵ���ĸ�����ĵ��ʰ�����  
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
 * �õ���ĸ�ڵ�ǰ�����ַ����г��ֵĸ��� 
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
 * �жϲµ���ĸ�Ƿ���ƥ����ַ����У���� 
 * ������ô���ش��ڵ�λ�������򷵻�-1 
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
 * ��һ�β� 
 * */  
    private char getFirst() {  
        int length = judgeString.length();  
  
        boolean[] ch = new boolean[100];  
        Iterator<String> it = parseToken.tokenString[length].iterator();  
        while (it.hasNext()) {  
            String tmp = it.next();  
            for (int i = 0; i < tmp.length(); i++) {  
                // �޳������õķ��� ����'-'  
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