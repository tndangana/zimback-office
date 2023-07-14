package zw.co.zimttech.abn.service;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import zw.co.zimttech.abn.dto.DiabeticDTO;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.entity.Gender;
import zw.co.zimttech.abn.entity.Patient;
import zw.co.zimttech.abn.repository.DiabeticScreeningRepository;
import zw.co.zimttech.abn.repository.PatientRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DiabeticServiceTests {

    @Autowired
    private DiabeticScreeningRepository diabeticScreeningRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DiabeticService diabeticService;

    @Test
    public void testSearch() {
        // Arrange
        Patient patient = new Patient("1", "John", Gender.MALE, 23,  "1234567890");
        patientRepository.save(patient);

        DiabeticScreening diabeticScreening = new DiabeticScreening();
        diabeticScreening.setPatientId(patient.getId());
        diabeticScreening.setWeight(80f);
        diabeticScreening.setHeight(180f);
        diabeticScreening.setBloodPressure(120f);
        diabeticScreening.setBloodGlucose(5f);
        diabeticScreening.setTreatmentStartDate(LocalDate.now().minusMonths(18));
        diabeticScreeningRepository.save(diabeticScreening);

        // Act
        List<DiabeticDTO> diabeticDTOList = diabeticService.search(70f, 90f, 170f, 190f, 100f, 140f,123f,134f, 4f, 6f, LocalDate.now().minusMonths(18));

        // Assert
        assertNotNull(diabeticDTOList);
        assertEquals(1, diabeticDTOList.size());
        DiabeticDTO diabeticDTO = diabeticDTOList.get(0);
        assertEquals(patient.getId(), diabeticDTO.getPatient().getId());
        assertEquals(patient.getAge(), diabeticDTO.getPatient().getAge());
        assertEquals(patient.getGender(), diabeticDTO.getPatient().getGender());
        assertEquals(patient.getFirstName(), diabeticDTO.getPatient().getFirstName());
        assertEquals(patient.getMobileNumber(), diabeticDTO.getPatient().getMobileNumber());
        assertEquals(diabeticScreening.getWeight(), diabeticDTO.getWeight());
        assertEquals(diabeticScreening.getHeight(), diabeticDTO.getHeight());
        assertEquals(diabeticScreening.getBloodPressure(), diabeticDTO.getBloodPressure());
        assertEquals(diabeticScreening.getBloodGlucose(), diabeticDTO.getBloodGlucose());
        assertEquals(diabeticScreening.getTreatmentStartDate(), diabeticDTO.getTreatmentStartDate());
    }
}
