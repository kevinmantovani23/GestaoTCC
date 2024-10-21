package labBd.trabalhoTCC.controller;

import java.time.LocalDate;
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

import labBd.trabalhoTCC.model.Aluno;
import labBd.trabalhoTCC.model.Area;
import labBd.trabalhoTCC.model.Grupo;
import labBd.trabalhoTCC.model.Professor;
import labBd.trabalhoTCC.model.Subarea;
import labBd.trabalhoTCC.repository.AlunoRepository;
import labBd.trabalhoTCC.repository.AreaRepository;
import labBd.trabalhoTCC.repository.GrupoRepository;
import labBd.trabalhoTCC.repository.ProfessorRepository;
import labBd.trabalhoTCC.repository.SubareaRepository;

@Controller
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
		ModelAndView mv = new ModelAndView("grupos");
		mv.addObject("professores", listaProfessor);
		mv.addObject("grupos", listaGrupo);
		mv.addObject("subareas", listaSubarea);
		mv.addObject("grupo", new Grupo());
		return mv;
	}

	@GetMapping("/grupos/editar/{codigo}")
	public ModelAndView editarGrupo(@PathVariable("codigo") int codigo) {

		Grupo grupo = gpRep.findById(codigo).get();
		ModelAndView mv = new ModelAndView("grupos");
		mv.addObject("professores", listaProfessor);
		mv.addObject("grupos", listaGrupo);
		mv.addObject("subareas", listaSubarea);
		mv.addObject("grupo", grupo);
		return mv;
	}

	@PostMapping("/grupos")
	public ModelAndView grupoCreate(@ModelAttribute("grupo") Grupo grupo,
			@RequestParam(name = "professorCod", required = false) Integer professor,
			@RequestParam(name = "subarea", required = false) String subarea, @RequestParam("acao") String acao,
			@RequestParam(name = "area", required = false) String area,
			@RequestParam(name = "aluno", required = false) String aluno,
			@RequestParam(name = "professorNome", required = false) String profNome,
			@RequestParam(name = "alunoNome", required = false) String alNome,
			@RequestParam(name = "data", required = false) LocalDate data) {

		if ("gravar".equals(acao)) {
			Professor prof = profRep.findById(professor).get();
			Subarea subareaobj = subRep.findById(subarea).get();
			Area areaobj = arRep.findById(subareaobj.getArea().getNome()).get();
			grupo.setArea(areaobj);
			grupo.setSubArea(subareaobj);
			grupo.setOrientador(prof);
			gpRep.save(grupo);
			grupo = new Grupo();

		} else if ("pesquisarGrupo".equals(acao)) {
			if (profNome == null || profNome.isBlank()) {
				if (alNome == null || alNome.isBlank()) {
					if (data == null) {
						listaGrupo.clear();
						listaGrupo.addAll(gpRep.findAll());
					} else {
						listaGrupo.clear();
						listaGrupo.addAll(gpRep.findByDataAgendamento(data));
					}
				} else {
					listaGrupo.clear();
					listaGrupo.addAll(gpRep.findByNomeAluno(alNome));
				}

			} else {
				listaGrupo.clear();
				listaGrupo.addAll(gpRep.findByNomeProfessor(profNome));
			}
			grupo = new Grupo();
		} else if ("pesquisarArea".equals(acao)) {

			if (area != null && !area.isBlank()) {
				listaSubarea.clear();
				listaSubarea.addAll(subRep.findByNomeArea(area));
				listaProfessor.clear();
				listaProfessor.addAll(profRep.findByNomeArea(area));
			}

		} else if ("adicionar".equals(acao)) {
			if (gpRep.findById(grupo.getCodigo()).isEmpty()) {
				System.out.print("grupo nao existe");
			} else {
				Optional<Aluno> alopt = alRep.findById(aluno);
				if (alopt.isPresent()) {
					Aluno alunoobj = alopt.get();
					alunoobj.setGrupo(grupo);
					alRep.save(alunoobj);
					grupo = new Grupo();
				}
			}
		}
		ModelAndView mv = new ModelAndView("grupos");
		mv.addObject("professores", listaProfessor);
		mv.addObject("grupos", listaGrupo);
		mv.addObject("subareas", listaSubarea);
		mv.addObject("grupo", grupo);
		return mv;
	}

	@GetMapping("grupos/delete/{codigo}")

	public ModelAndView deleteGrupo(@PathVariable int codigo) {
		Grupo grupo = gpRep.findById(codigo).get();
		for (Aluno aluno : grupo.getAlunos()) {
			aluno.setGrupo(null);
			alRep.save(aluno);
		}
		gpRep.deleteById(codigo);
		listaGrupo.clear();
		listaGrupo.addAll(gpRep.findAll());
		ModelAndView mv = new ModelAndView("grupos");
		mv.addObject("professores", listaProfessor);
		mv.addObject("grupos", listaGrupo);
		mv.addObject("subareas", listaSubarea);
		mv.addObject("grupo", new Grupo());
		return mv;
	}

}
