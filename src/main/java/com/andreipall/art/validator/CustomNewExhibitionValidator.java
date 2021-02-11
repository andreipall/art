package com.andreipall.art.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;
import com.andreipall.art.dto.ExhibitionDTO;

@Component
public class CustomNewExhibitionValidator implements Validator {
	private static final long TWENTY_MB_IN_BYTES = 20971520;

	@Override
	public boolean supports(Class<?> clazz) {
		return ExhibitionDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ExhibitionDTO exhibitionDTO = (ExhibitionDTO) target;
		MultipartFile image = exhibitionDTO.getImage();
		if(image.isEmpty()){
            errors.rejectValue("image", "upload.image.required", "image can not be empty");
        }
        if(image.getSize() > TWENTY_MB_IN_BYTES){
            errors.rejectValue("image", "upload.exceeded.image.size", "upload a file with size less than 20 MB.");
        }
		MultipartFile[] images = exhibitionDTO.getImages();
		if(images.length == 0){
            errors.rejectValue("images", "upload.image.required", "images can not be empty");
        }
		for(MultipartFile exhibitionImage: images) {
			if(exhibitionImage.getSize() > TWENTY_MB_IN_BYTES){
	            errors.rejectValue("images", "upload.exceeded.image.size", "upload files with the size less than 20 MB.");
	        }
		}
	}

}
