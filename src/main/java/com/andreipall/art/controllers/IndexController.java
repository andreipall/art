package com.andreipall.art.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.andreipall.art.dto.CommentDTO;
import com.andreipall.art.entities.Painting;
import com.andreipall.art.entities.PaintingComment;
import com.andreipall.art.services.PaintingService;

@Controller
public class IndexController {
	private PaintingService paintingService;

	@Autowired
	public IndexController(PaintingService paintingService) {
		super();
		this.paintingService = paintingService;
	}

	@GetMapping("/")
	String home(Model model) {
		List<Painting> carouselPaintings = paintingService.findLatest3Paintings();
		model.addAttribute("carouselPaintings", carouselPaintings);
		List<Painting> listPaintings = paintingService.findLatestPaintings();
		model.addAttribute("listPaintings", listPaintings);
		model.addAttribute("module", "home");
		return "index";
	}

	@GetMapping("/login")
	String login() {
		return "login";
	}

	@GetMapping("/paintings")
	String paintings(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<Painting> paintingsPage = paintingService.findPaginated(currentPage - 1, 9);
		List<Painting> listPaintings = paintingsPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", paintingsPage.getTotalPages());
		model.addAttribute("listPaintings", listPaintings);
		model.addAttribute("module", "paintings");
		return "paintings";
	}

	@GetMapping("/paintings/{slug}")
	String painting(@PathVariable String slug, Model model) {
		Painting painting = this.paintingService.findBySlug(slug);
		model.addAttribute("name", painting.getName());
		model.addAttribute("slug", painting.getSlug());
		model.addAttribute("description", painting.getDescription());
		model.addAttribute("image", painting.getImageName());
		model.addAttribute("createdAt", painting.getCreatedAt());
		model.addAttribute("comments", painting.getComments());
		CommentDTO commentDTO = new CommentDTO();
		model.addAttribute("commentDTO", commentDTO);
		model.addAttribute("module", "paintings");
		return "painting";
	}

	@GetMapping("/paintings/{slug}/{name}")
	void paintingImage(@PathVariable String slug, HttpServletResponse response) throws IOException {
		Painting painting = this.paintingService.findBySlug(slug);
		response.setContentType(painting.getImageType());
		InputStream is = new ByteArrayInputStream(painting.getImageData());
		IOUtils.copy(is, response.getOutputStream());
	}

	@PostMapping("/paintings/{slug}")
	String paintingComment(@PathVariable String slug, @Valid CommentDTO commentDTO, BindingResult bindingResult,
			RedirectAttributes redirectAttr, Model model) {
		Painting painting = this.paintingService.findBySlug(slug);
		if (bindingResult.hasErrors()) {
			model.addAttribute("name", painting.getName());
			model.addAttribute("slug", painting.getSlug());
			model.addAttribute("description", painting.getDescription());
			model.addAttribute("image", painting.getImageName());
			model.addAttribute("createdAt", painting.getCreatedAt());
			model.addAttribute("comments", painting.getComments());
			model.addAttribute("commentDTO", commentDTO);
			model.addAttribute("module", "paintings");
			return "painting";
		}
		
		PaintingComment paintingComment = new PaintingComment();
		paintingComment.setPainting(painting);
		paintingComment.setName(commentDTO.getName());
		paintingComment.setComment(commentDTO.getComment());
		paintingService.addPaintingComment(paintingComment);
		redirectAttr.addFlashAttribute("message", "Comment added.");
		return "redirect:/paintings/" + slug;
	}

}
