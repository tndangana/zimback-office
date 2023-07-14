package zw.co.zimttech.abn.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "role")
@Data
public class Role extends BaseID {
    String name;
}
