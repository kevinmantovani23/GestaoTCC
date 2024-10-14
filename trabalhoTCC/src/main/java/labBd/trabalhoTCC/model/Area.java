package labBd.trabalhoTCC.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "area")
@Data
public class Area {
	 	@Id
	    @Column(name = "codigo")
	    private int codigo;

	    @Column(name = "nome", length = 100, nullable = false)
	    private String nome;  
}
