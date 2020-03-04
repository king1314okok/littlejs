package com.oudake.console.model;

import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author wangyi
 */
@Table(name = "TBL_FLOWER_CODE")
public class TblFlowerCode implements Serializable {
    private Integer typeId;

    @NotBlank(message = "种类名不能为空")
    private String typeName;

    private String fatherName;

    private String sort;

    private static final long serialVersionUID = 1L;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}