package sgpb.modelo;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Entity implementation class for Entity: Aluno
 *
 */
@Entity
@Table(name = "tab_aluno")
@PrimaryKeyJoinColumn(name = "id_pessoa")
public class Aluno extends Pessoa implements Serializable {

	public Aluno() {
		super();
	}

	private static final long serialVersionUID = 1L;
	

	private String situacao;
	// 1 para sim 2 para n�o
	
	private String nomeResponsavel;
	
	
	private String telefoneResponsavel;

	@Column(name = "permite_cadastro_certificado")
	private Integer permiteCadastroCertificado;









	public String getNomeResponsavel() {
		return nomeResponsavel;
	}

	public void setNomeResponsavel(String nomeResponsavel) {
		this.nomeResponsavel = nomeResponsavel;
	}

	public String getTelefoneResponsavel() {
		return telefoneResponsavel;
	}

	public void setTelefoneResponsavel(String telefoneResponsavel) {
		this.telefoneResponsavel = telefoneResponsavel;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public Integer getPermiteCadastroCertificado() {
		return permiteCadastroCertificado;
	}

	public void setPermiteCadastroCertificado(Integer permiteCadastroCertificado) {
		this.permiteCadastroCertificado = permiteCadastroCertificado;
	}
}
