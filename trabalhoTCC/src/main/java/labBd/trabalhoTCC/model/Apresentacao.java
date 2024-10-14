package labBd.trabalhoTCC.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "apresentacao")
@Data
public class Apresentacao {

	 	@Id
	    @Column (name= "codigo", nullable = false)
	    private int codigo;

	    @ManyToOne
	    @JoinColumn(name = "codigoGrupo", nullable = false)
	    private Grupo grupo; 

	    @Column(name = "dataApresentacao", nullable = false)
	    private LocalDate dataApresentacao; 

	    @Column(name = "tipoTcc", length = 4, nullable = false)
	    private String tipoTCC;  // TCC1 ou TCC2

	    @Column(name = "nota", nullable = true)
	    private Double nota;  // Nota final atribuída pela banca

	    // Getters e Setters
}
