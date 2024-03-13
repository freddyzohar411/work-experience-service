package com.avensys.rts.workexperienceservice.repository;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.avensys.rts.workexperienceservice.entity.WorkExperienceEntity;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class WorkExperienceRepositoryTest {

	@Autowired
	WorkExperienceRepository workExperienceRepository;

	WorkExperienceEntity workExperienceEntity;

	@BeforeEach
	void setUp() {
		workExperienceEntity = new WorkExperienceEntity(1, 1, "entityType", 1, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		workExperienceRepository.deleteAll();
		workExperienceEntity = null;
	}

	@Test
	void testFindByEntityTypeAndEntityId() {
		List<WorkExperienceEntity> workExperienceList = workExperienceRepository
				.findByEntityTypeAndEntityId("entityType", 1);
		assertNotNull(workExperienceList);
	}
}
