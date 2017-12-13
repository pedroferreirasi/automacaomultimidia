package LeituraAPI;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;

import br.com.LeituraAPI.modelo.Seriado;
import br.com.LeituraAPI.repositorio.dao.SeriadoDaoImpl;

public class App {
	public static void main( String[] args )
    {
		//String urlString = "https://eztv.ag/api/get-torrents?limit=30&page=4";
    	SeriadoDaoImpl seriadoDao = new SeriadoDaoImpl();
    	List<Seriado> seriado = seriadoDao.getAll();
    	
    	for (Seriado item : seriado) 
    	{
			String urlString = "https://eztv.ag/api/get-torrents?imdb_id=" + item.getImdbid() + "&page1";
	
			try {
	        URL mUrl = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) mUrl.openConnection();
	        conn.setDoOutput(true);
	        conn.setRequestMethod("GET");
	        //conn.setRequestProperty("Content-Type", "application/json");
	        conn.addRequestProperty("User-Agent", "Mozilla/4.76");
	
	        InputStream inputStream = conn.getInputStream();
	        //BufferedReader inputStream = new BufferedReader(new InputStreamReader(conn.getInputStream()));       
	        
	        JsonReader rdr = Json.createReader(inputStream);
	        JsonObject obj = rdr.readObject();
	        
	        JsonArray results = obj.getJsonArray("torrents");
	        int i = 1;
	        for (JsonObject result : results.getValuesAs(JsonObject.class)) {
	            System.out.println(i + " - " + result.getString("title").toUpperCase() + " " + result.getString("imdb_id").toUpperCase());
	            i++;
	        }
	        
			} catch(Exception e) {
				e.printStackTrace();
			}
		
    	}
    }
}
