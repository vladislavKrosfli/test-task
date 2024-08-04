package com.example.testTask;

import com.example.testTask.entity.Patient;
import com.example.testTask.service.KeycloakService;
import com.example.testTask.service.PatientService;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.LocalDateTime;
import java.util.Random;

@SpringBootApplication
@AllArgsConstructor
public class TestTaskApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(TestTaskApplication.class, args);
	}

	private PatientService patientService;
	private KeycloakService keycloakService;

	@Override
	public void run(String... args) throws Exception {

		String[] genders = {"male", "female"};
		Random random = new Random();

		keycloakService.deleteAllUsers();
		keycloakService.addUser("practitioner1", "password", "ROLE_PRACTITIONER");

		for (int i = 1; i <= 100; i++) {
			Patient patient = new Patient();

			patient.setName("patient" + i);

			int randomValue = random.nextInt(2);

			patient.setGender(randomValue % 2 == 0 ? genders[0] : genders[1]);
			patient.setBirthdate(LocalDateTime.now().minusMonths(i));

			keycloakService.addUser(patient.getName(), "password", "ROLE_PATIENT");
			patientService.create(patient);

		}
	}
}