package com.nequi.pruebanequi.domain.model.models;

import java.util.List;

public class Franchise {
    private Integer id;
    private String name;
    private List<Branch> branchIds;

    public Franchise(Integer id, String name, List<Branch> branchIds) {
        this.id = id;
        this.name = name;
        this.branchIds = branchIds;
    }

    public Franchise() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Branch> getBranchIds() {
        return branchIds;
    }

    public void setBranchIds(List<Branch> branchIds) {
        this.branchIds = branchIds;
    }
}
