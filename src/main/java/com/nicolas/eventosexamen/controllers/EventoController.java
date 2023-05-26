package com.nicolas.eventosexamen.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.nicolas.eventosexamen.models.Estados;
import com.nicolas.eventosexamen.models.Evento;
import com.nicolas.eventosexamen.models.User;
import com.nicolas.eventosexamen.services.EventoService;
import com.nicolas.eventosexamen.services.MensajeService;
import com.nicolas.eventosexamen.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EventoController {
	@Autowired
	private UserService userService;

	@Autowired
	private EventoService eventService;
	
	@Autowired
	private MensajeService mensajeService;

	// crear evento
	@GetMapping("/events")
	public String welcome(HttpSession sesion, Model viewModel, @ModelAttribute("event") Evento evento) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		User usuario = userService.findUserById(userId);
		viewModel.addAttribute("usuario", usuario);
		viewModel.addAttribute("estados", Estados.estados);
		viewModel.addAttribute("eventosEstado", eventService.encontrarEventosenEstado(usuario.getState()));
		// carga la tabla con eventos en otros estados
		viewModel.addAttribute("eventosotroEstado", eventService.encontrarEventosenOtroEstado(usuario.getState()));
		return "/event/index.jsp";
	}

	@PostMapping("/new/event")
	public String crearEvento(@Valid @ModelAttribute("event") Evento evento, BindingResult result, Model m) {
		
		if (result.hasErrors()) {
			User usuario = userService.findUserById(evento.getOrganizador().getId());
			m.addAttribute("usuario", usuario);
			m.addAttribute("estados", Estados.estados);
			m.addAttribute("estados", Estados.estados);
			m.addAttribute("eventosEstado", eventService.encontrarEventosenEstado(usuario.getState()));
			// carga la tabla con eventos en otros estados
			m.addAttribute("eventosotroEstado", eventService.encontrarEventosenOtroEstado(usuario.getState()));
			return "/event/index.jsp";
		}
		
		
		

		eventService.crearEvento(evento);
		return "redirect:/events";

	}

	// EDITAR EVENTO
	@GetMapping("/events/{idevento}/edit")
	public String editarEvento(@PathVariable("idevento") Long id, @ModelAttribute("event") Evento evento,
			HttpSession sesion, Model m) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		Evento unEvento = eventService.encontrarPorId(id);
		if (unEvento == null) {
			return "redirect:/events";
		}
		String[] listaEstados = Arrays.stream(Estados.estados).filter(estado -> !estado.contains(unEvento.getEstado()))
				.toArray(String[]::new);

		m.addAttribute("usuario", userService.findUserById(userId));
		m.addAttribute("eventoxid", unEvento);
		m.addAttribute("estados", listaEstados);
		return "/event/editar.jsp";
	}

	@PutMapping("/edit/{id}")
	public String guardarEdit(@Valid @ModelAttribute("event") Evento evento, BindingResult result,
			@PathVariable("id") Long id, HttpSession sesion, Model m) {

		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		if (result.hasErrors()) {
			Evento unEvento = eventService.encontrarPorId(id);
			if (unEvento == null) {
				return "redirect:/events";
			}
			String[] listaEstados = Arrays.stream(Estados.estados)
					.filter(estado -> !estado.contains(unEvento.getEstado())).toArray(String[]::new);

			m.addAttribute("usuario", userService.findUserById(userId));
			m.addAttribute("eventoxid", unEvento);
			m.addAttribute("estados", listaEstados);

			return "/event/editar.jsp";
		}

		eventService.guardarCambios(evento);
		return "redirect:/events";
	}

	// ELIMINAR EVENTO
	@DeleteMapping("/delete/{id}")
	public String eliminarEvento(@PathVariable("id") Long id) {
		eventService.eliminarEvento(id);
		return "redirect:/events";
	}

	// Administrar EVENTO unirse
	@GetMapping("/events/{id}/{opcion}")
	public String adminEvento(@PathVariable("id") Long id, @PathVariable("opcion") String opcion, HttpSession sesion,
			Model m) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		Evento unEvento = eventService.encontrarPorId(id);
		boolean unirse = (opcion.equals("unirse"));
		User usuario = userService.findUserById(userId);

		eventService.adminEventos(unEvento, usuario, unirse);
		return "redirect:/events";
	}

	// MENSAJES DE EVENTOS
	@GetMapping("/events/{idEvento}")
	public String mostrarEvento(Model m,@PathVariable("idEvento")Long id,HttpSession sesion) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		m.addAttribute("event", eventService.encontrarPorId(id));
		return "/event/mostrar.jsp";
	}
	
	
	
	
	@PostMapping("/events/{idEvento}/comentario")
	public String postComentario(@PathVariable("idEvento")Long id,@RequestParam("comentario")String comentario,HttpSession sesion,RedirectAttributes redirectAtt) {
		Long userId = (Long) sesion.getAttribute("userID");
		if (userId == null) {
			return "redirect:/";
		}
		if(comentario.equals("")) {
			redirectAtt.addFlashAttribute("error","Por favor escribe un comentario");
			return "redirect:/events/"+id;
		}
		Evento evento = eventService.encontrarPorId(id);
		User usuario = userService.findUserById(userId);
		mensajeService.comentario(usuario, evento, comentario);
		
		return "redirect:/events/"+id;
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userID", null);
		return "redirect:/";
	}

}
