package com.devops.backend.persistence.repositories;

import com.devops.backend.persistence.domain.backend.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by ALadin Zaier PC IBS on 07/02/2017.
 */

@Repository
public interface PlanRepository extends CrudRepository<Plan,Integer>{
}
