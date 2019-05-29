package com.atech.yekatit_care.Domains;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Data
@Entity
@Table(name = "patient_history")
public class PatientHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "history_id")
    private int history_id;

    @OneToOne(mappedBy = "patientHistory")
    private Patient patient;

    @Column(name = "history_saved_date")
    private Date historySavedDate;

    @PrePersist
    void historySavedDate() {
        this.historySavedDate = new Date();
    }

}
