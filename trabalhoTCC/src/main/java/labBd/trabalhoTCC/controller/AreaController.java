package labBd.trabalhoTCC.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import labBd.trabalhoTCC.model.Area;
import labBd.trabalhoTCC.repository.AreaRepository;

@Controller
public class AreaController {

	 @Autowired
	    private AreaRepository arRep;

	    private List<Area> lista = new ArrayList<>();
	    
	    @GetMapping("/areas")
	    public ModelAndView listarAreas() {
	    	ModelAndView model = new ModelAndView("areas");
	        model.addObject("areas", lista);
	        model.addObject("area", new Area()); 
	        return model; 
	    }

	    @GetMapping("/areas/editar/{nome}")
	    public ModelAndView editarArea(@PathVariable("nome") String nome) {
	    	
	        Area area = arRep.findById(nome).get();
	        ModelAndView model = new ModelAndView("areas");
	        model.addObject("area", area);
	        model.addObject("areas", lista); 
	        return model;  
	    }

	    @PostMapping("/areas")
		public ModelAndView areaCreate(@ModelAttribute("area") Area area, @RequestParam("acao") String acao) {
			if ("gravar".equals(acao)) {
				arRep.save(area);
			} else if ("pesquisar".equals(acao)) {
				if(area.getNome() == null || area.getNome().isBlank()) {
					lista.clear();
					lista.addAll(arRep.findAll());
				} else {
				lista.clear();
				lista.addAll(arRep.findByNome(area.getNome()));
			}}
			ModelAndView mv = new ModelAndView("areas");
			mv.addObject("areas", lista);
			mv.addObject("area", new Area());
			return mv;
		}

	    @GetMapping("areas/delete/{nome}")
	    public ModelAndView deleteArea(@PathVariable String nome) {
	        arRep.deleteById(nome);
	        lista.clear();
			lista.addAll(arRep.findAll());
			ModelAndView mv = new ModelAndView("areas");
			mv.addObject("areas", lista);
			mv.addObject("area", new Area());
			return mv;
	    }
}
