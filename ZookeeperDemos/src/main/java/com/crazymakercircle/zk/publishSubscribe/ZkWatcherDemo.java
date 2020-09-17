package com.crazymakercircle.zk.publishSubscribe;

import com.crazymakercircle.zk.ZKclient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.*;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
@Data
public class ZkWatcherDemo {

    private String workerPath = "/test/listener/remoteNode";
    private String subWorkerPath = "/test/listener/remoteNode/id-";

    @Test
    public void testWatcher() {
        CuratorFramework client = ZKclient.instance.getClient();

        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(workerPath);
        if (!isExist) {
            ZKclient.instance.createNode(workerPath, null);
        }

        try {

            Watcher w = new Watcher() {
                @Override
                public void process(WatchedEvent watchedEvent) {
                    System.out.println("监听到的变化 watchedEvent = " + watchedEvent);
                }
            };

            byte[] content = client.getData()
                    .usingWatcher(w).forPath(workerPath);

            log.info("监听节点内容：" + new String(content));

            // 第一次变更节点数据
            client.setData().forPath(workerPath, "第1次更改内容".getBytes());

            // 第二次变更节点数据
            client.setData().forPath(workerPath, "第2次更改内容".getBytes());

            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testNodeCache() {

        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(workerPath);
        if (!isExist) {
            ZKclient.instance.createNode(workerPath, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();
        try {
            NodeCache nodeCache =
                    new NodeCache(client, workerPath, false);
            NodeCacheListener l = new NodeCacheListener() {
                @Override
                public void nodeChanged() throws Exception {
                    ChildData childData = nodeCache.getCurrentData();
                    log.info("ZNode节点状态改变, path={}", childData.getPath());
                    log.info("ZNode节点状态改变, data={}", new String(childData.getData(), "Utf-8"));
                    log.info("ZNode节点状态改变, stat={}", childData.getStat());
                }
            };
            nodeCache.getListenable().addListener(l);
            nodeCache.start();

            // 第1次变更节点数据
            client.setData().forPath(workerPath, "第1次更改内容".getBytes());
            Thread.sleep(1000);

            // 第2次变更节点数据
            client.setData().forPath(workerPath, "第2次更改内容".getBytes());

            Thread.sleep(1000);

            // 第3次变更节点数据
            client.setData().forPath(workerPath, "第3次更改内容".getBytes());
            Thread.sleep(1000);

            // 第4次变更节点数据
//            client.delete().forPath(workerPath);
            Thread.sleep(Integer.MAX_VALUE);
        } catch (Exception e) {
            log.error("创建NodeCache监听失败, path={}", workerPath);
        }
    }


    @Test
    public void testPathChildrenCache() {

        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(workerPath);
        if (!isExist) {
            ZKclient.instance.createNode(workerPath, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();

        try {
            PathChildrenCache cache =
                    new PathChildrenCache(client, workerPath, true);
            PathChildrenCacheListener l =
                    new PathChildrenCacheListener() {
                        @Override
                        public void childEvent(CuratorFramework client,
                                               PathChildrenCacheEvent event) {
                            try {
                                ChildData data = event.getData();
                                switch (event.getType()) {
                                    case CHILD_ADDED:

                                        log.info("子节点增加, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));

                                        break;
                                    case CHILD_UPDATED:
                                        log.info("子节点更新, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));
                                        break;
                                    case CHILD_REMOVED:
                                        log.info("子节点删除, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));
                                        break;
                                    default:
                                        break;
                                }

                            } catch (
                                    UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    };
            cache.getListenable().addListener(l);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.createNode(subWorkerPath + i, null);
            }

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.deleteNode(subWorkerPath + i);
            }

        } catch (Exception e) {
            log.error("PathCache监听失败, path=", workerPath);
        }

    }

    @Test
    public void testTreeCache() {

        //检查节点是否存在，没有则创建
        boolean isExist = ZKclient.instance.isNodeExist(workerPath);
        if (!isExist) {
            ZKclient.instance.createNode(workerPath, null);
        }

        CuratorFramework client = ZKclient.instance.getClient();

        try {
            TreeCache treeCache =
                    new TreeCache(client, workerPath);
            TreeCacheListener l =
                    new TreeCacheListener() {
                        @Override
                        public void childEvent(CuratorFramework client,
                                               TreeCacheEvent event) {
                            try {
                                ChildData data = event.getData();
                                if (data == null) {
                                    log.info("数据为空");
                                    return;
                                }
                                switch (event.getType()) {
                                    case NODE_ADDED:

                                        log.info("[TreeCache]节点增加, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));

                                        break;
                                    case NODE_UPDATED:
                                        log.info("[TreeCache]节点更新, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));
                                        break;
                                    case NODE_REMOVED:
                                        log.info("[TreeCache]节点删除, path={}, data={}",
                                                data.getPath(), new String(data.getData(), "UTF-8"));
                                        break;
                                    default:
                                        break;
                                }

                            } catch (
                                    UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        }
                    };
            treeCache.getListenable().addListener(l);
            treeCache.start();
            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.createNode(subWorkerPath + i, null);
            }

            Thread.sleep(1000);
            for (int i = 0; i < 3; i++) {
                ZKclient.instance.deleteNode(subWorkerPath + i);
            }
            Thread.sleep(1000);

            ZKclient.instance.deleteNode(workerPath);

            Thread.sleep(Integer.MAX_VALUE);

        } catch (Exception e) {
            log.error("PathCache监听失败, path=", workerPath);
        }

    }


}
