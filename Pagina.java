package br.com.ed.arvoreb;

import java.util.LinkedList;
public class Pagina {
	LinkedList<Integer> chaves;
	LinkedList<Pagina> filhos;
	Pagina pai;
	boolean folha;
	
	public Pagina(Pagina pai){
		this.filhos = new LinkedList<Pagina>();
		this.chaves = new LinkedList<Integer>();
		this.pai 	= pai;
		folha = true;
		
	}
	
	public int getChave(int i){
		return  chaves.get(i);
	}
	
	public Pagina getFilho(int i){
		return  filhos.get(i);
	}
	
}
