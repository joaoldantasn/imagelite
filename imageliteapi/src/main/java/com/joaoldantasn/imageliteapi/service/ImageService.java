package com.joaoldantasn.imageliteapi.service;

import java.util.Optional;

import com.joaoldantasn.imageliteapi.domain.entity.Image;

public interface ImageService {
	
	Image save(Image image);
	
	Optional<Image> getById(String id);

}
