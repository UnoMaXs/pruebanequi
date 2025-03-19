package com.nequi.pruebanequi.infrastructure.input.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.nequi.pruebanequi.infrastructure.input.jpa.constants.Constants.TABLE_PRODUCT_BRANCH_NAME;


@Table(TABLE_PRODUCT_BRANCH_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BranchProductEntity {
    @Id
    private Integer id;
    @Column("branch_id")
    private Integer branchId;
    @Column("product_id")
    private Integer productId;
}
