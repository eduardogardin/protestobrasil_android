package br.protestbrasil.activity;

import br.protestbrasil.modelo.MiniaturaImagem;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class VisualizadorImagemActivity extends Activity {	
	
	public static void iniciar(Context contexto, MiniaturaImagem miniaturaSerializar) {
		Intent intent = new Intent(contexto, VisualizadorImagemActivity.class);
		intent.putExtra("", miniaturaSerializar);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		contexto.startActivity(intent);
	}
}

