package com.nicolas.eventosexamen.repositories;

import org.springframework.data.repository.CrudRepository;

import com.nicolas.eventosexamen.models.Mensaje;

public interface MensajeRepo extends CrudRepository<Mensaje,Long> {

}
