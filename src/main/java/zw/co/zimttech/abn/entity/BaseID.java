package zw.co.zimttech.abn.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.Instant;
import java.time.LocalDate;

@Data
public abstract class BaseID {
    @ApiModelProperty(readOnly = true)
    @Id
    private String id;
    private Instant createdDate = Instant.now();
    private Instant updatedDate = Instant.now();
}
