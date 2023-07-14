package zw.co.zimttech.abn.resource.generics;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IGenericResource<T,ID extends String> {

    ResponseEntity<T> create(T t,String token);
    ResponseEntity<Page<T>> getAll(int page, int size, String token);
    ResponseEntity<T> deleteById(ID id, String token);
    ResponseEntity<T> update(T t,ID id, String token);
    ResponseEntity<T> find(ID id, String token);
}

