package zw.co.zimttech.abn.service.generics;

import java.net.URI;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import zw.co.zimttech.abn.entity.BaseID;
import zw.co.zimttech.abn.repository.generic.GenericRepository;
import java.util.Optional;
import org.slf4j.Logger;
import zw.co.zimttech.abn.service.UserService;



public abstract class GenericService<T extends BaseID, ID extends String> implements IGenericService<T, ID> {

    private final GenericRepository<T> repository;

    @Autowired
    private UserService userService;
    private final Logger logger = LoggerFactory.getLogger(GenericService.class);

    
    public GenericService(GenericRepository<T> repository) {
        this.repository = repository;
    }

    @Override
    public ResponseEntity<Page<T>> findAll(int page, int size,String token) {
        try {

            Boolean authenticated = userService.validate(token);
            if(!authenticated){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            Pageable pageable = PageRequest.of(page, size);
            Page<T> result = repository.findAll(pageable);
            if (result.isEmpty()) {
                logger.info("No entities found");
                return ResponseEntity.noContent().build();
            } else {
                logger.info("Retrieved {} entities", result.getTotalElements());
                return ResponseEntity.ok(result);
            }
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve entities", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<T> findById(ID id, String token) {
        try {
            Boolean authenticated = userService.validate(token);
            if(!authenticated){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            logger.info("Finding entity by id {}", id);
            Optional<T> optionalEntity = repository.findById(id);
            return optionalEntity.map(entity -> ResponseEntity.ok(entity))
                    .orElse(ResponseEntity.notFound().build());
        } catch (DataAccessException e) {
            logger.error("Failed to retrieve entity by id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<T> save(T entity, String token) {
        try {
            Boolean authenticated = userService.validate(token);
            if(!authenticated){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            logger.info("Saving entity {}", entity);
            T savedEntity = repository.save(entity);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedEntity);
        } catch (DataAccessException e) {
            logger.error("Failed to save entity {}", entity, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> deleteById(ID id,String token) {
        try {
            Boolean authenticated = userService.validate(token);
            if(!authenticated){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            logger.info("Deleting entity by id {}", id);
            repository.deleteById(String.valueOf(id));
            return ResponseEntity.noContent().build();
        } catch (DataAccessException e) {
            logger.error("Failed to delete entity by id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<T> update(T t, ID id, String token) {
        try {
            Boolean authenticated = userService.validate(token);
            if(!authenticated){
                return new ResponseEntity(HttpStatus.UNAUTHORIZED);
            }
            return repository.findById(id).map(existingT -> {
                BeanUtils.copyProperties(t, existingT);
                T updatedT = repository.save(existingT);
                logger.info("Entity with ID {} was updated.", id);
                return ResponseEntity.ok(updatedT);
            }).orElseGet(() -> {
                logger.warn("Entity with ID {} was not found.", id);
                return ResponseEntity.notFound().build();
            });
        } catch (DataAccessException e) {
            logger.error("Failed to update entity with ID {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}