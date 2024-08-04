package com.example.testTask.service;

import com.example.testTask.entity.Patient;

import java.util.List;
import java.util.UUID;

public interface PatientService {

    List<Patient> getPatients();

    Patient getById(UUID id);

    Patient create(Patient patient);

    Patient update(Patient patient);

    void delete(UUID id);
}
