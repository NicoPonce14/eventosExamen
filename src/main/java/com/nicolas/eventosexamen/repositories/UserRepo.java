package com.nicolas.eventosexamen.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.nicolas.eventosexamen.models.User;

@Repository
public interface UserRepo extends CrudRepository<User,Long> {

	User findByEmail(String email);
}
