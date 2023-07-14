package zw.co.zimttech.abn.resource;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zw.co.zimttech.abn.dto.PatientDTO;
import zw.co.zimttech.abn.entity.Patient;
import zw.co.zimttech.abn.service.PatientService;

import java.util.List;

@RestController
@RequestMapping("/api/patient")
public class PatientResource {

    private PatientService patientService;

    public PatientResource(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/")
    public ResponseEntity<Page<Patient>> ListPatient(@RequestHeader(value = "token") final String token) {
        return patientService.findAll(0,100,token);
    }

    @PostMapping("/")
    public ResponseEntity<Patient> create(@RequestBody @Validated Patient t, @RequestHeader(value = "token") final String token) {
        return patientService.save(t,token);
    }

    @GetMapping("/list")
    public ResponseEntity<List<PatientDTO>> getPatientListWiScreen(){
        List<PatientDTO> patientDTOList = patientService.getPatientWithDiagnosticList();
        if (patientDTOList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(patientDTOList, HttpStatus.OK);
    }

    @GetMapping("/get-list-age")
    public ResponseEntity<List<PatientDTO>> getPatientListByAge(@RequestParam(name="age") Integer age){
        List<PatientDTO> patientDTOList = patientService.getPatientsByAgeAfter(age);
        if (!patientDTOList.isEmpty()) {
            return new ResponseEntity<>(patientDTOList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("/patient-adult-18")
    public ResponseEntity<List<PatientDTO>> getPatients18(){
        List<PatientDTO> patientDTOList = patientService.getPatientsByAgeAfter18();
        if (!patientDTOList.isEmpty()) {
            return new ResponseEntity<>(patientDTOList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
