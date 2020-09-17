package com.crazymakercircle.zk.Balance;

import com.crazymakercircle.util.JsonUtil;
import lombok.Data;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Data
public class Worker implements Comparable<Worker> {


    private Integer balance;
    private String id;
    private String host;
    private String port;


    //Zk客户端
    private CuratorFramework client = null;

    //工作节点的路径
    private String workerPath = "/test/balance/worker";

    public Worker(String host, String port,
                  CuratorFramework client, String workerPath) {
        this.host = host;
        this.port = port;
        this.client = client;
        this.workerPath = workerPath;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "host='" + host + '\'' +
                ", port='" + port + '\'' +
                ",balance=" + balance +
                '}';
    }

    public int compareTo(Worker o) {
        return this.getBalance().compareTo(o.getBalance());
    }


    // 在zookeeper中创建临时节点并写入信息
    public void init() {

        // 创建一个 ZNode 节点
        // 节点的 payload 为当前worker 实例

        try {
            byte[] payload = JsonUtil.Object2JsonBytes(this);

            client.create()
                    .creatingParentsIfNeeded()
                    .withMode(CreateMode.EPHEMERAL)
                    .forPath(workerPath, payload);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public boolean addBalance(Integer step) {

        // 增加负载：增加负载，并写回zookeeper
        while (true) {
            try {
                this.setBalance(this.getBalance() + step);
                byte[] payload = JsonUtil.Object2JsonBytes(this);
                client.setData().forPath(workerPath, payload);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }

    public boolean subBalance(Integer step) {

        // 增加负载：增加负载，并写回zookeeper
        while (true) {
            try {
                Integer curBalance = this.getBalance();
                curBalance = curBalance > step ? curBalance - step : 0;
                this.setBalance(curBalance);
                byte[] payload = JsonUtil.Object2JsonBytes(this);
                this.setBalance(this.getBalance() + step);
                client.setData().forPath(workerPath, payload);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

    }


}