package sgpb.modelo;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tab_autenticacao")
public class Autenticacao implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id_autenticacao")
	private Long id;

	@Column(name = "data_hora_autenticacao", nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataHora;

	@ManyToMany
	@JoinTable(name="tab_pessoa_autenticacao")
	@Column(name = "pessoa_autenticacao", nullable = false)
	private List<Pessoa> pessoas;

	@ManyToMany
	@JoinTable(name="tab_pessoa_autenticacao")
	@Column(name = "evento_autenticacao", nullable = false)
	private List<Evento> evento;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDataHora() {
		return dataHora;
	}

	public void setDataHora(Date dataHora) {
		this.dataHora = dataHora;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}

	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public List<Evento> getEvento() {
		return evento;
	}

	public void setEvento(List<Evento> evento) {
		this.evento = evento;
	}
	
	

}
