package br.com.LeituraAPI.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="tb_temporada")
public class Temporada implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="pk_seriado")
	private Integer id;
	
	@Column(name="fk_seriado")
	private String idSeriado;
	
	@Column(name="temporada")
	private String temporada;

	@Column(name="torrent_url")
	private String torrentURL;
	
	@Column(name="magnet_url")
	private String magnectURL;

	@Column(name="episode_url")
	private String episodioURL;
	
	@Column(name="episodio")
	private String episodio;
	
	@Column(name="filename")
	private String fileName;
	
	@Column(name="title")
	private String titulo;
	
	@Column(name="idEZTV")
	private String idEZTV;
	
	@Column(name="size_bytes")
	private String sizeBytes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdSeriado() {
		return idSeriado;
	}

	public void setIdSeriado(String idSeriado) {
		this.idSeriado = idSeriado;
	}

	public String getTemporada() {
		return temporada;
	}

	public void setTemporada(String temporada) {
		this.temporada = temporada;
	}

	public String getTorrentURL() {
		return torrentURL;
	}

	public void setTorrentURL(String torrentURL) {
		this.torrentURL = torrentURL;
	}

	public String getMagnectURL() {
		return magnectURL;
	}

	public void setMagnectURL(String magnectURL) {
		this.magnectURL = magnectURL;
	}

	public String getEpisodioURL() {
		return episodioURL;
	}

	public void setEpisodioURL(String episodioURL) {
		this.episodioURL = episodioURL;
	}

	public String getEpisodio() {
		return episodio;
	}

	public void setEpisodio(String episodio) {
		this.episodio = episodio;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getIdEZTV() {
		return idEZTV;
	}

	public void setIdEZTV(String idEZTV) {
		this.idEZTV = idEZTV;
	}

	public String getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(String sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	
	

}
