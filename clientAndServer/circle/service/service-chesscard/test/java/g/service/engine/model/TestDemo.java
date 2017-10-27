package g.service.engine.model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Created by LENOVO on 2016/11/15.
 */
public class TestDemo {
    void test() {
        for (int i = 100000; i >= 0; i--) {//最大循环次数100000
            if (fun1()) {
                break;//得到结果返回
            }
            if (i == 0) { //循环次数超过最大值,进行错误处理
                //TODO log Error
                break;
            }
        }
    }

    int testRate() {
        Random ran = new Random();
        int[] rates = {20, 30, 50};
        while (true) {
            for (int i = 0; i < rates.length; i++) {
                if (ran.nextInt(100) < rates[i]) {
                    return i;
                }
            }
        }
    }

    int testRate1() {
        Random ran = new Random();
        int[] rates = {20, 50, 100};
        int ranVal = ran.nextInt(100);
        for (int i = 0; i < rates.length; i++) {
            if (ranVal < rates[i]) {
                return i;
            }
        }
        return 0;
    }


    void getTens(ArrayList<Integer> list) {
        //找出链表中任意3个数总和是10的倍数
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.size(); j++) {
                for (int k = 0; k < list.size(); k++) {
                    if (i != j && i != k && j != k) {
                        if (list.get(i) + list.get(j) + list.get(k) % 10 == 0) {
                            return;//找到这三个数
                        }
                    }
                }
            }
        }
    }

    void getTens1(ArrayList<Integer> list) {
        //找出链表中任意3个数总和是10的倍数
        for (int i = 0, len = list.size(); i < len; i++) {
            for (int j = i + 1; j < len; j++) {
                for (int k = j + 1; k < len; k++) {
                    if (list.get(i) + list.get(j) + list.get(k) % 10 == 0) {
                        return;//找到这三个数
                    }
                }
            }
        }
    }

    void delEvenNum(ArrayList<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            Integer val = list.get(i);
            if (val % 2 == 0) {//根据值来判断是否删除
                list.remove(val);
            }
        }
    }

    void delEvenNum1(ArrayList<Integer> list) {
        for (int i = list.size() - 1; i >= 0; i--) {
            Integer val = list.get(i);
            if (val % 2 == 0) {//根据值来判断是否删除
                list.remove(i);
            }
        }
        //或者必须从索引值0或者其他开始的情况
        for (int i = 0, len = list.size(); i < len; i++) {
            Integer val = list.get(i);
            if (val % 2 == 0) {//根据值来判断是否删除
                list.remove(i);
                i--;
                len--;
            }
        }
    }


    boolean fun1() {
        int count = 0;
        while (count < 200000) {//循环条件
            count++;
            if (count == 210000) {
                break;//不可能触发的break
            }
        }
        return true;
    }

    static void fun(int num){
        if(num<5){
            throw new RuntimeException("xxx");
        }
    }
    static HashMap<Integer,String> fun1(HashMap<Integer,String> rs, int num){
        if(num<5){
            rs.put(1, "xxx");
            return rs;
        }
        return rs;
    }

    public static void main(String args[]) {
        int max = 10000000;
        long count = 0;
        long now = System.currentTimeMillis(),useTime = 0;
        for (int i = 0; i < max; i++) {
            try{
                fun(i%10);
            }catch (RuntimeException e){
                count++;
            }
        }
        useTime = -(now - (now = System.currentTimeMillis()));
        HashMap<Integer, String> maps = new HashMap<Integer, String>();
        System.out.println("Except: "+useTime+"ms :"+count);
        now = System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            if(fun1(maps, i%10).get(1) != null){
                count++;
            }
        }
        useTime = -(now - (now = System.currentTimeMillis()));
        System.out.println("return: "+useTime+"ms :"+count);
    }
}
