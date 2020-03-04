package com.oudake.console.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author wangyi
 */
@Table(name = "TBL_GATEWAY_ORDER")
public class TblGatewayOrder implements Serializable {

    @Transient
    private String realName;

    @Transient
    private String userPhone;

    @Transient
    private String userAddr;

    private Integer gatewayOrdId;

    private String gatewaySysTime;

    private String txnType;

    private Integer userId;

    private String currency;

    private BigDecimal txnAmt;

    private String txnRemark;

    private String status;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private static final long serialVersionUID = 1L;

    public Integer getGatewayOrdId() {
        return gatewayOrdId;
    }

    public void setGatewayOrdId(Integer gatewayOrdId) {
        this.gatewayOrdId = gatewayOrdId;
    }

    public String getGatewaySysTime() {
        return gatewaySysTime;
    }

    public void setGatewaySysTime(String gatewaySysTime) {
        this.gatewaySysTime = gatewaySysTime;
    }

    public String getTxnType() {
        return txnType;
    }

    public void setTxnType(String txnType) {
        this.txnType = txnType;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public BigDecimal getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(BigDecimal txnAmt) {
        this.txnAmt = txnAmt;
    }

    public String getTxnRemark() {
        return txnRemark;
    }

    public void setTxnRemark(String txnRemark) {
        this.txnRemark = txnRemark;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReserved1() {
        return reserved1;
    }

    public void setReserved1(String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getReserved2() {
        return reserved2;
    }

    public void setReserved2(String reserved2) {
        this.reserved2 = reserved2;
    }

    public String getReserved3() {
        return reserved3;
    }

    public void setReserved3(String reserved3) {
        this.reserved3 = reserved3;
    }

    public String getUserAddr() {
        return userAddr;
    }

    public void setUserAddr(String userAddr) {
        this.userAddr = userAddr;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }
}