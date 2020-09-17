package com.crazymakercircle.zk.NameService;

import com.crazymakercircle.util.JsonUtil;
import com.crazymakercircle.zk.ZKclient;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Data
public class SnowflakeIdWorker {

    //Zk客户端
    private CuratorFramework client = null;

    //工作节点的路径
    private String pathPrefix = "/test/IDMaker/worker-";
    private String pathRegistered = null;

    public static SnowflakeIdWorker instance = new SnowflakeIdWorker();


    private SnowflakeIdWorker() {
        instance.client = ZKclient.instance.getClient();
        instance.init();
    }


    // 在zookeeper中创建临时节点并写入信息
    public void init() {

        // 创建一个 ZNode 节点
        // 节点的 payload 为当前worker 实例

        try {
            byte[] payload = JsonUtil.Object2JsonBytes(this);

            pathRegistered = client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL_SEQUENTIAL)
                    .forPath(pathPrefix, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public long getId() {
        String sid = null;
        if (null == pathRegistered) {
            throw new RuntimeException("节点注册失败");
        }
        int index = pathRegistered.lastIndexOf(pathPrefix);
        if (index >= 0) {
            index += pathPrefix.length();
            sid = index <= pathRegistered.length() ? pathRegistered.substring(index) : null;
        }

        if (null == sid) {
            throw new RuntimeException("节点ID生成失败");
        }

        return Long.parseLong(sid);

    }
}