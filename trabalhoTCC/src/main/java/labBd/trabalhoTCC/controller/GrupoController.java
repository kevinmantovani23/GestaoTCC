package labBd.trabalhoTCC.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import labBd.trabalhoTCC.model.Aluno;
import labBd.trabalhoTCC.model.Area;
import labBd.trabalhoTCC.model.Grupo;
import labBd.trabalhoTCC.model.Professor;
import labBd.trabalhoTCC.model.ProfessorArea;
import labBd.trabalhoTCC.model.Subarea;
import labBd.trabalhoTCC.repository.AlunoRepository;
import labBd.trabalhoTCC.repository.AreaRepository;
import labBd.trabalhoTCC.repository.GrupoRepository;
import labBd.trabalhoTCC.repository.ProfessorAreaRepository;
import labBd.trabalhoTCC.repository.ProfessorRepository;
import labBd.trabalhoTCC.repository.SubareaRepository;

public class GrupoController {
	@Autowired
	private GrupoRepository gpRep;
	@Autowired
	private AlunoRepository alRep;
	@Autowired
	private ProfessorRepository profRep;
	@Autowired
	private SubareaRepository subRep;
	@Autowired
	private AreaRepository arRep;

	private List<Grupo> listaGrupo = new ArrayList<>();
	private List<Professor> listaProfessor = new ArrayList<>();
	private List<Subarea> listaSubarea = new ArrayList<>();

	@GetMapping("/grupos")
	public ModelAndView listarGrupos() {
		listaGrupo.clear();
		listaProfessor.clear();
		listaProfessor.addAll(profRep.findAll());
		listaGrupo.addAll(gpRep.findAll());
		ModelAndView model = new ModelAndView("grupos");
		model.addObject("professores", listaProfessor);
		model.addObject("grupos", listaGrupo);
		model.addObject("professor", new Professor());
		model.addObject("grupo", new Grupo());
		return model;
	}

	@GetMapping("/grupos/editar/{codigo}")
	public ModelAndView editarGrupo(@PathVariable("codigo") int codigo) {

		Grupo grupo = gpRep.findById(codigo).get();
		ModelAndView model = new ModelAndView("grupos");
		model.addObject("grupo", grupo);
		model.addObject("grupos", listaGrupo);
		model.addObject("professores", listaProfessor);
		return model;
	}

	@PostMapping("/grupos")
	public ModelAndView grupoCreate(@ModelAttribute("grupo") Grupo grupo, @RequestParam("professorCod") int professor,
			@RequestParam("subarea") String subarea, @RequestParam("acao") String acao, @RequestParam("area") String area,
			@RequestParam("aluno") String aluno, @RequestParam("professorNome") String profNome,
			@RequestParam("alunoNome") String alNome) {

		if ("gravar".equals(acao)) {
			Professor prof = profRep.findById(professor).get();
			Subarea subareaobj = subRep.findById(subarea).get();
			Area areaobj = arRep.findById(subareaobj.getArea().getNome()).get();
			grupo.setArea(areaobj);
			grupo.setSubArea(subareaobj);
			grupo.setOrientador(prof);
			gpRep.save(grupo);

		} else if ("pesquisarGrupo".equals(acao)) {
			if (profNome == null || profNome.isBlank()) {
				if (alNome == null || alNome.isBlank()) {
					listaGrupo.clear();
					listaGrupo.addAll(gpRep.findAll());
				} else {
					listaGrupo.clear();
					listaGrupo.addAll(profRep.findByNomeAluno(alNome));
				}
			} else {
				listaGrupo.clear();
				listaGrupo.addAll(profRep.findByNomeProf(profNome));
			}

		} else if ("pesquisarArea".equals(acao)) {
			if (area != null || !area.isBlank()) {
				listaSubarea.clear();
				listaSubarea.addAll(subRep.findByNomeArea(area));
				listaProfessor.clear();
				listaProfessor.addAll(profRep.findByNomeArea(area));
			}
			
		} else if ("adicionar".equals(acao)) {
			if (grupo.getOrientador() == null) {

			} else {
				Optional<Aluno> alopt = alRep.findById(aluno);
				if (alopt.isPresent()) {
					Aluno alunoobj = alopt.get();
					gpRep.save(grupo);
					alunoobj.setGrupo(grupo);
					alRep.save(alunoobj);
				}
			}
		}
		ModelAndView mv = new ModelAndView("grupos");
		mv.addObject("professores", lista);
		mv.addObject("professor", new Professor());
		return mv;
	}

	@GetMapping("grupos/delete/{codigo}")
	public ModelAndView deleteProfessor(@PathVariable int codigo) {
		gpRep.deleteById(codigo);
		listaGrupo.clear();
		listaProfessor.clear();
		listaGrupo.addAll(gpRep.findAll());
		listaProfessor.addAll(profRep.findAll());
		ModelAndView mv = new ModelAndView("professores");
		mv.addObject("professores", listaProfessor);
		mv.addObject("grupos", listaGrupo);
		mv.addObject("grupo", new Grupo());
		mv.addObject("professor", new Professor());
		return mv;
	}

}
