package com.atech.yekatit_care.Repositories;

import com.atech.yekatit_care.Domains.Result;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResultRepository extends CrudRepository<Result, Integer> {
}
