package zw.co.zimttech.abn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "diabetic_screening")
@Data
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class DiabeticScreening extends BaseID {
    private String patientId;
    private Float weight;
    private Float height;
    private Float bloodPressure;
    private Float bloodGlucose;
    private LocalDate treatmentStartDate = LocalDate.now();
    private LocalDate treatmentDate = LocalDate.now();
    private Float systolicBloodPressure;
    private Float diastolicBloodPressure;
}
