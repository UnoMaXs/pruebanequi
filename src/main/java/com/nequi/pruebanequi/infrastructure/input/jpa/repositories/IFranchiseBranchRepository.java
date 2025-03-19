package com.nequi.pruebanequi.infrastructure.input.jpa.repositories;

import com.nequi.pruebanequi.infrastructure.input.jpa.entities.FranchiseBranchEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IFranchiseBranchRepository extends ReactiveCrudRepository<FranchiseBranchEntity, Integer> {
}
