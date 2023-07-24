package com.example.accessingdatamysql.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.example.accessingdatamysql.entity.Technology;
@Repository
public interface TechnologyRepository extends CrudRepository<Technology, Integer> {
    Technology findByTitle(String title);
}
