package zw.co.zimttech.abn.resource.generics;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import zw.co.zimttech.abn.entity.BaseID;
import zw.co.zimttech.abn.service.generics.GenericService;

import java.util.List;


//T = entity
//ID = primary id
public class GenericResource<T extends BaseID,ID extends String> implements IGenericResource<T, ID> {

    GenericService<T,ID> service;

    public GenericResource(GenericService<T,ID> service) {
        this.service = service;
    }


    @Override
    @PostMapping("/")
    public ResponseEntity<T> create(@RequestBody @Validated T t, @RequestHeader(value = "token") final String token) {
        return service.save(t,token);
    }


    @Override
    @GetMapping("/")
    public ResponseEntity<Page<T>> getAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestHeader(value = "token") final String token) {
        return service.findAll(page, size,token);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<T> deleteById(@PathVariable("id") ID id, @RequestHeader(value = "token") final String token) {
         service.deleteById(id, token);
        return null;
    }


    @Override
    @PutMapping("/{id}")
    public ResponseEntity<T> update(@RequestBody T t, @PathVariable("id") ID id, @RequestHeader(value = "token") final String token) {
        return service.update(t,id,token);
    }


    @Override
    @GetMapping("/{id}")
    public ResponseEntity<T> find(@PathVariable("id") ID id, @RequestHeader(value = "token") final String token) {
        return service.findById(id,token);
    }
}