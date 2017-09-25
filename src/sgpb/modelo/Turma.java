package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Entity implementation class for Entity: Turma
 *
 */
@Entity
@Table(name = "tab_turma")
public class Turma implements Serializable {

	public Turma() {
		super();
	}

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_turma")
	private Long id;

	@Column(nullable = false)
	private String descricao;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "data_inicio_turma")
	private Date dataInicioTurma;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(nullable = false, name = "data_cadastro")
	private Date dataCadastro;

	@Column(nullable = false)
	private Boolean status;

	@JoinColumn(name = "id_curso", nullable = false)
	@ManyToOne
	private Curso curso;

	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataInicioTurma() {
		return dataInicioTurma;
	}

	public void setDataInicioTurma(Date dataInicioTurma) {
		this.dataInicioTurma = dataInicioTurma;
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

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public String toString() {
		return descricao + ", " + curso.getNome();
	}

}
