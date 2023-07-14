package zw.co.zimttech.abn.service;

import org.springframework.stereotype.Service;
import zw.co.zimttech.abn.dto.DiabeticDTO;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.entity.Patient;
import zw.co.zimttech.abn.repository.DiabeticScreeningRepository;
import zw.co.zimttech.abn.repository.PatientRepository;
import zw.co.zimttech.abn.repository.generic.GenericRepository;
import zw.co.zimttech.abn.service.generics.GenericService;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DiabeticService extends GenericService<DiabeticScreening, String> {
    private DiabeticScreeningRepository diabeticScreeningRepository;
    private PatientRepository patientRepository;

    public DiabeticService(GenericRepository<DiabeticScreening> genericRepository,
                           DiabeticScreeningRepository diabeticScreeningRepository, PatientRepository patientRepository) {
        super(genericRepository);
        this.diabeticScreeningRepository = diabeticScreeningRepository;
        this.patientRepository = patientRepository;
    }

    public List<DiabeticScreening> findListByPatientId(String id) {
        return Optional.of(diabeticScreeningRepository.findDiabeticScreeningsByPatientId(id)).get();
    }
   // Search is for patients above 18 and have been on treatment for the past 18months



    public List<DiabeticDTO> search(Float weightMin, Float weightMax, Float heightMin, Float heightMax, Float systolicBloodPressureMin, Float systolicBloodPressureMax,Float diastolicBloodPressureMin, Float diastolicBloodPressureMax, Float bloodGlucoseMin, Float bloodGlucoseMax, LocalDate treatmentDate) {
        List<DiabeticDTO> diabeticDTOList = new ArrayList<>();
        List<DiabeticScreening> diabeticScreeningList = diabeticScreeningRepository.search(weightMin, weightMax, heightMin, heightMax, systolicBloodPressureMin, systolicBloodPressureMax,diastolicBloodPressureMin, diastolicBloodPressureMax, bloodGlucoseMin, bloodGlucoseMax, treatmentDate);
        if (!diabeticScreeningList.isEmpty()) {
            for (int i = 0; i < diabeticScreeningList.size(); i++) {
                Patient patient = patientRepository.findById(diabeticScreeningList.get(i).getPatientId()).get();

                if(patient.getAge() >= 18){
                    DiabeticDTO diabeticDTO = new DiabeticDTO();
                    diabeticDTO.setId(diabeticScreeningList.get(i).getId());
                    diabeticDTO.setPatient(patient);
                    diabeticDTO.setHeight(diabeticScreeningList.get(i).getHeight());
                    diabeticDTO.setWeight(diabeticScreeningList.get(i).getWeight());
                    diabeticDTO.setBloodGlucose(diabeticScreeningList.get(i).getBloodGlucose());
                    diabeticDTO.setBloodPressure(diabeticScreeningList.get(i).getBloodPressure());
                    diabeticDTO.setDiastolicBloodPressure(diabeticScreeningList.get(i).getDiastolicBloodPressure());
                    diabeticDTO.setSystolicBloodPressure(diabeticScreeningList.get(i).getSystolicBloodPressure());
                    diabeticDTO.setTreatmentStartDate(diabeticScreeningList.get(i).getTreatmentStartDate());
                    diabeticDTOList.add(diabeticDTO);
                }

            }
            return diabeticDTOList;
        }
        return diabeticDTOList;
    }
}
