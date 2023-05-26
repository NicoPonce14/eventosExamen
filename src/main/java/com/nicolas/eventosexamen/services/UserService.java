package com.nicolas.eventosexamen.services;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.nicolas.eventosexamen.models.User;
import com.nicolas.eventosexamen.repositories.UserRepo;

@Service
public class UserService {

	@Autowired
	private UserRepo userRepo;

	public User registerUser(User user, BindingResult resultado) {
		User userRegistrado = userRepo.findByEmail(user.getEmail());
		if (userRegistrado != null) {
			resultado.rejectValue("email", "Matches", "Correo electronico ya existe");
		}
		if (!user.getPassword().equals(user.getPasswordConfirmation())) {
			resultado.rejectValue("password", "Matches", "La confirmacion de contraseña debe coincidir");
		}
		if (resultado.hasErrors()) {
			return null;
		}

		String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
		user.setPassword(hashed);
		return userRepo.save(user);
	}

	// encontrar un usuario por su email
	public User findByEmail(String email) {
		return userRepo.findByEmail(email);
	}

	// encontrar un usuario por su id
	public User findUserById(Long id) {
		Optional<User> u = userRepo.findById(id);

		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

//autenticar usuario (LOGIN)

	public boolean authenticateUser(String email, String password, BindingResult resultado) {
		// primero encontrar el usuario por su email
		User user = userRepo.findByEmail(email);
//		System.out.println(user + " AQUI USER MAIL");
		// si no lo podemos encontrar por su email, retornamos false
		if (user == null) {
			resultado.rejectValue("email", "Matches", "Email no válido");
			return false;
		} else {
			// si el password coincide devolvemos true, sino, devolvemos false
			if (BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				resultado.rejectValue("password", "Matches", "Password no es válido");
				return false;
			}
		}
	}
}
