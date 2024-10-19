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
	    @Column(name = "codigo")
	    private int codigo;

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  // Nome do grupo

	    @ManyToOne
	    @JoinColumn(name = "codigoProfessor")
	    private Professor orientador;  // Professor orientador

	    @OneToMany
	    @JoinColumn(name = "codigoGrupo")
	    private List<Aluno> alunos;  // Lista de alunos no grupo (máx 4)

}
