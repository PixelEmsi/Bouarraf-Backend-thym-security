package com.pixel.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pixel.dao.CinemaRepository;
import com.pixel.dao.FilmRepository;
import com.pixel.dao.PlaceRepository;
import com.pixel.dao.SalleRepository;
import com.pixel.dao.SeanceRepository;
import com.pixel.dao.TicketRepository;
import com.pixel.dao.UserRepository;
import com.pixel.dao.VilleRepository;
import com.pixel.dao.CategorieRepository;
import com.pixel.dao.ProjectionRepository;
import com.pixel.entities.Categorie;
import com.pixel.entities.Cinema;
import com.pixel.entities.Film;
import com.pixel.entities.Place;
import com.pixel.entities.Projection;
import com.pixel.entities.Salle;
import com.pixel.entities.Seance;
import com.pixel.entities.Ticket;
import com.pixel.entities.User;
import com.pixel.entities.Ville;



@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {
	
	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder encoder;
	
	
	@Override
	public void initUsers() {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUsername("amine");
		String password = "password";
		user.setPassword(encoder.encode(password));
		//user.setPassword(password);
		user.setActive(true);	
		user.setRoles("USER");
		userRepository.save(user);
	}

	@Override
	public void initVille() {
		// TODO Auto-generated method stub
		Stream.of("Casablanca","Marrakesh","Rabat","Tanger").forEach(name->{
			Ville ville = new Ville();
			ville.setName(name);
			villeRepository.save(ville);
		});
	}

	@Override
	public void initCinemas() {
		// TODO Auto-generated method stub

		villeRepository.findAll().forEach(v->{
			Stream.of("Megarama","Imax","Founoun","Chahrazad","Daouliz").forEach(nameCinema->{
				Cinema cinema = new Cinema();
				cinema.setName(nameCinema);
				cinema.setNombreSalle(3+ (int)Math.random()*7);
				cinema.setVille(v);
				cinemaRepository.save(cinema);
			});
		});	
	}

	@Override
	public void initSalles() {
		// TODO Auto-generated method stub
		cinemaRepository.findAll().forEach(cinema->{
			
			for(int i=0; i<cinema.getNombreSalle();i++) {
				
				Salle salle = new Salle();
				salle.setName("Salle"+(i+1));
				salle.setCinema(cinema);
				salle.setNombresPlaces(15+(int)(Math.random()*20));
				salleRepository.save(salle);
			}
		});
		
	}

	@Override
	public void initSeances() {
		// TODO Auto-generated method stub

		DateFormat date= new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","21:00").forEach(s->{
			Seance seance = new Seance();
			
			try {
				seance.setHeureDebut(date.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		});
	}

	@Override
	public void initPlaces() {
		// TODO Auto-generated method stub
		salleRepository.findAll().forEach(salle->{
			for(int i =0; i<salle.getNombresPlaces(); i++) {
				
				Place place = new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initCategories() {
		// TODO Auto-generated method stub
		
		Stream.of("Animation","Action","Drame","Horreur").forEach(cat->{
			
			Categorie categorie = new Categorie();
			categorie.setName(cat);
			categorieRepository.save(categorie);
			
		});
	}

	@Override
	public void initFilms() {
		// TODO Auto-generated method stub
		double[] durees = {1,1.5,2,2.5,3};
		java.util.List<Categorie> categories = categorieRepository.findAll();
		Stream.of("The Shawshank Redemption","Wall-E","the shinning","john Wick").forEach(titre->{
			
			Film film = new Film();
			film.setTitre(titre);
			film.setDuree(durees[new Random().nextInt(durees.length)]);
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			film.setPhoto(titre.replaceAll(" ", "")+".jpg");
			filmRepository.save(film);
		});
	}

	@Override
	public void initProjections() {
		// TODO Auto-generated method stub
		double[] prices  = {30,50,60,70,90,120};
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
					int index = new Random().nextInt(films.size());
						Film film = films.get(index);
						seanceRepository.findAll().forEach(seance->{
							Projection projection = new Projection();
							projection.setDateProjection(new Date());
							projection.setFilm(film);
							projection.setSalle(salle);
							projection.setPrix(prices[new Random().nextInt(prices.length)]);
							projection.setSeance(seance);
							projectionRepository.save(projection);
						});					
				});
			});
		});
	}

	@Override
	public void initTickets() {
		// TODO Auto-generated method stub
		projectionRepository.findAll().forEach(projection->{
			projection.getSalle().getPlaces().forEach(place->{
				Ticket ticket = new Ticket();
				ticket.setPlace(place);
				ticket.setProjection(projection);
				ticket.setReserve(false);
				ticketRepository.save(ticket);
			});
		});
		
	}

}
