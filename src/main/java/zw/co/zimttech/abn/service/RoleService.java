package zw.co.zimttech.abn.service;

import jdk.nashorn.internal.runtime.options.Option;
import org.springframework.stereotype.Service;
import zw.co.zimttech.abn.entity.Role;
import zw.co.zimttech.abn.repository.RoleRepository;
import zw.co.zimttech.abn.repository.generic.GenericRepository;
import zw.co.zimttech.abn.service.generics.GenericService;

import java.util.Optional;

@Service
public class RoleService extends GenericService<Role,String> {

    private RoleRepository roleRepository;

    public RoleService(GenericRepository<Role> repository, RoleRepository roleRepository) {
        super(repository);
        this.roleRepository = roleRepository;
    }



    public Optional<Role> findRoleByName(String name){
        return roleRepository.findRoleByName(name);
    }
}
