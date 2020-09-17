package com.crazymakercircle.zk.Balance;

import com.crazymakercircle.util.JsonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Data
@Slf4j
public class Manger {

    //static和transient修饰的变量，将不参加序列化

    //Zk客户端
    private transient CuratorFramework client = null;

    //工作节点的路径
    private String mangerPath = "/test/balance/worker";

    public Manger(String host, String port,
                  CuratorFramework client, String mangerPath) {
        this.client = client;
        this.mangerPath = mangerPath;
    }

    protected Worker balance(List<Worker> items) {
        if (items.size() > 0) {
            // 根据负载由小到大排序
            Collections.sort(items);

            // 返回负载最小的那个
            return items.get(0);
        } else {
            return null;
        }
    }


    /**
     * 从zookeeper中拿到所有节点的基本信息
     */
    protected List<Worker> getWorkers() {

        List<Worker> workers = new ArrayList<Worker>();

        List<String> children = null;
        try {
            children = client.getChildren().forPath(mangerPath);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        for (String child : children) {
            log.info("child:", child);
            byte[] payload = null;
            try {
                payload = client.getData().forPath(child);

            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == payload) {
                continue;
            }
            Worker worker = JsonUtil.JsonBytes2Object(payload, Worker.class);
            workers.add(worker);
        }


        return workers;

    }


}