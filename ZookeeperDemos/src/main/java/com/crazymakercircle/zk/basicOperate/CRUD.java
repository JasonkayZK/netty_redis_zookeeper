package com.crazymakercircle.zk.basicOperate;

import com.crazymakercircle.zk.ClientFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.utils.CloseableUtils;
import org.apache.zookeeper.AsyncCallback;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.junit.Test;

import java.util.List;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
public class CRUD {
    private static final String ZK_ADDRESS = "127.0.0.1:2181";

    /**
     * 检查节点
     */
    @Test
    public void checkNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            // 创建一个 ZNode 节点
            // 节点的数据为 payload

            String zkPath = "/test/CRUD/remoteNode-1";
            Stat stat = client.checkExists().forPath(zkPath);
            if (null == stat) {
                log.info("节点不存在:", zkPath);
            } else {

                log.info("节点存在 stat is:", stat.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }


    /**
     * 创建节点
     */
    @Test
    public void createNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            // 创建一个 ZNode 节点
            // 节点的数据为 payload

            String data = "hello";
            byte[] payload = data.getBytes("UTF-8");
            String zkPath = "/test/CRUD/remoteNode-1";
            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.PERSISTENT)
                    .forPath(zkPath, payload);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }


    /**
     * 创建 临时 节点
     */
    @Test
    public void createEphemeralNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            // 创建一个 ZNode 节点
            // 节点的数据为 payload

            String data = "hello";
            byte[] payload = data.getBytes("UTF-8");
            String zkPath = "/test/remoteNode-2";
            client.create()
                    .creatingParentsIfNeeded()

                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(zkPath, payload);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    /**
     * 创建 持久化 顺序 节点
     */
    @Test
    public void createPersistentSeqNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            // 创建一个 ZNode 节点
            // 节点的数据为 payload

            String data = "hello";

            for (int i = 0; i < 10; i++) {
                byte[] payload = data.getBytes("UTF-8");
                String zkPath = "/test/remoteNode-seq-";
                client.create()
                        .creatingParentsIfNeeded()
                        .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                        .forPath(zkPath, payload);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    /**
     * 读取节点
     */
    @Test
    public void readNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            String zkPath = "/test/CRUD/remoteNode-1";


            Stat stat = client.checkExists().forPath(zkPath);
            if (null != stat) {
                //读取节点的数据
                byte[] payload = client.getData().forPath(zkPath);
                String data = new String(payload, "UTF-8");
                log.info("read data:", data);

                String parentPath = "/test";
                List<String> children = client.getChildren().forPath(parentPath);

                for (String child : children) {
                    log.info("child:", child);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }


    /**
     * 更新节点
     */
    @Test
    public void updateNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();


            String data = "hello world";
            byte[] payload = data.getBytes("UTF-8");
            String zkPath = "/test/remoteNode-1";
            client.setData()
                    .forPath(zkPath, payload);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }


    /**
     * 更新节点 - 异步模式
     */
    @Test
    public void updateNodeAsync() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {

            //更新完成监听器
            AsyncCallback.StringCallback callback = new AsyncCallback.StringCallback() {

                @Override
                public void processResult(int i, String s, Object o, String s1) {
                    System.out.println(
                            "i = " + i + " | " +
                                    "s = " + s + " | " +
                                    "o = " + o + " | " +
                                    "s1 = " + s1
                    );
                }
            };
            //启动客户端实例,连接服务器
            client.start();

            String data = "hello ,every body! ";
            byte[] payload = data.getBytes("UTF-8");
            String zkPath = "/test/CRUD/remoteNode-1";
            client.setData()
                    .inBackground(callback)
                    .forPath(zkPath, payload);

            Thread.sleep(10000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }

    /**
     * 删除节点
     */
    @Test
    public void deleteNode() {
        //创建客户端
        CuratorFramework client = ClientFactory.createSimple(ZK_ADDRESS);
        try {
            //启动客户端实例,连接服务器
            client.start();

            //删除节点
            String zkPath = "/test/CRUD/remoteNode-1";
            client.delete().forPath(zkPath);


            //删除后查看结果
            String parentPath = "/test";
            List<String> children = client.getChildren().forPath(parentPath);

            for (String child : children) {
                log.info("child:", child);
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            CloseableUtils.closeQuietly(client);
        }
    }


}
