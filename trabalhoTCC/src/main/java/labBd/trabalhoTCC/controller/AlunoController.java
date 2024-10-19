package labBd.trabalhoTCC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import labBd.trabalhoTCC.model.Aluno;
import labBd.trabalhoTCC.repository.AlunoRepository;

import java.util.ArrayList;
import java.util.List;


@Controller
public class AlunoController {

    @Autowired
    private AlunoRepository alRep;

    private List<Aluno> lista = new ArrayList<>();
    
    @GetMapping("/alunos")
    public ModelAndView listarAlunos() {
    	ModelAndView model = new ModelAndView("alunos");
        model.addObject("lista", lista);
        model.addObject("aluno", new Aluno()); 
        return model; 
    }

    @GetMapping("/alunos/editar/{ra}")
    public ModelAndView editarAluno(@PathVariable("ra") String ra) {
    	
        Aluno aluno = alRep.findByRa(ra);
        ModelAndView model = new ModelAndView("alunos");
        model.addObject("aluno", aluno);
        model.addObject("lista", lista); 
        return model;  
    }

    @PostMapping("/alunos")
	public ModelAndView alunoCreate(@ModelAttribute("aluno") Aluno aluno, @RequestParam("acao") String acao) {
		if ("gravar".equals(acao)) {
			alRep.save(aluno);
		} else if ("pesquisar".equals(acao)) {
			if(aluno.getPercentualConclusao() == null) {
				lista.clear();
				lista.addAll(alRep.findAll());
			} else {
			lista.clear();
			lista.addAll(alRep.findByPercentualConclusao(aluno.getPercentualConclusao()));
		}}
		ModelAndView mv = new ModelAndView("alunos");
		mv.addObject("lista", lista);
		mv.addObject("aluno", new Aluno());
		return mv;
	}

    @GetMapping("alunos/delete/{ra}")
    public String deleteAluno(@PathVariable String ra) {
        alRep.deleteById(ra);
        lista.clear();
		lista.addAll(alRep.findByNome(""));
        return "redirect:/alunos"; // Redireciona para a lista de alunos
    }

    
}