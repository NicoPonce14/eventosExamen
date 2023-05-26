package com.nicolas.eventosexamen.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="eventos")
public class Evento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Future
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date fechaEvento;
	
	@NotBlank
	private String nombreEvento;
	
	@NotBlank
	private String ciudad;
	
	@NotBlank
	private String estado;
	
	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	//relacion muchos a 1 usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User organizador;
	
	//relacion con la table mensaje
	@OneToMany(mappedBy="evento",fetch=FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Mensaje> mensajes;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="users_event", 
			joinColumns = @JoinColumn(name="event_id"),
			inverseJoinColumns = @JoinColumn(name="user_id"))
	private List<User> asistentes;
	


	public Evento() {}
	
	
	public List<User> getAsistentes() {
		return asistentes;
	}
	
	
	
	public void setAsistentes(List<User> asistentes) {
		this.asistentes = asistentes;
	}
	
	
	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getFechaEvento() {
		return fechaEvento;
	}



	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}



	public String getNombreEvento() {
		return nombreEvento;
	}



	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}



	public String getCiudad() {
		return ciudad;
	}



	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}



	public String getEstado() {
		return estado;
	}



	public void setEstado(String estado) {
		this.estado = estado;
	}



	public Date getCreatedAt() {
		return createdAt;
	}



	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}



	public Date getUpdatedAt() {
		return updatedAt;
	}



	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}



	public User getOrganizador() {
		return organizador;
	}



	public void setOrganizador(User organizador) {
		this.organizador = organizador;
	}

	


	public List<Mensaje> getMensajes() {
		return mensajes;
	}


	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}


	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
}
