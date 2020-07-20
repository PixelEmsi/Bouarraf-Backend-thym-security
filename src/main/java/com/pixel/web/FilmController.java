package com.pixel.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pixel.dao.FilmRepository;
import com.pixel.entities.Film;


@Controller
public class FilmController {

	@Autowired
	private FilmRepository filmRepository;
	

	@GetMapping(path = "/listfilms")
	public String list(Model model,
			@RequestParam(name="page",defaultValue = "0")int page,
			@RequestParam(name="size",defaultValue = "5")int size, 
			@RequestParam(name="keyword",defaultValue = "")String keyword){
		
		Page<Film> pageFilms = filmRepository.findByTitreContains(keyword, PageRequest.of(page, size));
		model.addAttribute("films", pageFilms.getContent());
		model.addAttribute("pages", new int[pageFilms.getTotalPages()]);
		model.addAttribute("keyword", keyword);
		model.addAttribute("currentPage", page);
		model.addAttribute("size", size);
		return "films";	
	}
	
	@GetMapping(path = "/deleteFilm")
	public String delete(Model model, Long id, int page, int size, String keyword) {
		
		filmRepository.deleteById(id);
		return list(model,page,size,keyword);
	}
	
	@GetMapping(path= "/formFilm")
	public String formFilm(Model model) {
		model.addAttribute("film", new Film());
		return "ajouterFilm";
	}
	
	@PostMapping("/saveFilm")
	public String saveFilm(Film film) {
		filmRepository.save(film);
		return "formFilm";
		
	}
}
