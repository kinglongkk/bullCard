package g.service.test;

import com.alibaba.fastjson.JSON;
import g.service.test.netBean.TestNetBean;
import g.service.webSocket.Msg;
import g.service.webSocket.WebSocketServer;
import g.service.webSocket.WebSocketServerHandler;
import g.service.webSocket.codeCreator.NetBeanTool;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.util.ArrayList;
import java.util.Date;

/**
 * 测试网络底层的拆封装
 */
public class TestGameEnDeCode {


    public TestGameEnDeCode() {
    }

    public  void test()throws Exception{
        //100万数据8列,json封装2224,json解封3962,总6186, 系列化封装7083,系列化解封22111,总29194
        // 二进制封装3615,二进制解装2927,总6542
        WebSocketServer wsServer = new WebSocketServer();
        wsServer.setNetBeanPackage("g.service.test.netBean");
        wsServer.setNetHandelPackage("g.service.chesscard.netHandles");
        wsServer.setWebSocketServerHandler(new WebSocketServerHandler());
        wsServer.init();
        long startTime = System.currentTimeMillis();
        int count = 1000000;
        TestNetBean[] games = new TestNetBean[count];
        WebSocketFrame[] jsons = new WebSocketFrame[count];
        ArrayList<Integer> list = new ArrayList<>(6);
        for (int i = 0; i < 6; i++) {
            list.add(i);
        }
        for(int i=0;i<count;i++){
            TestNetBean game = new TestNetBean();
            game.setId(i*i);
            game.setName("斗牛");
            game.setCode("DOU_NIU");
            game.setStatus(i);
            game.setType(i+1);
            game.setCreateTime(System.nanoTime());
            game.setDeleted(false);
            game.setTypes(new int[]{i+1,i+2,i+3,i+4,i+5,i+6});
            game.setList(list);
            games[i]=game;
        }
        TestNetBean nb = JSON.parseObject(JSON.toJSONString(games[0]), TestNetBean.class);
        long bytes = 0;
        startTime = System.currentTimeMillis();
        for(int i=0;i<count;i++) {
            Msg m = new Msg();
            NetBeanTool.writeObject(m,games[i],false);
            bytes+=m.data.writerIndex();
            TestNetBean netBean =(TestNetBean)NetBeanTool.readObject(m,TestNetBean.class);
            m.release();
        }
        System.out.println( bytes+" 二进制封拆装"+(System.currentTimeMillis() - startTime));
        bytes = 0;
        startTime = System.currentTimeMillis();
        for(int i=0;i<count;i++) {
            String json = JSON.toJSONString(games[i]);
            bytes+=json.getBytes("utf8").length;
            games[i]=nb = JSON.parseObject(json, TestNetBean.class);
        }
        System.out.println(bytes+" json拆封装"+(System.currentTimeMillis() - startTime));
        //结果:
        // 78931205 二进制封拆装2211
        //215054849 json拆封装6055
    }
//
//    public  void startServer() throws Exception{
//    }
//
    public static void main(String[] args) throws Exception {
        TestGameEnDeCode gm = new TestGameEnDeCode();
        gm.test();

    }
}
