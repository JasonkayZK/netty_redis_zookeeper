package com.crazymakercircle.zk.publishSubscribe;

import com.crazymakercircle.zk.ZKclient;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/

@Slf4j
@Data
public class ConfigSubscriber {
    private String workerPath = "/test/config";
    CuratorFramework client = ZKclient.instance.getClient();

    public void init() {

        try {
            PathChildrenCache cache =
                    new PathChildrenCache(client, workerPath, true);
            PathChildrenCacheListener l =
                    new PathChildrenCacheListener() {
                        @Override
                        public void childEvent(CuratorFramework client,
                                               PathChildrenCacheEvent event) {
                            ChildData data = event.getData();
                            switch (event.getType()) {
                                case CHILD_ADDED:
                                    setConfig(data);
                                    break;
                                case CHILD_UPDATED:
                                    updateConfig(data);
                                    break;
                                case CHILD_REMOVED:
                                    removeConfig(data);
                                    break;
                                default:
                                    break;
                            }
                        }
                    };
            cache.getListenable().addListener(l);
            cache.start(PathChildrenCache.StartMode.BUILD_INITIAL_CACHE);
        } catch (Exception e) {
            log.error("PathCache监听失败, path=", workerPath);
        }

    }

    private void setConfig(ChildData data) {

        log.info("子节点增加, path={}, data={}",
                data.getPath(), data.getData());

        ConfigManger.instance.setType(data.getPath(), data.getData());

    }

    private void updateConfig(ChildData data) {

        log.info("子节点更新, path={}, data={}",
                data.getPath(), data.getData());

        ConfigManger.instance.setType(data.getPath(), data.getData());

    }

    private void removeConfig(ChildData data) {

        log.info("子节点更新, path={}, data={}",
                data.getPath(), data.getData());

        ConfigManger.instance.removeType(data.getPath());

    }

}
