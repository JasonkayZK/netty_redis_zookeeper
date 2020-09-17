package com.crazymakercircle.zk.publishSubscribe;

import com.crazymakercircle.util.JsonUtil;
import com.crazymakercircle.zk.ZKclient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/

@Slf4j
@Data
public class ConfigPublisher {
    private String workerPath = "/test/config";
    CuratorFramework client = ZKclient.instance.getClient();

    public void publish(String t, ConfigItem i) {
        try {
            // 创建一个 ZNode 节点
            // 节点的数据为 payload

            String zkPath = workerPath + "/" + t;
            byte[] payload = JsonUtil.Object2JsonBytes(i);

            client.create()
                    .creatingParentsIfNeeded()
                    .withProtection()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(zkPath, payload);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
