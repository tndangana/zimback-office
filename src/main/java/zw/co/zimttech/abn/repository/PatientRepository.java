package zw.co.zimttech.abn.repository;



import zw.co.zimttech.abn.entity.Gender;
import zw.co.zimttech.abn.entity.Patient;
import zw.co.zimttech.abn.repository.generic.GenericRepository;

import java.util.List;
public interface PatientRepository  extends GenericRepository<Patient> {
    List<Patient> findPatientsByAgeAfter(int age);
    List<Patient> findPatientsByGender(Gender gender);

    List<Patient> findPatientByAgeAfter(Integer age);

}
