package org.sid.cinema.web;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.sid.cinema.classes.TicketForm;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.entites.Film;
import org.sid.cinema.entites.Ticket;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin("*")
public class CinemaRestController {

	@Autowired
	private FilmRepository film_repo ;
	@Autowired
	private TicketRepository ticket_repo ;
	@GetMapping("listFilms")
	public List<Film> index()
	{
		return film_repo.findAll();
	}
	
	@GetMapping(path="/images/{film_id}",produces = MediaType.IMAGE_JPEG_VALUE)
	public byte[] images(@PathVariable(value = "film_id")  Long filmID) throws IOException
	{
		Film film = film_repo.findById(filmID).get();
		String photo = film.getPhoto();
 		File file = new File("/var/www/html/my_work/formation/crococoder/images/"+photo);
 		Path path = Paths.get(file.toURI());
 		 return Files.readAllBytes(path);
	}
	@PostMapping("/pay-ticket")
	@Transactional
	public  List<Ticket>  payTicket(@RequestBody TicketForm tickeForm)
	{
		 
 		 List<Ticket> tickes = new ArrayList<>();
		tickeForm.getTickets().forEach(ticket_id->{
			Ticket ticket = ticket_repo.findById(ticket_id).get();
			
				   ticket.setNameClient(tickeForm.getNameClient());
				   ticket.setCodePayement(tickeForm.getCodePayment());
				   ticket.setReserve(true);
				   ticket_repo.save(ticket);
				   tickes.add(ticket);
		});
		return tickes ;
		
	}
}
