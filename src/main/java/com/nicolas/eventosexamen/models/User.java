package com.nicolas.eventosexamen.models;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min = 1, max = 30, message = "Nombre debe contener entre 1 y 30 caracteres")
	private String firstName;
	
	@NotBlank
	@Size(min = 1, max = 30, message = "apellido debe contener entre 1 y 30 caracteres")
	private String lastName;

	@Email(message = "El correo ingresado no es valido")
	@NotBlank(message = "Por favor, no olvides ingresar un correo electronico")
	private String email;
	
	@NotBlank
	@Size(min = 1, max = 30, message = "location debe contener entre 1 y 30 caracteres")
	private String location;
	
	@NotBlank(message="por favor selecciona un estado")
	private String state;

	@NotBlank(message = "Por favor ingresa tu password")
	@Size(min = 8, max = 64, message = "Password debe tener minimo 8 caracteres")
	private String password;

	@Transient
	@NotBlank(message = "Por favor confirma tu password")
	@Size(min = 8, max = 64, message = "Password debe tener minimo 8 caracteres")
	private String passwordConfirmation;

	@Column(updatable = false)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date createdAt;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date updatedAt;
	
	//Relacion 1 a muchos eventos
	@OneToMany(mappedBy="organizador",fetch=FetchType.LAZY)
	private List<Evento> eventosOrganizados;
	
	//relacion con la tabla mensaje
	@OneToMany(mappedBy="autor",fetch=FetchType.LAZY)
	private List<Mensaje> mensajes;
	
	@ManyToMany(fetch=FetchType.LAZY)
	@JoinTable(
			name="users_event", 
			joinColumns = @JoinColumn(name="user_id"),
			inverseJoinColumns = @JoinColumn(name="event_id"))
	private List<Evento> eventos;
	
	public List<Evento> getEventosOrganizados() {
		return eventosOrganizados;
	}

	public void setEventosOrganizados(List<Evento> eventosOrganizados) {
		this.eventosOrganizados = eventosOrganizados;
	}

	public List<Evento> getEventos() {
		return eventos;
	}

	public void setEventos(List<Evento> eventos) {
		this.eventos = eventos;
	}

	public User() {}
	
	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPasswordConfirmation() {
		return passwordConfirmation;
	}

	public void setPasswordConfirmation(String passwordConfirmation) {
		this.passwordConfirmation = passwordConfirmation;
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

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	

	
}
