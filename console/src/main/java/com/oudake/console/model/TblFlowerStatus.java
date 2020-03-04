package com.oudake.console.model;

import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author wangyi
 */
@Table(name = "TBL_FLOWER_STATUS")
public class TblFlowerStatus implements Serializable {
    private Integer flowerId;

    private String type;

    private Integer amount;

    private static final long serialVersionUID = 1L;

    public Integer getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Integer flowerId) {
        this.flowerId = flowerId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }
}