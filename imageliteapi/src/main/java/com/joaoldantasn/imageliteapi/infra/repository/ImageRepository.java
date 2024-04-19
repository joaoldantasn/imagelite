package com.joaoldantasn.imageliteapi.infra.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.joaoldantasn.imageliteapi.domain.entity.Image;

public interface ImageRepository extends JpaRepository<Image, String>{

}
