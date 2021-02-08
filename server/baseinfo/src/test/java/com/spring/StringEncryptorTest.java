/*
package com.spring;

import com.spring.common.cache.TempCaheUtil;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes= BaseInfoApplication.class)
public class StringEncryptorTest
{
    @Autowired
    StringEncryptor stringEncryptor;

    @Test
    public void encryptAccount()
    {
        String decrypt = stringEncryptor.decrypt("Kb5x6djOREouZq0LKuvwJXP13bxcP9g1");
        System.out.println("decrp:"+decrypt);
        String decrypt1 = stringEncryptor.decrypt("RE7+OOlqkOrb7uz2ON0AAA==");
        System.out.println("decrp1:"+decrypt1);
        TempCaheUtil.putTempCahce("name", "Joe");
        String data = TempCaheUtil.getTempCahce("name").toString();
        System.out.printf(data);

       */
/* String path = System.getProperty("user.dir");

        // 创建Cache管理器
        CacheManager manager = CacheManager.create(path + "/src/main/resources/cache/ehcache.xml");
        // 获取指定Cache
        Cache cache = manager.getCache("tempCache");
        // 把一个元素添加到Cache中
        cache.put(new Element("name", "Joe"));
        // 根据Key获取缓存元素
        Element ele = cache.get("name");*//*

        */
/*System.out.println("name==" + ele.getObjectValue());

        cache.flush(); // 刷新缓存
        manager.shutdown();  // 关闭缓存管理器*//*


    }


}

*/
