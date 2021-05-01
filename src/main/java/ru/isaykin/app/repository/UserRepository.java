package ru.isaykin.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.isaykin.app.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {


}
