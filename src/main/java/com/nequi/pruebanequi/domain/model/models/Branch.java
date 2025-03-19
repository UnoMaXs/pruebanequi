package com.nequi.pruebanequi.domain.model.models;

import java.util.List;

public class Branch {
    private Integer id;
    private String name;
    private List<Product> productIds;

    public Branch(Integer id, String name, List<Product> productIds) {
        this.id = id;
        this.name = name;
        this.productIds = productIds;
    }

    public Branch() {
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

    public List<Product> getProductIds() {
        return productIds;
    }

    public void setProductIds(List<Product> productIds) {
        this.productIds = productIds;
    }
}
