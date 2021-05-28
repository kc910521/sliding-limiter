package com.ck.tools.k;

import com.ck.tools.m.CommonRequest;

import java.util.concurrent.ConcurrentLinkedDeque;

/**
 * @Author caikun
 * @Description
 * @Date 下午5:30 21-5-28
 **/
public class SlidingHolder {


    private ConcurrentLinkedDeque<CommonRequest> q = new ConcurrentLinkedDeque<>();

    private final int timesLimit = 3;

    private final long periodLimit = 5000L;

    /**
     * 得到本次毫秒ts，
     * 和stamp向前100的ts比对，如果小于limitPerSec则入队
     * @return
     */
    private boolean doRequest(CommonRequest req) {
        // todo：优化
        synchronized (q) {
            if (q.size() < timesLimit) {
                // 可入q
                q.add(req);
                return true;
            } else {
                // 满足 limit的情况
                CommonRequest peek = q.peekFirst();
                if (req.getTs() - peek.getTs() > periodLimit) {
                    // 超过时间限制,出口无效，lpop and rpush
                    q.pollFirst();
                    q.add(req);
                } else {
                    // 未超过时间限制
                    return false;
                }
            }
            return true;
        }

    }

    public boolean doRequest(Object obj) {
        return this.doRequest(new CommonRequest(System.currentTimeMillis(), obj));
    }

    public void clearToStamp() {

    }

    public static void main(String[] args) {
        final SlidingHolder slidingHolder = new SlidingHolder();
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                System.out.println(slidingHolder.doRequest(new Object()));
                try {
                    Thread.sleep(400L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(slidingHolder.doRequest(new Object()));
                try {
                    Thread.sleep(400L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(slidingHolder.doRequest(new Object()));
                try {
                    Thread.sleep(400L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(slidingHolder.doRequest(new Object()));
                System.out.println(slidingHolder.doRequest(new Object()));
                try {
                    Thread.sleep(400L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(slidingHolder.doRequest(new Object()));
                System.out.println("===============");

            }).start();
        }

    }

}
