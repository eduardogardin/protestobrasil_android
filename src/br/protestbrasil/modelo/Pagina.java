package br.protestbrasil.modelo;

import java.io.Serializable;
import java.util.ArrayList;

public class Pagina implements Serializable {
	private static final long serialVersionUID = 1L;

	public Pagina() {
		this.miniaturas = new ArrayList<MiniaturaImagem>();
	}

	private ArrayList<MiniaturaImagem> miniaturas;
	private long minimoId;

	public long getMinimoId() {
		return minimoId;
	}

	public void setMinimoId(long minimoId) {
		this.minimoId = minimoId;
	}

	public void adicionarMiniatura(MiniaturaImagem miniatura) {
		//if (!existeMiniatura(miniatura))
			this.miniaturas.add(miniatura);
	}

	public boolean existeMiniatura(MiniaturaImagem miniaturaImagem) {
		for (MiniaturaImagem m : this.miniaturas) {
			if (m.getUrlFotoResolucaoBaixa().equals(miniaturaImagem.getUrlFotoResolucaoBaixa()))
				return true;
		}
		return false;
	}

	public void esvaziarPagina() {
		this.miniaturas.clear();
	}

	public int getQuantidadeMiniaturas() {
		return this.miniaturas.size();
	}

	public MiniaturaImagem getMiniaturaNaPosicao(int posicao) {
		return this.miniaturas.get(posicao);
	}

	public ArrayList<MiniaturaImagem> getMiniaturas() {
		return this.miniaturas;
	}
}