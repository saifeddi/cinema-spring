package org.sid.cinema.entites;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

@Projection(name="p1" , types = { org.sid.cinema.entites.Projection.class })
public interface ProjectionProject {

	public Long getId();
	public Date getDateProjection();
	public double getPrix();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
