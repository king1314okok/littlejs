package com.oudake.common.msg;

import java.io.Serializable;

/**
 * @author wangyi
 */
public class TxnMessage implements Serializable {

    private String key;
    private String txnType;
    private Integer txnId;
    private Integer userId;
    private Integer amount;
    private Long txnAmt;
    private String txnRemark;
    private String msg;
    private boolean success;
    private Object obj;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public Integer getTxnId() {
        return txnId;
    }

    public void setTxnId(Integer txnId) {
        this.txnId = txnId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Long getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(Long txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTxnRemark() {
        return txnRemark;
    }

    public void setTxnRemark(String txnRemark) {
        this.txnRemark = txnRemark;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "TxnMessage{" +
                "txnType='" + txnType + '\'' +
                ", txnId=" + txnId +
                ", userId=" + userId +
                ", amount=" + amount +
                ", txnAmt=" + txnAmt +
                ", txnRemark='" + txnRemark + '\'' +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                '}';
    }
}
