package com.nicolas.eventosexamen.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nicolas.eventosexamen.models.Evento;
import com.nicolas.eventosexamen.models.User;
import com.nicolas.eventosexamen.repositories.EventoRepo;

@Service
public class EventoService {

	@Autowired
	private EventoRepo servEvento;

	public Evento crearEvento(Evento e) {
		return servEvento.save(e);
	}

	//encuentra todos los eventos en el estado del usuario
	public List<Evento> encontrarEventosenEstado(String estado) {

		return servEvento.findByEstado(estado);
	}
	

	//encuentra todos los eventos en otro estado distinto del usuario
	public List<Evento> encontrarEventosenOtroEstado(String estado) {

		return servEvento.findByEstadoIsNot(estado);
	}
	
	public Evento encontrarPorId(Long Id) {
		return servEvento.findById(Id).orElse(null);
	}
	
	//ACTUALIZAR UN EVENTO
	public Evento guardarCambios(Evento evento) {
		return servEvento.save(evento);
	}
	
	//eliminar evento
	public void eliminarEvento(Long id) {
		servEvento.deleteById(id);
	}
	
	//ADMINISTRAR EVENTO
		public void adminEventos(Evento evento, 
				User usuario, boolean unirse) {
			if(unirse) {
				evento.getAsistentes().add(usuario);
			}else {
				evento.getAsistentes().remove(usuario);
			}
			servEvento.save(evento);
		}
}
