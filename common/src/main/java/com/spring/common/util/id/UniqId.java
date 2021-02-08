/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.spring.common.util.id;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.locks.ReentrantLock;

/**
* @author 作者：赵进华 
* @version 创建时间：2019年3月21日 上午9:36:54
* @Desc类说明:生成唯一的ID
*/

public class UniqId {
    private static final Logger log    = LoggerFactory.getLogger(UniqId.class);
    private static final char[]       digits = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'
    };
    public static String   DIGEST_ALG="sha1";

    private static final UniqId me       = new UniqId();
    private String        hostAddr;
    private final Random        random  = new SecureRandom();
    private MessageDigest mHasher;
    private final UniqTimer     timer   = new UniqTimer();

    private final ReentrantLock opLock = new ReentrantLock();

    private UniqId() {
        try {
            InetAddress addr = InetAddress.getLocalHost();

            hostAddr = addr.getHostAddress();
        } catch (IOException e) {
            log.error("[UniqID] Get HostAddr Error", e);
            hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (null == hostAddr || hostAddr.trim().length() == 0
                || "127.0.0.1".equals(hostAddr)) {
            hostAddr = String.valueOf(System.currentTimeMillis());
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID]hostAddr is:" + hostAddr);
        }

        try {
            mHasher = MessageDigest.getInstance(DIGEST_ALG);
        } catch (NoSuchAlgorithmException nex) {
            mHasher = null;
            log.error("[UniqID]new {}  Hasher error",DIGEST_ALG, nex);
        }
    }

    /**
     * 获取UniqID实例
     * @return UniqId
     */
    public static UniqId getInstance() {
        return me;
    }

    /**
     * 获得不会重复的毫秒数
     * @return 不会重复的时间
     */
    public long getUniqTime() {
        return timer.getCurrentTime();
    }

    /**
     * 获得UniqId
     * @return uniqTime-randomNum-hostAddr-threadId
     */
    public String getUniqId() {
        StringBuffer sb = new StringBuffer();
        long         t = timer.getCurrentTime();

        sb.append(t);

        sb.append("-");

        sb.append(random.nextInt(8999) + 1000);

        sb.append("-");
        sb.append(hostAddr);

        sb.append("-");
        sb.append(Thread.currentThread().hashCode());

        if (log.isDebugEnabled()) {
            log.debug("[UniqID.getUniqID]" + sb.toString());
        }

        return sb.toString();
    }

    /**
     * 获取MD5之后的uniqId string
     * @return uniqId md5 string
     */
    public String getUnIqIdHashString() {
        return hashString(getUniqId());
    }

    /**
     * 获取MD5之后的uniqId
     * @return uniqId md5 byte[16]
     */
    public byte[] getUnIqIdHash() {
        return hash(getUniqId());
    }

    /**
     * 对字符串进行md5
     * @param str
     * @return md5 byte[16]
     */
    public byte[] hash(String str) {
        opLock.lock();
        try {
            byte[] bt = mHasher.digest(str.getBytes());
            return bt;
        }finally {
            opLock.unlock();
        }
    }

    /**
     * 对字符串进行md5 string
     * @param str
     * @return md5 string
     */
    public String hashString(String str) {
        byte[] bt = hash(str);
        return bytes2string(bt);
    }

   private  static final char[] hexDigit = {'0', '1', '2', '3', '4', '5', '6', '7',
            '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String bytes2string(byte[] b) {
        StringBuffer buf = new StringBuffer();
        for (int j=0; j<b.length; j++) {
            buf.append(hexDigit[(b[j] >> 4) & 0x0f]);
            buf.append(hexDigit[b[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * 将一个字节数组转化为可见的字符串
     * @param bt
     * @return 每个字节两位，如f1d2
     */
    public String bytes2stringx(byte[] bt) {
        int    l = bt.length;

        char[] out = new char[l << 1];

        for (int i = 0, j = 0; i < l; i++) {
            out[j++]     = digits[(0xF0 & bt[i]) >>> 4];
            out[j++]     = digits[0x0F & bt[i]];
        }

        if (log.isDebugEnabled()) {
            log.debug("[UniqID.hash]" + (new String(out)));
        }

        return new String(out);
    }

    /**
     * 实现不重复的时间
     * @author dogun
     */
    private class UniqTimer {
        private final AtomicLong lastTime = new AtomicLong(System.currentTimeMillis());

        public long getCurrentTime() {
            return this.lastTime.incrementAndGet();
        }
    }
}
