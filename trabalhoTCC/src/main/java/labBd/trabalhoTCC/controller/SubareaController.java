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
import labBd.trabalhoTCC.model.Subarea;
import labBd.trabalhoTCC.repository.AreaRepository;
import labBd.trabalhoTCC.repository.SubareaRepository;

@Controller
public class SubareaController {

	   @Autowired
	    private SubareaRepository subRep;
	   @Autowired
	   private AreaRepository arRep;
	    private List<Subarea> lista = new ArrayList<>();
	    
	    @GetMapping("/subareas")
	    public ModelAndView listarSubareas() {
	    	ModelAndView model = new ModelAndView("subareas");
	        model.addObject("subareas", lista);
	        model.addObject("subarea", new Subarea()); 
	        return model; 
	    }

	    @GetMapping("/subareas/editar/{nome}")
	    public ModelAndView editarSubarea(@PathVariable("nome") String nome) {
	    	
	        Subarea subarea = subRep.findById(nome).get();
	        ModelAndView model = new ModelAndView("subareas");
	        model.addObject("subarea", subarea);
	        model.addObject("subareas", lista); 
	        return model;  
	    }

	    @PostMapping("/subareas")
		public ModelAndView subareaCreate(@ModelAttribute("subarea") Subarea subarea, @RequestParam("acao") String acao, @RequestParam("area") String area) {
			if ("gravar".equals(acao)) {
				Optional<Area> optarea = arRep.findById(area);
				if(optarea.isPresent()) {
					subarea.setArea(optarea.get());
					subRep.save(subarea);
				}
				
			} else if ("pesquisar".equals(acao)) {
				if(subarea.getNome() == null || subarea.getNome().isBlank() ) {
					lista.clear();
					lista.addAll(subRep.findAll());
				} else {
				lista.clear();
				Optional<Subarea> sub = subRep.findById(subarea.getNome());
				if (sub.isPresent()) {
					lista.add(sub.get());
				}
				
			}}
			ModelAndView mv = new ModelAndView("subareas");
			mv.addObject("subareas", lista);
			mv.addObject("subarea", new Subarea());
			return mv;
		}

	    @GetMapping("subareas/delete/{nome}")
	    public ModelAndView deleteSubarea(@PathVariable String nome) {
	        subRep.deleteById(nome);
	        lista.clear();
			lista.addAll(subRep.findAll());
			ModelAndView mv = new ModelAndView("subareas");
			mv.addObject("subareas", lista);
			mv.addObject("subarea", new Subarea());
			return mv;
	    }

	    
}
