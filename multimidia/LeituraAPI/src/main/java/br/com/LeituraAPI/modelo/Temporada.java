package br.com.LeituraAPI.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tb_temporada")
public class Temporada implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="pk_season")
	private int id;
	
	@Column(name="fk_seriado")
	private int seriado;
	
	//@Column(name="fk_torrent")
	//private int torrent;
	
	@Column(name="temporada")
	private int temporada;
	
	@Column(name="episodio")
	private int episodio;
	
	@Column(name="imdbid")
	private String imdbid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSeriado() {
		return seriado;
	}

	public void setSeriado(int seriado) {
		this.seriado = seriado;
	}

	/*public int getTorrent() {
		return torrent;
	}

	public void setTorrent(int torrent) {
		this.torrent = torrent;
	}*/

	public int getTemporada() {
		return temporada;
	}

	public void setTemporada(int temporada) {
		this.temporada = temporada;
	}

	public int getEpisodio() {
		return episodio;
	}

	public void setEpisodio(int episodio) {
		this.episodio = episodio;
	}

	public String getImdbid() {
		return imdbid;
	}

	public void setImdbid(String imdbid) {
		this.imdbid = imdbid;
	}
		

}
