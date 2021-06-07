package com.andreipall.art.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.andreipall.art.dto.ExhibitionDTO;
import com.andreipall.art.dto.PaintingDTO;
import com.andreipall.art.dto.UserDTO;
import com.andreipall.art.entities.Exhibition;
import com.andreipall.art.entities.ExhibitionImage;
import com.andreipall.art.entities.Painting;
import com.andreipall.art.entities.PaintingComment;
import com.andreipall.art.entities.User;
import com.andreipall.art.services.ExhibitionService;
import com.andreipall.art.services.PaintingService;
import com.andreipall.art.services.UserService;
import com.andreipall.art.validator.CustomExhibitionValidator;
import com.andreipall.art.validator.CustomFileValidator;
import com.andreipall.art.validator.CustomNewExhibitionValidator;
import com.andreipall.art.validator.CustomPaintingValidator;
import com.github.slugify.Slugify;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private CustomFileValidator customFileValidator;
	private CustomPaintingValidator customPaintingValidator;
	private CustomNewExhibitionValidator customNewExhibitionValidator;
	private CustomExhibitionValidator customExhibitionValidator;
	private Slugify slugify;
	private PaintingService paintingService;
	private ExhibitionService exhibitionService;
	private UserService userService;

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	public AdminController(CustomFileValidator customFileValidator, CustomPaintingValidator customPaintingValidator,
			CustomNewExhibitionValidator customNewExhibitionValidator,
			CustomExhibitionValidator customExhibitionValidator, Slugify slugify, PaintingService paintingService,
			ExhibitionService exhibitionService, UserService userService) {
		super();
		this.customFileValidator = customFileValidator;
		this.customPaintingValidator = customPaintingValidator;
		this.customNewExhibitionValidator = customNewExhibitionValidator;
		this.customExhibitionValidator = customExhibitionValidator;
		this.slugify = slugify;
		this.paintingService = paintingService;
		this.exhibitionService = exhibitionService;
		this.userService = userService;
	}

	@GetMapping()
	String home(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<Painting> paintingsPage = paintingService.findPaginated(currentPage - 1, 10);
		List<Painting> listPaintings = paintingsPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", paintingsPage.getTotalPages());
		model.addAttribute("listPaintings", listPaintings);
		model.addAttribute("module", "paintings");
		return "admin/paintings";
	}

	@GetMapping("/paintings")
	String paintings(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<Painting> paintingsPage = paintingService.findPaginated(currentPage - 1, 10);
		List<Painting> listPaintings = paintingsPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", paintingsPage.getTotalPages());
		model.addAttribute("listPaintings", listPaintings);
		model.addAttribute("module", "paintings");
		return "admin/paintings";
	}

	@GetMapping("/paintings/new")
	String newPainting(@ModelAttribute PaintingDTO paintingDTO, Model model) {
		model.addAttribute("module", "paintings");
		return "admin/newPainting";
	}

	@PostMapping("/paintings/new")
	String savePainting(@Valid PaintingDTO paintingDTO, BindingResult bindingResult, RedirectAttributes redirectAttr) {
		customFileValidator.validate(paintingDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/newPainting";
		}

		try {
			Painting painting = new Painting();
			painting.setName(paintingDTO.getName());
			painting.setSlug(this.slugify.slugify(paintingDTO.getName()));
			painting.setDescription(paintingDTO.getDescription());
			painting.setImageName(paintingDTO.getImage().getOriginalFilename());
			painting.setImageType(paintingDTO.getImage().getContentType());
			painting.setImageData(paintingDTO.getImage().getBytes());
			paintingService.addPainting(painting);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		redirectAttr.addFlashAttribute("message", "Painting added.");
		return "redirect:/admin/paintings";
	}

	@GetMapping("/paintings/{id}/edit")
	String editPainting(@PathVariable int id, @ModelAttribute PaintingDTO paintingDTO, Model model) {
		Painting painting = this.paintingService.findById(id);
		model.addAttribute("module", "paintings");
		paintingDTO.setName(painting.getName());
		paintingDTO.setDescription(painting.getDescription());
		model.addAttribute("id", id);
		model.addAttribute("slug", painting.getSlug());
		model.addAttribute("image", painting.getImageName());
		model.addAttribute("comments", painting.getComments());
		return "admin/editPainting";
	}

	@PostMapping("/paintings/{id}/edit")
	String saveEditedPainting(@PathVariable int id, @Valid PaintingDTO paintingDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) {
		Painting painting = this.paintingService.findById(id);
		customPaintingValidator.validate(paintingDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("module", "paintings");
			model.addAttribute("id", id);
			model.addAttribute("slug", painting.getSlug());
			model.addAttribute("image", painting.getImageName());
			model.addAttribute("paintingDTO", paintingDTO);
			model.addAttribute("comments", painting.getComments());
			return "admin/editPainting";
		}

		try {
			painting.setName(paintingDTO.getName());
			painting.setSlug(this.slugify.slugify(paintingDTO.getName()));
			painting.setDescription(paintingDTO.getDescription());
			if (!paintingDTO.getImage().isEmpty()) {
				painting.setImageName(paintingDTO.getImage().getOriginalFilename());
				painting.setImageType(paintingDTO.getImage().getContentType());
				painting.setImageData(paintingDTO.getImage().getBytes());
			}
			paintingService.savePainting(painting);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		redirectAttr.addFlashAttribute("message", "Painting edited.");
		return "redirect:/admin/paintings";
	}

	@DeleteMapping("/paintings/{id}")
	ResponseEntity<?> deletePainting(@PathVariable int id) {
		Painting painting = new Painting();
		painting.setId(id);
		this.paintingService.deletePainting(painting);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/paintings/{id}/comments/{commentId}")
	ResponseEntity<?> deletePaintingComment(@PathVariable int id, @PathVariable int commentId) {
		PaintingComment paintingComment = new PaintingComment();
		paintingComment.setId(commentId);
		this.paintingService.deletePaintingComment(paintingComment);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/exhibitions")
	String exhibitions(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<Exhibition> exhibitionsPage = exhibitionService.findPaginated(currentPage - 1, 10);
		List<Exhibition> listExhibitions = exhibitionsPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", exhibitionsPage.getTotalPages());
		model.addAttribute("listExhibitions", listExhibitions);
		model.addAttribute("module", "exhibitions");
		return "admin/exhibitions";
	}

	@GetMapping("/exhibitions/new")
	String newExhibition(@ModelAttribute ExhibitionDTO exhibitionDTO, Model model) {
		model.addAttribute("module", "exhibitions");
		return "admin/newExhibition";
	}

	@PostMapping("/exhibitions/new")
	String saveExhibition(@Valid ExhibitionDTO exhibitionDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttr) {
		customNewExhibitionValidator.validate(exhibitionDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			return "admin/newExhibition";
		}

		try {
			Exhibition exhibition = new Exhibition();
			exhibition.setName(exhibitionDTO.getName());
			exhibition.setSlug(this.slugify.slugify(exhibitionDTO.getName()));
			exhibition.setDescription(exhibitionDTO.getDescription());
			exhibition.setImageName(exhibitionDTO.getImage().getOriginalFilename());
			exhibition.setImageType(exhibitionDTO.getImage().getContentType());
			exhibition.setImageData(exhibitionDTO.getImage().getBytes());
			List<ExhibitionImage> exhibitionImages = new ArrayList<>();
			for (MultipartFile image : exhibitionDTO.getImages()) {
				ExhibitionImage exhibitionImage = new ExhibitionImage();
				exhibitionImage.setImageName(image.getOriginalFilename());
				exhibitionImage.setImageType(image.getContentType());
				exhibitionImage.setImageData(image.getBytes());
				exhibitionImage.setExhibition(exhibition);
				exhibitionImages.add(exhibitionImage);
			}
			exhibition.setImages(exhibitionImages);
			exhibitionService.addExhibition(exhibition);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		redirectAttr.addFlashAttribute("message", "Exhibition added.");
		return "redirect:/admin/exhibitions";
	}

	@GetMapping("/exhibitions/{id}/edit")
	String editExhibition(@PathVariable int id, @ModelAttribute ExhibitionDTO exhibitionDTO, Model model) {
		Exhibition exhibition = this.exhibitionService.findById(id);
		model.addAttribute("module", "exhibitions");
		exhibitionDTO.setName(exhibition.getName());
		exhibitionDTO.setDescription(exhibition.getDescription());
		model.addAttribute("id", id);
		model.addAttribute("slug", exhibition.getSlug());
		model.addAttribute("image", exhibition.getImageName());
		model.addAttribute("images", exhibition.getImages());
		return "admin/editExhibition";
	}

	@PostMapping("/exhibitions/{id}/edit")
	String saveEditedExhibition(@PathVariable int id, @Valid ExhibitionDTO exhibitionDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) {
		Exhibition exhibition = this.exhibitionService.findById(id);
		customExhibitionValidator.validate(exhibitionDTO, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("module", "exhibitions");
			model.addAttribute("id", id);
			model.addAttribute("slug", exhibition.getSlug());
			model.addAttribute("image", exhibition.getImageName());
			model.addAttribute("exhibitionDTO", exhibitionDTO);
			model.addAttribute("images", exhibition.getImages());
			return "admin/editExhibition";
		}

		try {
			exhibition.setName(exhibitionDTO.getName());
			exhibition.setSlug(this.slugify.slugify(exhibitionDTO.getName()));
			exhibition.setDescription(exhibitionDTO.getDescription());
			if (!exhibitionDTO.getImage().isEmpty()) {
				exhibition.setImageName(exhibitionDTO.getImage().getOriginalFilename());
				exhibition.setImageType(exhibitionDTO.getImage().getContentType());
				exhibition.setImageData(exhibitionDTO.getImage().getBytes());
			}
			if (exhibitionDTO.getImages().length != 0) {
				List<ExhibitionImage> exhibitionImages = new ArrayList<>();
				for (MultipartFile image : exhibitionDTO.getImages()) {
					ExhibitionImage exhibitionImage = new ExhibitionImage();
					exhibitionImage.setImageName(image.getOriginalFilename());
					exhibitionImage.setImageType(image.getContentType());
					exhibitionImage.setImageData(image.getBytes());
					exhibitionImage.setExhibition(exhibition);
					exhibitionImages.add(exhibitionImage);
				}
				exhibition.setImages(exhibitionImages);
			}
			exhibitionService.saveExhibition(exhibition);
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		redirectAttr.addFlashAttribute("message", "Exhibition edited.");
		return "redirect:/admin/exhibitions";
	}

	@DeleteMapping("/exhibitions/{id}")
	ResponseEntity<?> deleteExhibition(@PathVariable int id) {
		Exhibition exhibition = new Exhibition();
		exhibition.setId(id);
		this.exhibitionService.deleteExhibition(exhibition);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/users")
	String users(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<User> usersPage = userService.findPaginated(currentPage - 1, 10);
		List<User> listUsers = usersPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", usersPage.getTotalPages());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("module", "users");
		return "admin/users";
	}

	@GetMapping("/users/new")
	String newUser(@ModelAttribute UserDTO userDTO, Model model) {
		model.addAttribute("module", "users");
		return "admin/newUser";
	}

	@PostMapping("/users/new")
	String saveUser(@Valid UserDTO userDTO, BindingResult bindingResult, RedirectAttributes redirectAttr) {
		if (bindingResult.hasErrors()) {
			return "admin/newUser";
		}
		User user = new User();
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());
		user.setEnabled(userDTO.isEnabled());
		userService.addUser(user);

		redirectAttr.addFlashAttribute("message", "User added.");
		return "redirect:/admin/users";
	}

	@GetMapping("/users/{id}/edit")
	String editUser(@PathVariable int id, @ModelAttribute UserDTO userDTO, Model model) {
		User user = this.userService.findById(id);
		model.addAttribute("module", "users");
		userDTO.setUsername(user.getUsername());
		userDTO.setRole(user.getRole());
		userDTO.setEnabled(user.isEnabled());
		model.addAttribute("id", id);
		return "admin/editUser";
	}

	@PostMapping("/users/{id}/edit")
	String saveEditedUser(@PathVariable int id, @Valid UserDTO userDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) {
		User user = this.userService.findById(id);
		if (bindingResult.hasErrors()) {
			model.addAttribute("module", "users");
			model.addAttribute("id", id);
			model.addAttribute("userDTO", userDTO);
			return "admin/editUser";
		}
		user.setUsername(userDTO.getUsername());
		user.setPassword(userDTO.getPassword());
		user.setRole(userDTO.getRole());
		user.setEnabled(userDTO.isEnabled());
		userService.saveUser(user);
		redirectAttr.addFlashAttribute("message", "User edited.");
		return "redirect:/admin/users";
	}

	@DeleteMapping("/users/{id}")
	ResponseEntity<?> deleteUser(@PathVariable int id) {
		User user = new User();
		user.setId(id);
		this.userService.deleteUser(user);
		return ResponseEntity.ok().build();
	}
}
