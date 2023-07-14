package zw.co.zimttech.abn.repository;

import zw.co.zimttech.abn.entity.User;
import zw.co.zimttech.abn.repository.generic.GenericRepository;

import java.util.Optional;

public interface UserRepository extends GenericRepository<User> {

    Optional<User> findUserByUsername(String username);
    Boolean existsByUsername(String username);


}
