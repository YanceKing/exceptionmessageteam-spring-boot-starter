package com.yance.pojos.dingding;

/**
 * 异常通知 钉钉结果
 *
 * @author yance
 * @version 1.0
 * @since 2020/04/01
 */
public class DingDingResult {

    /**
     * 错误编码
     */
    private int errcode;

    /**
     * 错误描述
     */
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "DingDingResult [errcode=" + errcode + ", errmsg=" + errmsg + "]";
    }

}
