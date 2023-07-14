package zw.co.zimttech.abn.service.generics;


import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import zw.co.zimttech.abn.response.Response;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T, K> {
    ResponseEntity<Page<T>> findAll(int page, int size, String token) ;

    ResponseEntity<T> findById(K k, String token);

    ResponseEntity<T> save(T entity, String token);

    ResponseEntity<Void> deleteById(K k, String token);

    ResponseEntity<T> update(T entity,K k, String token);

}
