package com.joaoldantasn.imageliteapi.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.joaoldantasn.imageliteapi.domain.entity.Image;
import com.joaoldantasn.imageliteapi.infra.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService{
	
	private final ImageRepository repository;

	@Override
	@Transactional
	public Image save(Image image) {
		return repository.save(image);
	}

	@Override
	public Optional<Image> getById(String id) {
		return repository.findById(id);
	}

}
