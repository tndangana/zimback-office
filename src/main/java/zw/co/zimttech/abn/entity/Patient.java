package zw.co.zimttech.abn.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "patient")
@AllArgsConstructor
public class Patient  extends BaseID{
    private String firstName;
    private String lastName;
    private Gender gender;
    private Integer age;
    private String mobileNumber;

}
