package com.example.accessingdatamysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {

}

