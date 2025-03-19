package com.nequi.pruebanequi.domain.model.models;

public class BranchProduct {
    private Integer id;
    private Integer branchId;
    private Integer productId;

    public BranchProduct(Integer id, Integer branchId, Integer productId) {
        this.id = id;
        this.branchId = branchId;
        this.productId = productId;
    }

    public BranchProduct() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }
}