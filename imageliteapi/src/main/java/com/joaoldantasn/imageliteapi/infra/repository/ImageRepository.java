package com.joaoldantasn.imageliteapi.infra.repository;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.util.StringUtils;

import com.joaoldantasn.imageliteapi.domain.entity.Image;
import com.joaoldantasn.imageliteapi.domain.enums.ImageExtension;

public interface ImageRepository extends JpaRepository<Image, String>, JpaSpecificationExecutor<Image>{

	
	default List<Image> findByExtensionAndNameOrTagsLike(ImageExtension extension, String query){
		Specification<Image> conjunction = (root, q, criteriaBuilder) -> criteriaBuilder.conjunction();
		Specification<Image> spec = Specification.where(conjunction);
		
		if(extension != null) {
			Specification<Image> extensionEqual = (root, q, cb) -> cb.equal(root.get("extension"), extension);
			spec = spec.and(extensionEqual);
		}
		
		if(StringUtils.hasText(query)) {
			Specification<Image> nameLike = (root, q, cb) -> cb.like(cb.upper(root.get("name")),"%" + query.toUpperCase() + "%");
			Specification<Image> tagsLike = (root, q, cb) -> cb.like(cb.upper(root.get("tags")),"%" + query.toUpperCase() + "%");
			
			Specification<Image> nameOrTagsLike = Specification.anyOf(nameLike, tagsLike);
			
			spec = spec.and(nameOrTagsLike);
		}
		
		return findAll();
	}
	
}
