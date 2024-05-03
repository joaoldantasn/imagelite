package com.joaoldantasn.imageliteapi.application.images;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.joaoldantasn.imageliteapi.domain.entity.Image;
import com.joaoldantasn.imageliteapi.domain.enums.ImageExtension;
import com.joaoldantasn.imageliteapi.imagesMapper.ImageMapper;
import com.joaoldantasn.imageliteapi.service.ImageService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/v1/images")
@Slf4j
@RequiredArgsConstructor
public class ImagesController {
	
	private final ImageService service;
	private final ImageMapper mapper;
	//para receber arquivos não usa formato json e sim formato formdata

	@PostMapping
	public ResponseEntity save(
			@RequestParam("file") MultipartFile file, 
			@RequestParam("name") String name,
			@RequestParam("tags") List<String> tags
			) throws IOException {
		log.info("Imagem recebida: name:{}, size: {}", file.getOriginalFilename(), file.getSize());
		
		Image image = mapper.mapToImage(file, name, tags);
		Image savedImage = service.save(image);
		URI imageUri = buildImageURL(savedImage);
		
		//pegar url
		
		return ResponseEntity.created(imageUri).build();
	}
	
	// /v1/images/iuhiudhbisud
	@GetMapping("{id}")
	public ResponseEntity<byte[]> getImage(@PathVariable String id){
		var possibleImage = service.getById(id);
		if(possibleImage.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var image = possibleImage.get();
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(image.getExtension().getMediaType());
		headers.setContentLength(image.getSize());
		//inline;filename="image.PNG"
		headers.setContentDispositionFormData("inline; filename=\"" + image.getFileName() +"\"", image.getFileName());
		
		return new ResponseEntity<>(image.getFile(), headers, HttpStatus.OK);
	}
	// loacalhost:8080/v1/images?extension=PNG&query=Nature
	@GetMapping
	public ResponseEntity<List<ImageDTO>> search(@RequestParam(value = "extension", required = false) String extension,@RequestParam(value = "extension", required = false) String query){
		var result = service.search(ImageExtension.valueOf(extension), query);
		var images = result.stream().map(image -> {
			var url = buildImageURL(image);
			return mapper.imageToDTO(image, url.toString());
		}).collect(Collectors.toList());
		
		return ResponseEntity.ok(images);
	}
	
	// loacalhost:8080/v1/images/akigbfdkcfdsa
	private URI buildImageURL(Image image) {
		String imagePath = "/" + image.getId();
		return ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path(imagePath)
				.build().toUri();
	}
	
}
