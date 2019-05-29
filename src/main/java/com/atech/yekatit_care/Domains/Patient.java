package com.atech.yekatit_care.Domains;

import lombok.Data;
import org.hibernate.annotations.Check;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Data
@Entity
@Table(name = "patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "patient_id")
    private int id;

    @Column(name = "name")
    @NotNull(message = "*Please provide your last name")
    @Size(min = 3, message="Name must be greater than 3 characters")
    private String name;

    @Column(name = "age")
    @Size(min =1 , message="*Age must be provided")
    private String age;

    @Column(name = "gender")
    @NotNull(message = "Gender must be selected")
    @Size(min =1 , message="*Gender must be selected")
    private String gender;

    @Column(name = "address")
    @Size(min = 5,message = "*Please provide your address")
    private String address;
    @Column(name = "phone_no")
    @Size(min=10,message = "*Phone number must be 10 digits")

    private String phone_no;

    @Column(name = "date")
    private Date date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_history_id")
    private PatientHistory patientHistory;
    @Column(name="assignedDoctor")
    @Size(min = 1, message="*A doctor must be assigned")
    @NotNull(message = "*A doctor must be assigned")
    private String assignedDoctor;
}
