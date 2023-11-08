package org.sid.cinema;

import org.sid.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner	 {

	@Autowired 
	ICinemaInitService intSerice ;
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		intSerice.initVilles();
		intSerice.initCinemas();
		intSerice.initSalles();
		intSerice.initPlaces();
		intSerice.initSeance();
		intSerice.initCategories();
		intSerice.initFilms();
		intSerice.initProjections();
		intSerice.initTickes();
 
		
	}

}
