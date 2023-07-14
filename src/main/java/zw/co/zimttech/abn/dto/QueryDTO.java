package zw.co.zimttech.abn.dto;

import lombok.Data;

@Data
public class QueryDTO {
    private Float weightMin;
    private Float weightMax;
    private Float heightMin;
    private Float heightMax;
    private Float systolicBloodPressureMin;
    private Float systolicBloodPressureMax;
    private Float diastolicBloodPressureMin;
    private Float diastolicBloodPressureMax;
    private Float bloodGlucoseMin;
    private Float bloodGlucoseMax;
}

