package com.ck.tools.m;

/**
 * @Author caikun
 * @Description 封装请求
 * @Date 下午5:28 21-5-28
 **/
public class CommonRequest {

    public CommonRequest(long ts, Object req) {
        this.ts = ts;
        this.req = req;
    }

    private long ts;

    private Object req;

    public long getTs() {
        return ts;
    }

    public void setTs(long ts) {
        this.ts = ts;
    }

    public Object getReq() {
        return req;
    }

    public void setReq(Object req) {
        this.req = req;
    }

    @Override
    public String toString() {
        return ts + "|" + req + "|" + super.toString();
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }
}
