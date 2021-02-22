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
import com.andreipall.art.dto.ContactDTO;
import com.andreipall.art.entities.Exhibition;
import com.andreipall.art.entities.ExhibitionImage;
import com.andreipall.art.entities.Painting;
import com.andreipall.art.entities.PaintingComment;
import com.andreipall.art.services.ExhibitionService;
import com.andreipall.art.services.PaintingService;

@Controller
public class IndexController {
	private PaintingService paintingService;
	private ExhibitionService exhibitionService;

	@Autowired
	public IndexController(PaintingService paintingService, ExhibitionService exhibitionService) {
		super();
		this.paintingService = paintingService;
		this.exhibitionService = exhibitionService;
	}

	@GetMapping("/")
	String home(Model model) {
		List<Painting> listPaintings = paintingService.findLatestPaintings();
		model.addAttribute("listPaintings", listPaintings);
		List<Exhibition> listExhibition = exhibitionService.findLatestExhibitions();
		model.addAttribute("listExhibitions", listExhibition);
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

	@GetMapping("/exhibitions/{slug}/{name}")
	void exhibitionImage(@PathVariable String slug, HttpServletResponse response) throws IOException {
		Exhibition exhibitionImage = this.exhibitionService.findBySlug(slug);
		response.setContentType(exhibitionImage.getImageType());
		InputStream is = new ByteArrayInputStream(exhibitionImage.getImageData());
		IOUtils.copy(is, response.getOutputStream());
	}

	@GetMapping("/exhibitions/{slug}/images/{name}")
	void exhibitionImages(@PathVariable String slug, @PathVariable String name, HttpServletResponse response)
			throws IOException {
		ExhibitionImage exhibitionImage = this.exhibitionService.findExhibitionImage(slug, name);
		response.setContentType(exhibitionImage.getImageType());
		InputStream is = new ByteArrayInputStream(exhibitionImage.getImageData());
		IOUtils.copy(is, response.getOutputStream());
	}

	@GetMapping("/exhibitions")
	String exhibitions(@RequestParam("page") Optional<Integer> page, Model model) {
		int currentPage = page.orElse(1);
		Page<Exhibition> exhibitionsPage = exhibitionService.findPaginated(currentPage - 1, 2);
		List<Exhibition> listExhibitions = exhibitionsPage.getContent();
		model.addAttribute("currentPage", currentPage);
		model.addAttribute("totalPages", exhibitionsPage.getTotalPages());
		model.addAttribute("listExhibitions", listExhibitions);
		model.addAttribute("module", "exhibitions");
		return "exhibitions";
	}

	@GetMapping("/exhibitions/{slug}")
	String exhibition(@PathVariable String slug, Model model) {
		Exhibition exhibition = this.exhibitionService.findBySlug(slug);
		model.addAttribute("name", exhibition.getName());
		model.addAttribute("slug", exhibition.getSlug());
		model.addAttribute("description", exhibition.getDescription());
		model.addAttribute("images", exhibition.getImages());
		model.addAttribute("createdAt", exhibition.getCreatedAt());
		model.addAttribute("module", "exhibitions");
		return "exhibition";
	}

	@GetMapping("/about-me")
	String aboutMe(Model model) {
		model.addAttribute("module", "about-me");
		return "aboutMe";
	}

	@GetMapping("/contact")
	String contact(Model model) {
		ContactDTO contactDTO = new ContactDTO();
		model.addAttribute("contactDTO", contactDTO);
		model.addAttribute("module", "contact");
		return "contact";
	}

	@PostMapping("/contact")
	String contactSent(@Valid ContactDTO contactDTO, BindingResult bindingResult, RedirectAttributes redirectAttr,
			Model model) {
		if (bindingResult.hasErrors()) {
			model.addAttribute("contactDTO", contactDTO);
			model.addAttribute("module", "contact");
			return "contact";
		}
		return "redirect:/contacted";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam("keyword") String keyword) {
		List<Painting> listPaintings = paintingService.listAll(keyword);
		model.addAttribute("listPaintings", listPaintings);
		model.addAttribute("keyword", keyword);
		return "search";
	}
}
