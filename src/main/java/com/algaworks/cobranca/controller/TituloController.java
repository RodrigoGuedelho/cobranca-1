package com.algaworks.cobranca.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {
	
	@Autowired
	private Titulos titulos;

	@GetMapping("/novo")
	public String novo() {
		return "CadastroTitulo";
	}

	@PostMapping
	public String salvar(Titulo titulo) {
		titulos.save(titulo);
		return "CadastroTitulo";
	}
}
