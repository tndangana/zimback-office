package zw.co.zimttech.abn.resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zw.co.zimttech.abn.dto.DiabeticDTO;
import zw.co.zimttech.abn.dto.QueryDTO;
import zw.co.zimttech.abn.entity.DiabeticScreening;
import zw.co.zimttech.abn.service.DiabeticService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/screening")
public class DiabeticScreeningResource {


    private DiabeticService diabeticService;

    public DiabeticScreeningResource(DiabeticService diabeticService) {
        this.diabeticService = diabeticService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<DiabeticScreening>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "100") int size, @RequestHeader(value = "token") final String token) {
        return diabeticService.findAll(page, size,token);
    }

    @PostMapping("/")
    public ResponseEntity<DiabeticScreening> create(@RequestBody @Validated DiabeticScreening diabeticScreening, @RequestHeader(value = "token") final String token ) {
        List<DiabeticScreening> diabeticScreeningList = diabeticService.findListByPatientId(diabeticScreening.getPatientId());
        if (diabeticScreeningList.isEmpty()) {
            // Patient has not started treatment
            diabeticScreening.setTreatmentStartDate(diabeticScreening.getTreatmentDate());
            diabeticService.save(diabeticScreening, token);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        //Patient already started treatment
        diabeticScreening.setTreatmentStartDate(diabeticScreeningList.get(0).getTreatmentStartDate());
        diabeticService.save(diabeticScreening,token);
        return new ResponseEntity<>( HttpStatus.CREATED);

    }

    @PostMapping("/filter")
    public ResponseEntity<List<DiabeticDTO>> getFilteredDiagnosticResults(@RequestBody @Validated QueryDTO queryDTO) {
        List<DiabeticDTO> diabeticScreeningList = diabeticService
                .search(queryDTO.getWeightMin(), queryDTO.getWeightMax(), queryDTO.getHeightMin(), queryDTO.getHeightMax(),
                        queryDTO.getSystolicBloodPressureMin(), queryDTO.getSystolicBloodPressureMax(),queryDTO.getDiastolicBloodPressureMin(), queryDTO.getDiastolicBloodPressureMax(), queryDTO.getBloodGlucoseMin(), queryDTO.getBloodGlucoseMax(), LocalDate.now().minusMonths(18));
        if (!diabeticScreeningList.isEmpty()) {
            return new ResponseEntity<>(diabeticScreeningList, HttpStatus.OK);
        }
        if(diabeticScreeningList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
