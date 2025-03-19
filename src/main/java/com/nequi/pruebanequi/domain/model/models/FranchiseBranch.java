package com.nequi.pruebanequi.domain.model.models;

public class FranchiseBranch {
    private Integer id;
    private Integer franchiseId;
    private Integer branchId;

    public FranchiseBranch(Integer id, Integer franchiseId, Integer branchId) {
        this.id = id;
        this.franchiseId = franchiseId;
        this.branchId = branchId;
    }

    public FranchiseBranch() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFranchiseId() {
        return franchiseId;
    }

    public void setFranchiseId(Integer franchiseId) {
        this.franchiseId = franchiseId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }
}
