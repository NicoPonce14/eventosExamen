package com.nicolas.eventosexamen.controllers;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.nicolas.eventosexamen.models.Estados;

import com.nicolas.eventosexamen.models.Login;
import com.nicolas.eventosexamen.models.User;
import com.nicolas.eventosexamen.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
//import com.nicolas.eventosexamen.services.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService serv;
	
	@GetMapping("/")
	public String index(Model viewModel) {
		viewModel.addAttribute("user", new User());
		viewModel.addAttribute("login", new Login());
		viewModel.addAttribute("estados", Estados.estados);
		return "loginreg.jsp";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User usuario, 
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("login", new Login());
			viewModel.addAttribute("estados", Estados.estados);
			return "loginreg.jsp";
		}
		
		User usuarioRegistrado = serv.registerUser(usuario, resultado);
		viewModel.addAttribute("login", new Login());
		if(usuarioRegistrado != null) {
			viewModel.addAttribute("succesRegister", "Gracias por registrarte, por favor login"); 	
		}
		return "loginreg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") Login loginuser, 
			BindingResult resultado, Model viewModel, HttpSession sesion) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("user", new User());
			viewModel.addAttribute("estados", Estados.estados);
			return "loginreg.jsp";
		}
		
		if(serv.authenticateUser(loginuser.getEmail(), 
				loginuser.getPassword(), resultado)) {
			User usuarioLog = serv.findByEmail(loginuser.getEmail());
			sesion.setAttribute("userID",  usuarioLog.getId());
//			System.out.println(sesion.getAttribute("userID") + "atributo ");
			return "redirect:/events";
			
		}else {
			viewModel.addAttribute("user", new User());
			return "loginreg.jsp";
		}
	}
	
	
}
