package zw.co.zimttech.abn.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.Email;


@Document(collection = "user")
@Data
public class User extends BaseID implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private String username;
    private String firstName;
    private String lastName;
    @Email
    private String email;
    private String password;
}
