package br.com.example.spring.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import br.com.example.spring.enums.TipoPermissao;

@Entity
@Table(name = "USUARIO", indexes = { @Index(name = "IDX_EMAIL", columnList = "EMAIL") })
public class Usuario implements Serializable {

	private static final long serialVersionUID = -7023455488399103384L;

	@Id
	@SequenceGenerator(name = "GENERATOR_USUARIO", sequenceName = "USU_SEQ")
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "GENERATOR_USUARIO")
	@Column(name = "ID", nullable = false, unique = true)
	private Long id;

	@Column(name = "SENHA", nullable = false)
	private String senha;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;

	@Column(name = "ATIVO", nullable = false)
	private Boolean status;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "PERMISSAO", nullable = false)
	private TipoPermissao permissao;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean getStatus() {
		return status;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public TipoPermissao getPermissao() {
		return permissao;
	}

	public void setPermisao(TipoPermissao permissao) {
		this.permissao = permissao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
