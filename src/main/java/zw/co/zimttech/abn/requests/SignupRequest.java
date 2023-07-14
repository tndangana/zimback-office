package zw.co.zimttech.abn.requests;

import lombok.Data;

@Data
public class SignupRequest {
    private String username;
    private String password;
    private String first_name;
    private String last_name;
    private String email;
    private String phone_number;
}
