package zw.co.zimttech.abn.service;

import org.springframework.beans.BeanUtils;
import zw.co.zimttech.abn.entity.BaseID;
import zw.co.zimttech.abn.repository.generic.GenericRepository;

import java.util.List;
import java.util.Optional;

public abstract class GenericService<T extends BaseID, ID extends String> implements IGenericService<T, ID> {

    private final GenericRepository<T> genericRepository;

    public GenericService(GenericRepository<T> genericRepository) {
        this.genericRepository = genericRepository;
    }

    @Override
    public T save(T t) {
        return genericRepository.save(t);
    }

    @Override
    public void deleteById(ID s) {
        genericRepository.deleteById(s);
    }

    @Override
    public Optional<T> findById(ID s) {
        return Optional.ofNullable(genericRepository.findById(s).get());
    }

    @Override
    public Optional<List<T>> findAll() {
        return Optional.ofNullable(genericRepository.findAll());
    }

    @Override
    public Optional<T> update(T t, ID s) {
        return genericRepository.findById(s).map(existingT -> {
            BeanUtils.copyProperties(t, existingT);
            return genericRepository.save(existingT);
        });

    }


}
