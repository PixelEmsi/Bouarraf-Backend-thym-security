package com.pixel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.pixel.entities.Ville;

@RepositoryRestResource
@CrossOrigin("*")
public interface VilleRepository extends JpaRepository<Ville, Long> {

}
