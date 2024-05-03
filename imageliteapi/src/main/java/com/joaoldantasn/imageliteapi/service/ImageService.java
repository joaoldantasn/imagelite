package com.joaoldantasn.imageliteapi.service;

import java.util.List;
import java.util.Optional;

import com.joaoldantasn.imageliteapi.domain.entity.Image;
import com.joaoldantasn.imageliteapi.domain.enums.ImageExtension;

public interface ImageService {
	
	Image save(Image image);
	
	Optional<Image> getById(String id);
	
	List<Image> search(ImageExtension extension, String query);

}
