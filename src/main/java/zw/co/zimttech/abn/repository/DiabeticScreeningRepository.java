package zw.co.zimttech.abn.repository;

import org.springframework.data.mongodb.repository.Query;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.repository.generic.GenericRepository;

import java.time.LocalDate;
import java.util.List;

public interface DiabeticScreeningRepository extends GenericRepository<DiabeticScreening> {

    List<DiabeticScreening> getDiabeticScreeningByBloodGlucoseAfter(Float bloodGlucoseLevel);

    List<DiabeticScreening> getDiabeticScreeningsByBloodGlucoseBefore(Float bloodGlucoseLevel);

    List<DiabeticScreening> getDiabeticScreeningsByBloodPressureAfter(Float bloodPressure);

    List<DiabeticScreening> getDiabeticScreeningsByBloodPressureBefore(Float bloodPressure);

    List<DiabeticScreening> findDiabeticScreeningsByTreatmentStartDateAfterOrTreatmentStartDateEquals(LocalDate date);

    List<DiabeticScreening> findDiabeticScreeningsByPatientId(String patientId);


    @Query(value = "{$and: ["
            + "?#{ [0] == null ? {_id: {$ne: null}} : {weight: {$gte: [0]}}} ,"
            + "?#{ [1] == null ? {_id: {$ne: null}} : {weight: {$lte: [1]}}} ,"
            + "?#{ [2] == null ? {_id: {$ne: null}} : {height: {$gte: [2]}}} ,"
            + "?#{ [3] == null ? {_id: {$ne: null}} : {height: {$lte: [3]}}} ,"
            + "?#{ [4] == null ? {_id: {$ne: null}} : {systolicBloodPressure: {$gte: [4]}}} ,"
            + "?#{ [5] == null ? {_id: {$ne: null}} : {systolicBloodPressure: {$lte: [5]}}} ,"
            + "?#{ [6] == null ? {_id: {$ne: null}} : {diastolicBloodPressure: {$gte: [6]}}} ,"
            + "?#{ [7] == null ? {_id: {$ne: null}} : {diastolicBloodPressure: {$lte: [7]}}} ,"
            + "?#{ [8] == null ? {_id: {$ne: null}} : {bloodGlucose: {$gte: [8]}}} ,"
            + "?#{ [9] == null ? {_id: {$ne: null}} : {bloodGlucose: {$lte: [9]}}} ,"
            + "?#{ [10] == null ? {_id: {$ne: null}} : {treatmentStartDate: {$lte: [10]}}}"
            + "]}", fields = "{_id: 0}")
    List<DiabeticScreening> search(Float weightMin, Float weightMax, Float heightMin, Float heightMax, Float systolicBloodPressureMin, Float systolicBloodPressureMax,Float diastolicBloodPressureMin, Float diastolicBloodPressureMax, Float bloodGlucoseMin, Float bloodGlucoseMax,LocalDate treatmentDate);
}
;