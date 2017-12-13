package br.com.LeituraAPI.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_torrent")
public class Torrent implements Serializable {
	

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pk_torrent")
	private Integer id;
	
	@Column(name="fk_seriado", nullable=false)
	private Integer idSeriado;
	
	@Column(name="temporada", nullable=false)
	private String temporada;
	
	@Column(name="episodio", nullable=false)
	private String episodio;	

	@Column(name="torrent_url")
	private String torrentURL;
	
	@Column(name="magnet_url")
	private String magnetURL;		
	
	@Column(name="episode_url")
	private String episodioURL;
	
	@Column(name="filename")
	private String fileName;
	
	@Column(name="title")
	private String titulo;
	
	@Column(name="idEZTV")
	private String idEZTV;
	
	@Column(name="idIMDB")
	private String idIMDB;		
	
	@Column(name="size_bytes")
	private Integer sizeBytes;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdSeriado() {
		return idSeriado;
	}

	public void setIdSeriado(Integer idSeriado) {
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

	public String getMagnetURL() {
		return magnetURL;
	}

	public void setMagnetURL(String magnetURL) {
		this.magnetURL = magnetURL;
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

	public Integer getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Integer sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
	
	public String getIdIMDB() {
		return idIMDB;
	}

	public void setIdIMDB(String idIMDB) {
		this.idIMDB = idIMDB;
	}	

}
