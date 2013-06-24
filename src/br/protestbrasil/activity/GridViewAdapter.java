package br.protestbrasil.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;
import br.protestbrasil.modelo.MiniaturaImagem;
import br.protestbrasil.modelo.Pagina;

import com.loopj.android.image.SmartImageView;

public class GridViewAdapter extends BaseAdapter implements OnClickListener {
	private Context mContexto;
	private Pagina mPagina;
	private SmartImageView mSmartImageView;
	private Animation mAnimacao;
//	private MiniaturaImagem mMiniaturaAtual;
	
	public GridViewAdapter(Context baseContext, Pagina paginaCompleta) {
		this.mContexto = baseContext;
		this.mPagina = paginaCompleta;
	}

	@Override
	public int getCount() {		
		return this.mPagina.getQuantidadeMiniaturas();
	}

	@Override
	public Object getItem(int posicao) {
		return mPagina.getMiniaturaNaPosicao(posicao);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	
	@Override
	public View getView(int posicao, View convertView, ViewGroup parent) {
		if(convertView == null) {
			LayoutInflater inflater = (LayoutInflater) mContexto.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.grid_view_linha, null);
			mAnimacao = AnimationUtils.loadAnimation(mContexto, android.R.anim.fade_in);
		}
		final MiniaturaImagem mMiniaturaAtual = (MiniaturaImagem) getItem(posicao);
		mSmartImageView = (SmartImageView) convertView.findViewById(R.idGridViewLinha.imgMiniatura);
		mSmartImageView.setImageUrl(mMiniaturaAtual.getUrlFotoResolucaoBaixa());
		mSmartImageView.startAnimation(mAnimacao);
		mSmartImageView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				VisualizadorImagemActivity.iniciar(mContexto,mMiniaturaAtual);
			}
		});
		
		TextView txvUsuario = (TextView) convertView.findViewById(R.idGridViewLinha.txvUsuario);
		txvUsuario.setText(String.format("por: %s (%s)", mMiniaturaAtual.getNomeUsuario(), mMiniaturaAtual.getNomePessoa()));
		
		return convertView;
	}
	
	@Override
	public boolean areAllItemsEnabled() {
		return false;
	}
	
	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.idGridViewLinha.imgMiniatura:				
				break;
		}
	}
}