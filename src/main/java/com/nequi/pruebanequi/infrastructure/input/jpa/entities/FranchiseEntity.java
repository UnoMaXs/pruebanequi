package com.nequi.pruebanequi.infrastructure.input.jpa.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import static com.nequi.pruebanequi.infrastructure.input.jpa.constants.Constants.TABLE_FRANCHISE_NAME;


@Table(TABLE_FRANCHISE_NAME)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class FranchiseEntity {
    @Id
    private Integer id;
    private String name;
}
