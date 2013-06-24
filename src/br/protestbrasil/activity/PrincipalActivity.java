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
import android.app.ProgressDialog;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.widget.GridView;
import android.widget.ListAdapter;
import android.widget.Toast;
import br.protestbrasil.modelo.MiniaturaImagem;
import br.protestbrasil.modelo.Pagina;

public class PrincipalActivity extends Activity implements Runnable {
	private Thread thread;
	private GridView mGridView;
	private Pagina paginaCompleta;
	private ProgressDialog pd;
	private Handler handler;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_principal);
		mGridView = (GridView) findViewById(R.idPrincipal.gridView);		
		iniciarCarregamento();
	}
	
	
	private void iniciarCarregamento() {
		pd =new ProgressDialog(PrincipalActivity.this);
		pd.setMessage("Carregando Imagens");
		pd.setCancelable(false);
		pd.show();
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				super.handleMessage(msg);
				
				switch (msg.what) {
				case 1:
					mGridView.setAdapter(getGridViewAdapter());
					break;
				default:
					break;
				}
			}
		};
		Thread thread = new Thread(this);		
		thread.start();	
	}


	private ListAdapter getGridViewAdapter() {		
		return new GridViewAdapter(getBaseContext(), paginaCompleta);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.principal, menu);
		return true;
	}
	
	
	
	private BufferedReader getReaderJson() throws IOException {
		String token = "fd8538825477422db9da6dd26ee25a4f";
		URL url = new URL("https://api.instagram.com/v1/tags/ogiganteacordou/media/recent?callback=?&client_id=" + token);
		URLConnection tc = url.openConnection();
        return  new BufferedReader(new InputStreamReader(tc.getInputStream()));
	}

	@Override
	public void run() {
		BufferedReader bufReader = null;
		String linhaJson = null;
		paginaCompleta = new Pagina();
		try {
			bufReader = getReaderJson();

	        while ((linhaJson = bufReader.readLine()) != null) {
	            JSONObject jsonLinhaCompleta = new JSONObject(linhaJson);
	            JSONObject jsonPaginacao = jsonLinhaCompleta.getJSONObject("pagination");
	            JSONArray jsonCabecalho = jsonLinhaCompleta.getJSONArray("data");
	            paginaCompleta.setMinimoId(jsonPaginacao.getLong("min_tag_id"));
	
	            for (int i = 0; i < jsonCabecalho.length(); i++) {
	            	MiniaturaImagem miniatura = MiniaturaImagem.preencherObjeto((JSONObject)jsonCabecalho.get(i));
	                paginaCompleta.adicionarMiniatura(miniatura);
	            }
	        }
	    } catch (MalformedURLException e) {
	        e.printStackTrace();
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (JSONException e) {	
	        e.printStackTrace();
	    }finally{
	    	pd.dismiss();
	    	handler.sendEmptyMessage(1);
	    	try {
				bufReader.close();
			} catch (IOException e) {
				Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
			}
	    }
	}
	
	public boolean estiverCarregandoImagens() {
		State estado = thread != null ? thread.getState() : State.TERMINATED;
		return !estado.equals(State.TERMINATED);
	}
}