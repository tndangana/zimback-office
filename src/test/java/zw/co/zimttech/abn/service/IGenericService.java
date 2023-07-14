package zw.co.zimttech.abn.service;

import java.util.List;
import java.util.Optional;

public interface IGenericService<T,ID> {

    T save(T t);
    void deleteById(ID id);
    Optional<T> findById(ID id);
    Optional<List<T>> findAll();
    Optional<T> update(T t, ID id);
}
