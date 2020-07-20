package com.pixel.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pixel.dao.FilmRepository;
import com.pixel.dao.TicketRepository;
import com.pixel.entities.Film;
import com.pixel.entities.Ticket;

import lombok.Data;

@RestController
@CrossOrigin("*")
public class CinemaRestController {
	
	@Autowired
	private FilmRepository filmRepository;
	
	@Autowired
	private TicketRepository ticketRepository;
	
	@GetMapping(path="/imageFilm/{id}", produces=MediaType.IMAGE_JPEG_VALUE) 
	public byte[] image(@PathVariable(name="id") Long id) throws IOException {
		
		Film film = filmRepository.findById(id).get();
		String name = film.getPhoto();
		File file = new File(System.getProperty("user.home")+"/cinema/images/"+name);
		Path path = Paths.get(file.toURI());
		
	
		return Files.readAllBytes(path);
		

	}
	
	@PostMapping("/payerTickets")
	@Transactional
	public List<Ticket> payerTicket(@RequestBody TicketFrom ticketFrom){
		
		List<Ticket> tickets = new ArrayList<Ticket>();
		
		ticketFrom.getTickets().forEach(ticketId->{
			
			Ticket ticket = ticketRepository.findById(ticketId).get();
			ticket.setNomClient(ticketFrom.getNomClient());
			ticket.setReserve(true);
			ticketRepository.save(ticket);
			tickets.add(ticket);
			
		});
		return tickets;
		
	}
}	
	@Data
	class TicketFrom{
		
		private String nomClient;
		private int codePayement;
		private List<Long> tickets = new ArrayList<>();
	}
	
	

