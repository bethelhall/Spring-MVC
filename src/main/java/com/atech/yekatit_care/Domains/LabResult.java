package com.atech.yekatit_care.Domains;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "lab_result")
public class LabResult {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "result_id")
    private int result_id;

    @Column(name = "testRequest_id")
    private int testRequest_id;

    @Column(name = "labTechnician_name")
    private String labTechnician_name;

    @ManyToMany(targetEntity=Result.class)
    private List<Result> lab_result;
}
