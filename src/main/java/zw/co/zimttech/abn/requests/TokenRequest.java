package zw.co.zimttech.abn.requests;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class TokenRequest {
    @NotNull
   private String token;
}
