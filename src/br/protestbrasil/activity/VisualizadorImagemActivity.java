package br.protestbrasil.activity;

import com.loopj.android.image.SmartImage;
import com.loopj.android.image.SmartImageView;

import br.protestbrasil.modelo.MiniaturaImagem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnCreateContextMenuListener;
import android.widget.ImageView;

public class VisualizadorImagemActivity extends Activity {	
	private SmartImageView imagem;
	
	public static void iniciar(Context contexto, MiniaturaImagem miniaturaSerializar) {
		Intent intent = new Intent(contexto, VisualizadorImagemActivity.class);
		intent.putExtra("img", miniaturaSerializar);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		contexto.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.visualizador_activity);
		
		MiniaturaImagem img = (MiniaturaImagem) getIntent().getSerializableExtra("img");
		
		imagem = (SmartImageView) findViewById(R.idVisualizador.imagem);
		imagem.setImageUrl(img.getUrlFotoResolucaoPadrao());
	}
}

