package br.protestbrasil.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONObject;

import android.util.Log;

public class MiniaturaImagem implements Serializable {
	private static final long serialVersionUID = 1L;

	public MiniaturaImagem() {
		this.tagsImagem = new ArrayList<String>();
	}

	private String nomeUsuario;
	private String urlFotoResolucaoBaixa;
	private String urlFotoResolucaoPadrao;
	private String urlFotoPerfil;
	private String urlFotoNoInstagram;
	private String nomePessoa;
	private String descricaoImagem;
	private ArrayList<String> tagsImagem;
	private String localizacao;

	public String getNomeUsuario() {
		return nomeUsuario;
	}

	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}

	public String getUrlFotoResolucaoBaixa() {
		return urlFotoResolucaoBaixa;
	}

	public void setUrlFotoResolucaoBaixa(String urlFotoResolucaoBaixa) {
		this.urlFotoResolucaoBaixa = urlFotoResolucaoBaixa;
	}

	public String getUrlFotoResolucaoPadrao() {
		return urlFotoResolucaoPadrao;
	}

	public void setUrlFotoResolucaoPadrao(String urlFotoResolucaoPadrao) {
		this.urlFotoResolucaoPadrao = urlFotoResolucaoPadrao;
	}

	public String getUrlFotoNoInstagram() {
		return urlFotoNoInstagram;
	}

	public void setUrlFotoNoInstagram(String urlFotoNoInstagram) {
		this.urlFotoNoInstagram = urlFotoNoInstagram;
	}

	public String getLocalizacao() {
		return localizacao;
	}

	public void setLocalizacao(String localizacao) {
		this.localizacao = localizacao;
	}

	public void setTagsImagem(ArrayList<String> listaTags) {
		this.tagsImagem = listaTags;
	}

	public ArrayList<String> getTags() {
		return this.tagsImagem;
	}

	public String getUrlFotoPerfil() {
		return urlFotoPerfil;
	}

	public void setUrlFotoPerfil(String urlFotoPerfil) {
		this.urlFotoPerfil = urlFotoPerfil;
	}

	public String getNomePessoa() {
		return nomePessoa;
	}

	public void setNomePessoa(String nomePessoa) {
		this.nomePessoa = nomePessoa;
	}

	public String getDescricaoImagem() {
		return descricaoImagem;
	}

	public void setDescricaoImagem(String descricaoImagem) {
		this.descricaoImagem = descricaoImagem;
	}
	
	public MiniaturaImagem preencherObjeto(JSONObject objetoPai) {
		MiniaturaImagem miniatura = null;
		for (int i = 0; i < objetoPai.length(); i++) {
			miniatura = new MiniaturaImagem();
            JSONObject dados = (JSONObject) objetoPai.get(i);
            
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
        }
		return new MiniaturaImagem();
	}
}