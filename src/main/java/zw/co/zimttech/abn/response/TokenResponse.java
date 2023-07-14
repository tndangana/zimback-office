package zw.co.zimttech.abn.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TokenResponse {
    private String token;
    private String userName;
    private String id;
    private Boolean success;
}
