package br.protestbrasil.modelo;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
	
	public static MiniaturaImagem preencherObjeto(JSONObject objetoPai) throws JSONException {
		MiniaturaImagem miniatura  = new MiniaturaImagem();
        JSONObject dadosUsuario    = objetoPai.getJSONObject("user");
        JSONObject imagens         = (JSONObject) objetoPai.getJSONObject("images");
        JSONObject resolucaoBaixa  = (JSONObject) imagens.getJSONObject("low_resolution");
        JSONObject resolucaoPadrao = (JSONObject) imagens.getJSONObject("standard_resolution");

        miniatura.setUrlFotoResolucaoBaixa(resolucaoBaixa.getString("url"));
        miniatura.setUrlFotoResolucaoPadrao(resolucaoPadrao.getString("url"));
        miniatura.setUrlFotoNoInstagram(objetoPai.getString("link"));
        miniatura.setDescricaoImagem(resolucaoPadrao.getString("url"));
        miniatura.setNomeUsuario(dadosUsuario.getString("username"));
        miniatura.setNomePessoa(dadosUsuario.getString("full_name"));	  
        miniatura.setUrlFotoPerfil(dadosUsuario.getString("profile_picture"));
		return miniatura;
	}
}