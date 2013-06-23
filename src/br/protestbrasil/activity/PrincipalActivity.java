package br.protestbrasil.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.Thread.State;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ListAdapter;
import br.protestbrasil.modelo.MiniaturaImagem;
import br.protestbrasil.modelo.Pagina;

public class PrincipalActivity extends Activity implements Runnable {
	private Thread thread;
	private GridView mGridView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		
		mGridView = (GridView) findViewById(R.idPrincipal.gridView);
		
		try {
			mGridView.setAdapter(getGridViewAdapter(getPaginaCompleta()));
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	private ListAdapter getGridViewAdapter(Pagina paginaCompleta) {		
		return new GridViewAdapter(getBaseContext(), paginaCompleta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	private Pagina getPaginaCompleta() throws Exception {
		Pagina paginaCompleta = new Pagina();
		String linhaJson = null;
		try {
			BufferedReader bufReader = getReaderJson();

	        while ((linhaJson = bufReader.readLine()) != null) {
	            JSONObject jsonLinhaCompleta = new JSONObject(linhaJson);
	            JSONObject jsonPaginacao = jsonLinhaCompleta.getJSONObject("pagination");
	            JSONArray jsonCabecalho = jsonLinhaCompleta.getJSONArray("data");
	            paginaCompleta.setMinimoId(jsonPaginacao.getLong("min_tag_id"));
	
	            for (int i = 0; i < jsonCabecalho.length(); i++) {
	            	MiniaturaImagem miniatura = new MiniaturaImagem();
	                JSONObject dados = (JSONObject) jsonCabecalho.get(i);
	                
	                JSONObject dadosUsuario = dados.getJSONObject("user");
	                JSONObject imagens = (JSONObject) dados.getJSONObject("images");
	                JSONObject resolucaoBaixa = (JSONObject) imagens.getJSONObject("low_resolution");
	                JSONObject resolucaoPadrao = (JSONObject) imagens.getJSONObject("standard_resolution");

	                miniatura.setUrlFotoResolucaoBaixa(resolucaoBaixa.getString("url"));
	                miniatura.setUrlFotoResolucaoPadrao(resolucaoPadrao.getString("url"));
	                miniatura.setUrlFotoNoInstagram(dados.getString("link"));
	                miniatura.setDescricaoImagem(resolucaoPadrao.getString("url"));
	                miniatura.setNomeUsuario(dadosUsuario.getString("username"));
	                miniatura.setNomePessoa(dadosUsuario.getString("full_name"));	  
	                miniatura.setUrlFotoPerfil(dadosUsuario.getString("profile_picture"));
	                			
	                paginaCompleta.adicionarMiniatura(miniatura);
	                Log.d(getClass().getSimpleName() + " Adicionando miniatura: ", resolucaoBaixa.getString("url"));
	            }
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {	
	        e.printStackTrace();
	    }
		return paginaCompleta;
	}
	
	private BufferedReader getReaderJson() throws IOException {
		String token = "fd8538825477422db9da6dd26ee25a4f";
		URL url = new URL("https://api.instagram.com/v1/tags/ogiganteacordou/media/recent?callback=?&client_id=" + token);
		URLConnection tc = url.openConnection();
        return  new BufferedReader(new InputStreamReader(tc.getInputStream()));
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	public boolean estiverCarregandoImagens() {
		State estado = thread != null ? thread.getState() : State.TERMINATED;
		return !estado.equals(State.TERMINATED);
	}
}