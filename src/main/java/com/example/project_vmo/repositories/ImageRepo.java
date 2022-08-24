package com.example.project_vmo.repositories;


import com.example.project_vmo.models.entities.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepo extends JpaRepository<Image,Integer> {

}
