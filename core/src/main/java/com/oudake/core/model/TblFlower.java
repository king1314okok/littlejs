package com.oudake.core.model;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Objects;

@Table(name = "TBL_FLOWER")
public class TblFlower implements Serializable, Comparable<TblFlower> {

    /**
     * 次数
     */
    @Transient
    private Integer count;

    /**
     * 购买件数
     */
    @Transient
    private Integer amount;

    /**
     * 种类名
     */
    @Transient
    private String typeName;

    /**
     * 父类名
     */
    @Transient
    private String fatherName;

    private Integer flowerId;

    private String flowerName;

    private String orgCode;

    private String pictureUrl;

    private Long price;

    private Float discount;

    private Integer stock;

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

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(TblFlower o) {
        int i = this.getFlowerId() - o.getFlowerId();
        return i;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TblFlower flower = (TblFlower) o;
        return flowerId.equals(flower.flowerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(flowerId);
    }
}