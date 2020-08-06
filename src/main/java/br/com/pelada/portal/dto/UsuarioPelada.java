package br.com.pelada.portal.dto;

import java.io.Serializable;

public class UsuarioPelada implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer idUsuario;
	private Integer idPelada;

	public UsuarioPelada(Integer idUsuario, Integer idPelada) {
		super();
		this.idUsuario = idUsuario;
		this.idPelada = idPelada;
	}

	public UsuarioPelada() {

	}

	public Integer getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Integer idUsuario) {
		this.idUsuario = idUsuario;
	}

	public Integer getIdPelada() {
		return idPelada;
	}

	public void setIdPelada(Integer idPelada) {
		this.idPelada = idPelada;
	}

}
