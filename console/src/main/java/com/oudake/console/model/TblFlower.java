package com.oudake.console.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author wangyi
 */
@Table(name = "TBL_FLOWER")
public class TblFlower implements Serializable {

    @Transient
    private String typeName;

    @Transient
    private String fatherName;

    private Integer flowerId;

    @NotBlank(message = "花名不能为空")
    private String flowerName;

    @NotBlank(message = "机构号不能为空")
    private String orgCode;

    private String pictureUrl;

    @NotNull(message = "价格不能为空")
    @Min(message = "价格不能为负数", value = 0)
    private Long price;

    @NotNull(message = "折扣不能为空")
    @Min(message = "折扣不能为负数", value = 0)
    private Float discount;

    @NotNull(message = "库存不能为空")
    @Min(message = "库存不能为负数", value = 0)
    private Integer stock;

    @NotBlank(message = "状态不能为空")
    private String flowerStatus;

    private String material;

    private String packWay;

    private String meaning;

    private String addition;

    private String sendWay;

    private String description;

    private String reserved1;

    private String reserved2;

    private String reserved3;

    private static final long serialVersionUID = 1L;

    public Integer getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(Integer flowerId) {
        this.flowerId = flowerId;
    }

    public String getFlowerName() {
        return flowerName;
    }

    public void setFlowerName(String flowerName) {
        this.flowerName = flowerName;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getFlowerStatus() {
        return flowerStatus;
    }

    public void setFlowerStatus(String flowerStatus) {
        this.flowerStatus = flowerStatus;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }

    public String getPackWay() {
        return packWay;
    }

    public void setPackWay(String packWay) {
        this.packWay = packWay;
    }

    public String getMeaning() {
        return meaning;
    }

    public void setMeaning(String meaning) {
        this.meaning = meaning;
    }

    public String getAddition() {
        return addition;
    }

    public void setAddition(String addition) {
        this.addition = addition;
    }

    public String getSendWay() {
        return sendWay;
    }

    public void setSendWay(String sendWay) {
        this.sendWay = sendWay;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}