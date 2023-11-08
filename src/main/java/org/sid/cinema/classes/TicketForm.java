package org.sid.cinema.classes;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class TicketForm {
	private String nameClient ;
	private int codePayment ;
	private List<Long> tickets = new ArrayList<>();
}
