package labBd.trabalhoTCC.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "grupo")
@Data
public class Grupo {
	
	 	@Id
	    @Column(name = "codigo", nullable = false)
	    private int codigo;

	    @Column(name = "tema", length = 100, nullable = false)
	    private String tema; 
	    
	    @ManyToOne
	    @JoinColumn(name = "codigoArea")
	    private Area area;
	    
	    @ManyToOne
	    @JoinColumn(name = "codigoSubArea")
	    private Subarea subArea;

	    @ManyToOne
	    @JoinColumn(name = "codigoProfessor")
	    private Professor orientador;  

	    @OneToMany
	    @JoinColumn(name = "codigoGrupo")
	    private List<Aluno> alunos;  

}
