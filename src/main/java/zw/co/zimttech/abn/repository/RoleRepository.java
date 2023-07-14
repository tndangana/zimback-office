package zw.co.zimttech.abn.repository;

import zw.co.zimttech.abn.entity.Role;
import zw.co.zimttech.abn.repository.generic.GenericRepository;

import java.util.Optional;

public interface RoleRepository  extends GenericRepository<Role> {
    Optional<Role> findRoleByName(String name);
}
