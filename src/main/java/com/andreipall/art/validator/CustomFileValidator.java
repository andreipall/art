package com.andreipall.art.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.andreipall.art.dto.PaintingDTO;

@Component
public class CustomFileValidator implements Validator {
	private static final long TWENTY_MB_IN_BYTES = 20971520;

	@Override
	public boolean supports(Class<?> clazz) {
		return PaintingDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PaintingDTO paintingDTO = (PaintingDTO) target;
		MultipartFile image = paintingDTO.getImage();
		if(image.isEmpty()){
            errors.rejectValue("image", "upload.image.required", "image can not be empty");
        }
        else if(image.getSize() > TWENTY_MB_IN_BYTES){
            errors.rejectValue("image", "upload.exceeded.image.size", "upload a file with size less than 20 MB.");
        }
	}

}
