package com.example.accessingdatamysql.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.accessingdatamysql.entity.Projects;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends CrudRepository<Projects, Integer> {

}
