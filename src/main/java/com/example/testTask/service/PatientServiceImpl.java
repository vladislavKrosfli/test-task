package com.example.testTask.service;

import com.example.testTask.entity.Patient;
import com.example.testTask.repository.PatientRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PatientServiceImpl implements PatientService {

    private PatientRepository patientRepository;

    @Override
    public List<Patient> getPatients(){
        return patientRepository.findAll();
    }

    @Override
    public Patient getById(UUID id) {
        return patientRepository.findById(id).orElse(null);
    }

    @Override
    public Patient create(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public void delete(UUID id) {
        patientRepository.deleteById(id);
    }
}
