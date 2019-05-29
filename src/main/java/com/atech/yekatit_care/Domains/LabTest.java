package com.atech.yekatit_care.Domains;

import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Entity
@Table(name = "lab_test")
public class LabTest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "test_id")
    private int test_id;

//    @ManyToOne
//    @JoinColumn(name = "patient_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private int patient_id;

//    @ManyToOne
//    @JoinColumn(name = "user_id", nullable = false)
//    @OnDelete(action = OnDeleteAction.CASCADE)
    private String doctor_email;

    @ManyToMany(targetEntity=Test.class)
    @Size(min=1, message="You must select at least 1 lab test")
    private List<Test> lab_request;

}
