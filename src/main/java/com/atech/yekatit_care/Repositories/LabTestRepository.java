package com.atech.yekatit_care.Repositories;

import com.atech.yekatit_care.Domains.LabTest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LabTestRepository extends CrudRepository<LabTest, Integer > {
//    List<LabTest> findAllByDoctor_email(String doctor_email);

    @Query("SELECT lt FROM LabTest lt WHERE lt.patient_id = ?1")
    LabTest findByPatient_id(int id);

    @Query("SELECT lt FROM LabTest lt WHERE lt.test_id = ?1")
    LabTest findByTest_id(int id);

}
