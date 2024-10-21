package labBd.trabalhoTCC.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import jakarta.transaction.Transactional;
import labBd.trabalhoTCC.model.Apresentacao;
import labBd.trabalhoTCC.model.Grupo;
import labBd.trabalhoTCC.model.Professor;
import labBd.trabalhoTCC.repository.ApresentacaoRepository;
import labBd.trabalhoTCC.repository.BancaRepository;
import labBd.trabalhoTCC.repository.GrupoRepository;
import labBd.trabalhoTCC.repository.ProfessorRepository;

@Controller
public class ApresentacaoController {

	@Autowired
	private ProfessorRepository profRep;
	@Autowired
	private GrupoRepository gpRep;
	@Autowired
	private BancaRepository bcRep;
	@Autowired
	private ApresentacaoRepository apRep;

	private List<Professor> listaProfessor = new ArrayList<>();
	private List<Apresentacao> listaApresentacao = new ArrayList<>();

	@GetMapping("/apresentacoes")
	public ModelAndView listarApresentacao() {
		listaProfessor.clear();
		listaProfessor.addAll(profRep.findAll());
		listaApresentacao.clear();
		listaApresentacao.addAll(apRep.findAll());
		ModelAndView model = new ModelAndView("apresentacoes");
		model.addObject("professores", listaProfessor);
		model.addObject("apresentacoes", listaApresentacao);
		model.addObject("apresentacao", new Apresentacao());
		return model;
	}

	@GetMapping("/apresentacoes/editar/{codigo}") //Só permiti trocar a data da apresentação, pois faz mais sentido deletar ela e criar outra caso seja alterações na banca.
	public ModelAndView editarApresentacao(@PathVariable("codigo") int codigo) {

		Apresentacao apr = apRep.findById(codigo).get();
		ModelAndView model = new ModelAndView("apresentacoes");
		model.addObject("professores", listaProfessor);
		model.addObject("apresentacoes", listaApresentacao);
		model.addObject("apresentacao", apr);
		return model;
	}

	@PostMapping("/apresentacoes")
	public ModelAndView apresentacaoCreate(@ModelAttribute("apresentacao") Apresentacao apresentacao, @RequestParam("acao") String acao,
			@RequestParam(value = "grupo", required = false) int grupo,  @RequestParam(value ="professorCodigos", required = false) List<Integer> professorCodigos) {
		if ("gravar".equals(acao)) {  //Só permiti trocar a data da apresentação, pois faz mais sentido deletar ela e criar outra caso seja alterações na banca.
			Optional<Grupo> grpopt = gpRep.findById(grupo);
			if (grpopt.isPresent()) {
				Grupo gpobj = grpopt.get();
				apresentacao.setGrupo(gpobj);
				if(!apRep.findById(apresentacao.getCodigo()).isPresent()){
					apRep.save(apresentacao);
					bcRep.addProfessores(apresentacao.getCodigo(), professorCodigos);
				} else {
					apRep.save(apresentacao);
				}
			}
		} else if ("pesquisar".equals(acao)) {
			if (apresentacao.getDataApresentacao() == null) {
				listaApresentacao.clear();
				listaApresentacao.addAll(apRep.findAll());
			} else {
				listaApresentacao.clear();
				listaApresentacao.addAll(apRep.findByDataApresentacao(apresentacao.getDataApresentacao()));
			}
		
		}
			
		ModelAndView mv = new ModelAndView("apresentacoes");
		mv.addObject("professores", listaProfessor);
		mv.addObject("apresentacoes", listaApresentacao);
		mv.addObject("apresentacao", new Apresentacao());
		return mv;
	}
	
	
	@GetMapping("apresentacoes/delete/{codigo}")
	public ModelAndView deleteApresentacao(@PathVariable int codigo) {
		apRep.deleteById(codigo);
		listaApresentacao.clear();
		listaApresentacao.addAll(apRep.findAll());
		
		ModelAndView mv = new ModelAndView("apresentacoes");
		mv.addObject("professores", listaProfessor);
		mv.addObject("apresentacoes", listaApresentacao);
		mv.addObject("apresentacao", new Apresentacao());
		return mv;
	}
}
