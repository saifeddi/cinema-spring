package org.sid.cinema.service;

 import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.sid.cinema.dao.CategoryRepository;
import org.sid.cinema.dao.CinemaRepository;
import org.sid.cinema.dao.FilmRepository;
import org.sid.cinema.dao.PlaceRepository;
import org.sid.cinema.dao.ProjectionRepository;
import org.sid.cinema.dao.SalleRepository;
import org.sid.cinema.dao.SeanceRepository;
import org.sid.cinema.dao.TicketRepository;
import org.sid.cinema.dao.VilleRepository;
import org.sid.cinema.entites.Category;
import org.sid.cinema.entites.Cinema;
import org.sid.cinema.entites.Film;
import org.sid.cinema.entites.Place;
import org.sid.cinema.entites.Projection;
import org.sid.cinema.entites.Salle;
import org.sid.cinema.entites.Seance;
import org.sid.cinema.entites.Ticket;
import org.sid.cinema.entites.Ville;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeRepo ;
	@Autowired
	private CinemaRepository cinemaRep ;
	@Autowired
	private SalleRepository salleRep ;
	@Autowired
	private PlaceRepository placeRep ;
	@Autowired 
	private ProjectionRepository projectionRep ;
	@Autowired
	private FilmRepository filmRep ;
	@Autowired
	private CategoryRepository categoryRep ;
	@Autowired
	private SeanceRepository seanceRep ;
	@Autowired
	private TicketRepository ticketRep ;
	@Override
	public void initVilles() {
		 Stream.of("Tunis","Sfax","Gabes","Ariana").forEach(ville_name->{
			 Ville ville = new Ville();
			 ville.setName(ville_name);
			 villeRepo.save(ville);
		 });
		
	}

	@Override
	public void initCinemas() {
		 villeRepo.findAll().forEach(ville->{
			 Stream.of("colise", "REX", "MetroPolie", "Olympia")
			 		.forEach(name->{
			 			int n =  3 + (int)(Math.random()*7);

			 			Cinema cinema = new Cinema();
			 			cinema.setName(name);
			 			cinema.setNombreSalles(n);
			 			cinema.setVille(ville);
			 			cinemaRep.save(cinema);			 			
			 			}
			 		);
		 });	
 		
	}

	@Override
	public void initSalles() {
		 
		cinemaRep.findAll()
				.forEach(cinema->{
					for(int i=1 ; i< cinema.getNombreSalles() ; i++) {
						int nbrePlace = 3 + (int)(Math.random()*20) ;
						Salle salle = new Salle();
						salle.setName("salle-"+i);
						salle.setCinema(cinema);
						salle.setNbrePlace(nbrePlace);
						salleRep.save(salle);
					}
				});	
		}

	@Override
	public void initPlaces() {
		 salleRep.findAll()
		 		 .forEach(salle->{
		 			 for(int i =1 ; i< salle.getNbrePlace();i++) {
		 				 Place place = new Place() ;
		 				 place.setNumero(i);
		 				 place.setSalle(salle);
		 				 placeRep.save(place);
 		 			 }
		 		 });
	}

	@Override
	public void initSeance() {
	 DateFormat dateFormat  = new SimpleDateFormat("HH:mm");
	 Stream.of("10:00" ,"12:00","14:00","15:00","16:00","18:00").forEach(heur->{
		 Seance seance = new Seance();
		 try {
			seance.setHeureDebut(dateFormat.parse(heur));
			seanceRep.save(seance);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 });
		
	}

	@Override
	public void initCategories() {
		Stream.of("Action" ,"Sespance","Histroy","Drama","Remontic").forEach(name->{
			Category category = new Category();
			category.setName(name);
			categoryRep.save(category);
		});
		
	}

	@Override
	public void initFilms() {
		double[] dures = new double[] {1,1.3 , 2,2.3} ;
		List<Category> categories = categoryRep.findAll() ;
		Stream.of("GOT" ,"suits","la casa","Choufli_Hal","Mentalist").forEach(name->{
			int indexDure  = new Random().nextInt(dures.length);
			int indexCategory  = new Random().nextInt(categories.size());
			Film film = new Film();
			film.setTitre(name);
			film.setDuree(dures[indexDure]);
			film.setPhoto(name.replaceAll(" ", "")+".jpg");
			film.setCategory(categories.get(indexCategory));
			filmRep.save(film);
			 
		});
		
	}

	@Override
	public void initProjections() {
		 
		List<Seance> seances = seanceRep.findAll();
		 
		double[] prices = new double[] {10,12 ,15,20 , 25} ;
		List<Film> films = filmRep.findAll();
		
		villeRepo.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index = new Random().nextInt(films.size());
					Film film = films.get(index);
					 
						//int indexSeance  = new Random().nextInt(seances.size());
						int indePrice = new Random().nextInt(prices.length);
						seanceRep.findAll().forEach(seance->{
							Projection projection = new Projection();
						projection.setFilm(film);
						projection.setSalle(salle);
						projection.setSeance(seance);
						projection.setDateProjection(new Date());
						projection.setPrix(prices[indePrice]);
						projectionRep.save(projection);
						});
						
					 
				});
			});
		});
		
	}

	@Override
	public void initTickes() {
		projectionRep.findAll().forEach(pro->{
			pro.getSalle().getPlaces().forEach(place->{
				Random r = new Random();
				//int codePayment = r.nextInt(100) + 1;
				Ticket ticket = new Ticket();
				//ticket.setCodePayement(codePayment);
				ticket.setPrix(pro.getPrix());
				ticket.setPlace(place) ;
				ticket.setProjection(pro);
				//ticket.setReserve(false);
				ticketRep.save(ticket);
			});
			 
		});
		
	}

}
