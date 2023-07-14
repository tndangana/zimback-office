package zw.co.zimttech.abn.dto;

import lombok.Data;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.entity.Gender;


import java.util.List;
@Data
public class PatientDTO {
    private String id;
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String mobileNumber;
    List<DiabeticScreening> diabeticScreeningList;
}
