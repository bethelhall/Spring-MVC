package com.atech.yekatit_care.Repositories;

import com.atech.yekatit_care.Domains.Test;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends CrudRepository<Test, Integer> {


}
