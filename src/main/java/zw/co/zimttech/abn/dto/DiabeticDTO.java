package zw.co.zimttech.abn.dto;

import lombok.Data;
import zw.co.zimttech.abn.entity.Patient;

import java.time.LocalDate;

@Data
public class DiabeticDTO {
    private String id;
    private Patient patient;
    private Float weight;
    private Float height;
    private Float bloodPressure;
    private Float systolicBloodPressure;
    private Float diastolicBloodPressure;
    private Float bloodGlucose;
    private LocalDate treatmentStartDate = LocalDate.now();
}
