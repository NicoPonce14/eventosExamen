package com.nicolas.eventosexamen.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.eventosexamen.models.Evento;
import com.nicolas.eventosexamen.models.Mensaje;
import com.nicolas.eventosexamen.models.User;
import com.nicolas.eventosexamen.repositories.MensajeRepo;

@Service
public class MensajeService {

	@Autowired
	private MensajeRepo mensajeRepo;
	
	//crear comentario
	public void comentario(User u,Evento e,String comentario) {
		Mensaje men = new Mensaje(comentario,u,e);
		mensajeRepo.save(men);
	}
}
