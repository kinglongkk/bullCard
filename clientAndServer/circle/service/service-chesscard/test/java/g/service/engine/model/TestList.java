package g.service.engine.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

/**
 * 测试链表操作
 * Created by MK on 2016/11/10.
 */
public class TestList {
    ArrayList<Integer> list = new ArrayList<>();
    Random random = new Random();
    public TestList(){
        for (int i = 0; i < 200; i++) {
            list.add(i);
        }
        Collections.shuffle(list);
    }

    @Test
    public  void testListRemoveMK(){
        ArrayList<Integer> list1 = new ArrayList(list.size());
        for (int i = 0; i < 100000; i++) {
            list1.clear();
            list1.addAll(list);
            for (int j = 0; j < 25; j++) {
                int index = random.nextInt(list1.size());
                Integer val = list1.get(index);
                list1.remove(index);
            }
        }
    }
    @Test
    public  void testListRemove(){

        ArrayList<Integer> list1 = new ArrayList(list.size());
        for (int i = 0; i < 100000; i++) {
            list1.clear();
            list1.addAll(list);
            for (int j = 0; j < 25; j++) {
                int index = random.nextInt(list1.size());
                Integer val = list1.get(index);
                list1.remove(val);
            }
        }
    }

}
