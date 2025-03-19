package com.nequi.pruebanequi.infrastructure.input.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import static com.nequi.pruebanequi.infrastructure.input.jpa.constants.Constants.TABLE_FRANCHISE_BRANCH_NAME;


@Table(TABLE_FRANCHISE_BRANCH_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FranchiseBranchEntity {
    @Id
    private Integer id;
    @Column("franchise_id")
    private Integer franchiseId;
    @Column("branch_id")
    private Integer branchId;
}
