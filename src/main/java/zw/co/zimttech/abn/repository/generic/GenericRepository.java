package zw.co.zimttech.abn.repository.generic;

import org.springframework.data.mongodb.repository.MongoRepository;
import zw.co.zimttech.abn.entity.BaseID;

public interface GenericRepository<T extends BaseID> extends MongoRepository<T,String> {

}
