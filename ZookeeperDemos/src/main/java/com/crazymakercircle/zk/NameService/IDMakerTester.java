package com.crazymakercircle.zk.NameService;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
@Slf4j
public class IDMakerTester {

    @Test
    public void testMakeId() {
        IDMaker idMaker = new IDMaker();
        idMaker.init();
        String nodeName = "/test/IDMaker/ID-";

        for (int i = 0; i < 10; i++) {
            String id = idMaker.makeId(nodeName);
            log.info("第" + i + "个创建的id为:" + id);
        }
        idMaker.destroy();

    }
}
