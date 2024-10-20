package labBd.trabalhoTCC.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BancaID  implements Serializable {

	private static final long serialVersionUID = 1L;
	private Professor professor;
	private Apresentacao apresentacao;
	
}
