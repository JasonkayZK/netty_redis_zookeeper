package com.crazymakercircle.zk.masterSelector;

import com.crazymakercircle.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/

@Slf4j
@Data
public class Candidate {

    //Zk客户端
    private CuratorFramework client = null;

    //工作节点的路径
    private String workerPath = "/test/balance/worker";

    private String host;
    private String port;
    private String name;

    // 争抢Master
    private void takeMaster() {

        try {
            byte[] payload = JsonUtil.Object2JsonBytes(this);
            // 尝试创建Master临时节点
            client.create()
                    .creatingParentsIfNeeded()
                    .withProtection()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(workerPath, payload);

        } catch (Exception e) {

            log.info("抢夺master 失败:" + name);
        }

    }

    // 释放Master
    private void releaseMaster() throws Exception {
        byte[] payload = client.getData()
                .forPath(workerPath);

        Candidate candidate =
                JsonUtil.JsonBytes2Object(payload, Candidate.class);

        if (candidate.getName().equals(this.getName())) {
            client.delete().forPath(workerPath);
        }

    }


}
