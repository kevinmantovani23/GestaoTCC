package labBd.trabalhoTCC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "aluno")
@Data
public class Aluno {
	
	 	@Id
	    @Column(name = "ra", length = 20, nullable = false)
	    private String ra;  

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  

	    @Column(name = "percentualConclusao", nullable = false)
	    private Double percentualConclusao;  
	    
	    @ManyToOne
	    @JoinColumn(name = "codigoGrupo")
	    private Grupo grupo; 
	   
}
