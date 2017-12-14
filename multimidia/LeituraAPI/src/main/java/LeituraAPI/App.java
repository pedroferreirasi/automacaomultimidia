package LeituraAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.LeituraAPI.modelo.Seriado;
import br.com.LeituraAPI.modelo.Temporada;
import br.com.LeituraAPI.modelo.Torrent;
import br.com.LeituraAPI.repositorio.dao.SeriadoDaoImpl;
import br.com.LeituraAPI.repositorio.dao.TemporadaDaoImpl;
import br.com.LeituraAPI.repositorio.dao.TorrentDaoImpl;

public class App {
	public static void main(String[] args) {

		SeriadoDaoImpl seriadoDao = new SeriadoDaoImpl();
		TorrentDaoImpl torrentDao = new TorrentDaoImpl();
		TemporadaDaoImpl temporadaDao = new TemporadaDaoImpl();
		List<Torrent> listaTodosTorrents = new ArrayList<Torrent>();
		List<Torrent> listaTorrentsParaDownload = new ArrayList<Torrent>();
		Boolean bAchou = false;

		try {
			List<Seriado> seriado = seriadoDao.getAll();
			Torrent entity = null;
			Temporada temporada = null;

			for (Seriado item : seriado) {
				System.out.println(
						"***************************************************************************************************");
				System.out.println("*                       Iniciando comunicação com API!!! (" + item.getTitulo()
						+ ")               *");
			    System.out.println("* " + item.getId() + "/" + seriado.size());
				System.out.println(
						"***************************************************************************************************");
				String urlString = "https://eztv.ag/api/get-torrents?imdb_id=" + item.getImdbid() + "&page1";

				URL mUrl = new URL(urlString);
				HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
				conn.setDoOutput(true);
				conn.setRequestMethod("GET");
				conn.addRequestProperty("User-Agent", "Mozilla/4.76");

				InputStream inputStream = conn.getInputStream();

				JsonReader rdr = Json.createReader(inputStream);
				JsonObject obj = rdr.readObject();

				JsonArray results = obj.getJsonArray("torrents");

				Integer season = Integer.parseInt(results.get(0).asJsonObject().getString("season"));
				Integer epsode = Integer.parseInt(results.get(0).asJsonObject().getString("episode"));

				List<Temporada> listaTemporada = temporadaDao.getAllBySeriado(item.getId());
				
				Boolean bTemEpisodioNovo = (season >= listaTemporada.get(0).getTemporada()) && (epsode > listaTemporada.get(0).getEpisodio());

				if ((listaTemporada == null) || (listaTemporada.size() == 0) ||  (bTemEpisodioNovo)) {

					System.out.println(
							"***************************************************************************************************");
					System.out.println(
							"*                       Iniciando carga dos torrents!!!                                           *");
					System.out.println(
							"***************************************************************************************************");
					for (JsonObject result : results.getValuesAs(JsonObject.class)) {

						if (torrentDao.getByIdEZTV(Integer.toString(result.getInt("id"))).size() == 0) {
							entity = new Torrent();

							entity.setIdSeriado(item.getId());
							entity.setTemporada(result.getString("season"));
							entity.setEpisodio(result.getString("episode"));
							entity.setIdIMDB(item.getImdbid());
							entity.setIdEZTV(Integer.toString(result.getInt("id")));
							entity.setFileName(result.getString("filename"));
							entity.setMagnetURL(result.getString("magnet_url"));
							entity.setSizeBytes(Long.parseLong(result.getString("size_bytes")));
							entity.setEpisodioURL(result.getString("episode_url"));
							entity.setTorrentURL(result.getString("torrent_url"));
							entity.setTitulo(result.getString("title"));

							torrentDao.add(entity);

							listaTodosTorrents.add(entity);
						}
					}

					System.out.println(
							"***************************************************************************************************");
					System.out.println(
							"*                       Separando os torrents para Download!!!                                    *");
					System.out.println(
							"***************************************************************************************************");

					for (Torrent torrent : listaTodosTorrents) {
						bAchou = false;
						if (listaTorrentsParaDownload.size() == 0) {
							listaTorrentsParaDownload.add(torrent);
						} else {
							for (int i = 0; i < listaTorrentsParaDownload.size(); i++) {
								if ((listaTorrentsParaDownload.get(i).getTemporada().equals(torrent.getTemporada()))
										&& (listaTorrentsParaDownload.get(i).getEpisodio()
												.equals(torrent.getEpisodio()))) {
									bAchou = true;
									if (listaTorrentsParaDownload.get(i).getSizeBytes() > torrent.getSizeBytes()) {
										listaTorrentsParaDownload.set(i, torrent);
									}
								}
							}
							if (!bAchou) {
								listaTorrentsParaDownload.add(torrent);
							}
						}

					}

					System.out.println(
							"***************************************************************************************************");
					System.out.println(
							"*                       Iniciando o download dos torrents!!!                                      *");
					System.out.println(
							"***************************************************************************************************");

					for (Torrent torrent : listaTorrentsParaDownload) {
						gravaArquivoDeURL(torrent.getTorrentURL());
						temporada = new Temporada();
						temporada.setEpisodio(Integer.parseInt(torrent.getEpisodio()));
						temporada.setTemporada(Integer.parseInt(torrent.getTemporada()));
						temporada.setImdbid(torrent.getIdIMDB());
						temporada.setSeriado(torrent.getIdSeriado());

						temporadaDao.add(temporada);
					}

					listaTodosTorrents.clear();
					listaTorrentsParaDownload.clear();
					listaTemporada.clear();
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			System.out.println(
					"***************************************************************************************************");
			System.out.println(
					"*                       Execucao finalizada com sucesso!!!                                        *");
			System.out.println(
					"***************************************************************************************************");
			System.exit(0);
		}
	}

	public static File gravaArquivoDeURL(String stringUrl) {
		try {
			String sistema = getSistemaOperacional();
			String pathLocal = "";
			if (sistema.equals("Windows 10")) {
				pathLocal = "c:\\pedro\\torrent\\";
			} else {
				pathLocal = "//mnt//nasdownload//";
			}
			// Encapsula a URL num objeto java.net.URL
			URL url = new URL(stringUrl);

			// Queremos o arquivo local com o mesmo nome descrito na URL
			// Lembrando que o URL.getPath() ira retornar a estrutura
			// completa de diretorios e voce deve tratar esta String
			// caso nao deseje preservar esta estrutura no seu disco local.
			String nomeArquivoLocal = url.getPath();
			nomeArquivoLocal = nomeArquivoLocal.substring(9, nomeArquivoLocal.length());

			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("GET");
			conn.addRequestProperty("User-Agent", "Mozilla/4.76");

			// Cria streams de leitura (este metodo ja faz a conexao)...
			InputStream inputStream = conn.getInputStream();

			// Cria streams de leitura (este metodo ja faz a conexao)...
			// InputStream is = url.openStream();

			// ... e de escrita.
			FileOutputStream fos = new FileOutputStream(pathLocal + nomeArquivoLocal);

			// Le e grava byte a byte. Voce pode (e deve) usar buffers para
			// melhor performance (BufferedReader).
			int umByte = 0;

			while ((umByte = inputStream.read()) != -1) {
				fos.write(umByte);
			}

			// Nao se esqueca de sempre fechar as streams apos seu uso!
			inputStream.close();
			fos.close();
			// apos criar o arquivo fisico, retorna referencia para o mesmo
			return new File(pathLocal + nomeArquivoLocal);
		} catch (Exception e) {
			// Lembre-se de tratar bem suas excecoes, ou elas tambem lhe tratarão mal!
			// Aqui so vamos mostrar o stack no stderr.
			e.printStackTrace();
		}
		return null;
	}

	public static Boolean pastaExiste(String pathLocal) {
		File diretorio = new File(pathLocal); // ajfilho é uma pasta!
		if (!diretorio.exists()) {
			return false;
		} else {
			return true;
		}
	}

	public static String getSistemaOperacional() {
		return System.getProperty("os.name");
	}
}
