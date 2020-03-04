package com.oudake.console.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyi
 */
@Table(name = "TBL_FLOWER_TYPE")
public class TblFlowerType implements Serializable {
    private Integer flowerId;

    @NotBlank(message = "种类名不能为空")
    private String typeName;

    @NotBlank(message = "花名不能为空")
    @Transient
    private String flowerName;

    private static final long serialVersionUID = 1L;

    public Integer getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Integer flowerId) {
        this.flowerId = flowerId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }
}