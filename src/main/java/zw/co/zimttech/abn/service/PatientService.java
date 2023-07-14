package zw.co.zimttech.abn.service;

import org.springframework.stereotype.Service;
import zw.co.zimttech.abn.dto.PatientDTO;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.entity.Patient;
import zw.co.zimttech.abn.repository.DiabeticScreeningRepository;
import zw.co.zimttech.abn.repository.PatientRepository;
import zw.co.zimttech.abn.repository.generic.GenericRepository;
import zw.co.zimttech.abn.service.generics.GenericService;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PatientService extends GenericService<Patient, String> {

    private PatientRepository patientRepository;
    private DiabeticScreeningRepository diabeticScreeningRepository;

    public PatientService(GenericRepository<Patient> genericRepository, PatientRepository patientRepository, DiabeticScreeningRepository diabeticScreeningRepository) {
        super(genericRepository);
        this.patientRepository = patientRepository;
        this.diabeticScreeningRepository = diabeticScreeningRepository;
    }

    public List<PatientDTO> getPatientWithDiagnosticList() {
        List<Patient> patientList = patientRepository.findAll();
        List<PatientDTO> patientDTOList = new ArrayList<>();
        List<DiabeticScreening> diabeticScreeningList = new ArrayList<>();
        if (!patientList.isEmpty()) {
            for (int i = 0; i < patientList.size(); i++) {
                diabeticScreeningList = diabeticScreeningRepository.findDiabeticScreeningsByPatientId(patientList.get(i).getId());
                PatientDTO patientDTO = new PatientDTO();
                patientDTO.setAge(patientList.get(i).getAge());
                patientDTO.setGender(patientList.get(i).getGender());
                patientDTO.setFirstName(patientList.get(i).getFirstName());
                patientDTO.setMobileNumber(patientList.get(i).getMobileNumber());
                patientDTO.setDiabeticScreeningList(diabeticScreeningList);
                patientDTO.setId(patientList.get(i).getId());
                patientDTOList.add(patientDTO);
            }
            return patientDTOList;
        }
        return null;
    }

    public List<PatientDTO> getPatientsByAgeAfter(Integer age) {
        List<Patient> patientList = patientRepository.findPatientByAgeAfter(age);
        List<PatientDTO> patientDTOListByAge = new ArrayList<>();
        List<DiabeticScreening> diabeticScreeningList = new ArrayList<>();
        if (!patientList.isEmpty()) {
            for (int i = 0; i < patientList.size(); i++) {
                diabeticScreeningList = diabeticScreeningRepository.findDiabeticScreeningsByPatientId(patientList.get(i).getId());
                PatientDTO patientDTO = new PatientDTO();
                patientDTO.setAge(patientList.get(i).getAge());
                patientDTO.setGender(patientList.get(i).getGender());
                patientDTO.setFirstName(patientList.get(i).getFirstName());
                patientDTO.setMobileNumber(patientList.get(i).getMobileNumber());
                patientDTO.setDiabeticScreeningList(diabeticScreeningList);
                patientDTO.setId(patientList.get(i).getId());
                patientDTOListByAge.add(patientDTO);
            }
            return patientDTOListByAge;
        }
        return null;
    }

    public List<PatientDTO> getPatientsByAgeAfter18() {
        List<Patient> patientList = patientRepository.findPatientByAgeAfter(18);
        List<PatientDTO> patientDTOListByAge = new ArrayList<>();
        List<DiabeticScreening> diabeticScreeningList = new ArrayList<>();
        if (!patientList.isEmpty()) {
            for (int i = 0; i < patientList.size(); i++) {
                diabeticScreeningList = diabeticScreeningRepository.findDiabeticScreeningsByPatientId(patientList.get(i).getId());
                LocalDate localDate = LocalDate.now().minusMonths(18);
                //This picks all adults over 18 months of treatment
                if(diabeticScreeningList.size() > 0 && diabeticScreeningList.get(0).getTreatmentStartDate().isBefore(localDate)){
                    PatientDTO patientDTO = new PatientDTO();
                    patientDTO.setAge(patientList.get(i).getAge());
                    patientDTO.setGender(patientList.get(i).getGender());
                    patientDTO.setFirstName(patientList.get(i).getFirstName());
                    patientDTO.setMobileNumber(patientList.get(i).getMobileNumber());
                    patientDTO.setDiabeticScreeningList(diabeticScreeningList);
                    patientDTO.setId(patientList.get(i).getId());
                    patientDTOListByAge.add(patientDTO);
                }

            }
            return patientDTOListByAge;
        }
        return null;
    }
}
