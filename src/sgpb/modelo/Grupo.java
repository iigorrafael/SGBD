package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Grupo
 *
 */
@Entity
@Table(name = "tab_grupo")
public class Grupo implements Serializable {

	public Grupo() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_grupo")
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "data_cadastro")
	private Date dataCadastro;

	@Column(nullable = false)
	private Boolean status;

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

}
