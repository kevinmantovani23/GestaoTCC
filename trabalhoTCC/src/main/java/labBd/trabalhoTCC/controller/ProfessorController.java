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

import labBd.trabalhoTCC.model.Area;
import labBd.trabalhoTCC.model.Professor;
import labBd.trabalhoTCC.model.ProfessorArea;
import labBd.trabalhoTCC.model.ProfessorAreaID;
import labBd.trabalhoTCC.repository.AreaRepository;
import labBd.trabalhoTCC.repository.ProfessorAreaRepository;
import labBd.trabalhoTCC.repository.ProfessorRepository;

@Controller
public class ProfessorController {

	@Autowired
	private ProfessorRepository profRep;
	@Autowired
	private ProfessorAreaRepository prArRep;
	@Autowired
	private AreaRepository areaRep;

	private List<Professor> lista = new ArrayList<>();

	@GetMapping("/professores")
	public ModelAndView listarProfessores() {
		lista.addAll(profRep.findAll());
		ModelAndView model = new ModelAndView("professores");
		model.addObject("professores", lista);
		model.addObject("professor", new Professor());
		return model;
	}

	@GetMapping("/professores/editar/{codigo}")
	public ModelAndView editarProfessor(@PathVariable("codigo") int codigo) {

		Professor professor = profRep.findById(codigo).get();
		ModelAndView model = new ModelAndView("professores");
		model.addObject("professor", professor);
		model.addObject("professores", lista);
		return model;
	}

	@PostMapping("/professores")
	public ModelAndView profCreate(@ModelAttribute("professor") Professor professor, @RequestParam("acao") String acao,
			@RequestParam("area") String area) {
		if ("gravar".equals(acao)) {
			profRep.save(professor);
		} else if ("pesquisar".equals(acao)) {
			if (area == null || area.isBlank()) {
				lista.clear();
				lista.addAll(profRep.findAll());
			} else {
				lista.clear();
				lista.addAll(profRep.findByNomeArea(area));
			}
		} else if ("adicionar".equals(acao)) {
			Optional<Area> areaopt = areaRep.findById(area);
			if (areaopt.isPresent()) {
				Area areaobj = areaopt.get();
				ProfessorArea proArea = new ProfessorArea();
				proArea.setArea(areaobj);
				proArea.setProfessor(profRep.findById(professor.getCodigo()).get());
				prArRep.save(proArea);
			}//else?

		}
		ModelAndView mv = new ModelAndView("professores");
		mv.addObject("professores", lista);
		mv.addObject("professor", new Professor());
		return mv;
	}

	@GetMapping("professores/delete/{codigo}")
	public ModelAndView deleteProfessor(@PathVariable int codigo) {
		profRep.deleteById(codigo);
		lista.clear();
		lista.addAll(profRep.findAll());
		ModelAndView mv = new ModelAndView("professores");
		mv.addObject("professores", lista);
		mv.addObject("professor", new Professor());
		return mv;
	}
}
