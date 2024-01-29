package org.sid.cinema.entites;
import javax.persistence.ManyToOne;

import org.springframework.data.rest.core.config.Projection;
@Projection(name="t1" , types = {Ticket.class })

public interface TicketProjection {
 	public Long getId();
 	public String getNameClient() ;
	public double getPrix() ;
	public int getCodePayement() ;
	public boolean getReserve() ;
 	public Place getPlace() ;
}
