package org.sid.cinema;

import org.sid.cinema.entites.Film;
import org.sid.cinema.entites.Salle;
import org.sid.cinema.service.ICinemaInitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner	 {

	@Autowired 
	ICinemaInitService intSerice ;
	@Autowired
	private RepositoryRestConfiguration repoConfig ;
	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		repoConfig.exposeIdsFor(Film.class,Salle.class);
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
