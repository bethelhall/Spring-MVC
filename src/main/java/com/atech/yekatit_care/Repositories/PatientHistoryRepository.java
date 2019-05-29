package com.atech.yekatit_care.Repositories;

import com.atech.yekatit_care.Domains.PatientHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientHistoryRepository extends CrudRepository<PatientHistory, Integer> {
}
