package com.algaworks.cobranca.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.algaworks.cobranca.model.StatusTitulo;
import com.algaworks.cobranca.model.Titulo;
import com.algaworks.cobranca.repository.Titulos;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String CADASTRO_VIEW = "CadastroTitulo";
	@Autowired
	private Titulos titulos;

	@GetMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("titulo", new Titulo());
		return mv;
	}

	@GetMapping
	public ModelAndView pesquisar() {
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("titulos", titulos.findAll());
		return mv;
	}

	@PostMapping
	public String salvar(@Validated Titulo titulo, Errors erros, RedirectAttributes attributes) {
		if (erros.hasErrors()) {
			return CADASTRO_VIEW;
		}

		try {
			titulos.save(titulo);
			titulo = new Titulo();
			attributes.addFlashAttribute("mensagem", "Título cadastrado com sucesso!");
			return "redirect:/titulos/novo";
		} catch (DataIntegrityViolationException e) {
			erros.rejectValue("dataVencimento", null, "Formato de data inválido");
			return CADASTRO_VIEW;
		}
	}

	@GetMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		// Optional<Titulo> titulo = titulos.findById(codigo);
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject("titulo", titulo);
		return mv;
	}

	@DeleteMapping("{codigo}")
	public String excluir(@PathVariable Long codigo, RedirectAttributes attributes) {
		titulos.deleteById(codigo);
		attributes.addFlashAttribute("mensagem", "Título excluído com sucesso!");
		return "redirect:/titulos";
	}

	@ModelAttribute("todosStatusTitulos")
	public List<StatusTitulo> todosStatusTitulos() {
		return Arrays.asList(StatusTitulo.values());
	}
}
