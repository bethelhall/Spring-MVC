package com.atech.yekatit_care.Repositories;

import com.atech.yekatit_care.Domains.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("PatientRepository")
public interface PatientRepository extends CrudRepository<Patient, Integer> {
    Patient findById(int id);
    public List<Patient> findAllByOrderByName();
    public  List<Patient> findAllByOrderById();
    public List<Patient> findPatientsByOrderByName();
    public void deletePatientById(int id);
    List<Patient> findByAssignedDoctor(String doctor);



}

