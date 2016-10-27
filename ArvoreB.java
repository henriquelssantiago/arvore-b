package br.com.ed.arvoreb;

//import java.util.Arrays;
import java.util.List;
public class ArvoreB {
		
	Pagina raiz;
	int ordem;
	
	ArvoreB(int ordem){
		this.ordem = ordem;
		raiz = new Pagina(null);
	}
	
	public Pagina buscarPag(Pagina pag, int chave){    //retorna a pagina em que uma chave x deve se encontrar 
		int ramo = 0;
		
		while(ramo < pag.chaves.size() && chave < (int)pag.chaves.get(ramo)){ //enquanto o contador de ramos for menor que a quantidade de chaves na pagina
			ramo ++;										//e a chave sendo procurada for menor que as chaves contidas na pagina, o contador de ramos � incrementado 
		}
		
		if(chave == (int)pag.chaves.get(ramo)){ // a chave est� nessa pag
			return pag;
		}
		if(pag.folha){  //se a pagina for folha, e a chave nao estiver retorna nulo
			return null;
		}
		else
			return buscarPag(pag.getFilho(ramo), chave);
			
	}
	
	public Pagina buscarPagIdeal(Pagina pag, int chave){ // passar a raiz e a chave que se deseja inserir 
		
		if(pag.folha){
			return pag;
		}
			
		
		int ramo = 	0;
		
		while(ramo < pag.chaves.size() && chave > pag.chaves.get(ramo)){ //ramo vai incrementando 
			ramo++;
		}
		
		
		return buscarPagIdeal(pag.getFilho(ramo), chave);
	}
	
	public int buscarPos(Pagina pag, int chave){ // retorna a posi��o na pagina
		int poschave = 0;
		while(poschave < pag.chaves.size() && chave > (int)pag.chaves.get(poschave))
			poschave++;
		
		return poschave;
	}
	/*
	 * �nico m�todo de Inser��o vis�vel ao usu�rio,
	 * os outros s�o s� auxilares
	 * -Recebe somente a chave
	 * -busca a p�gina(folha) ideal para inser��o e
	 * -a respectiva posi��o no vetor de chaves
	 */
	public void inserir(int chave){
		Pagina pag = buscarPagIdeal(raiz, chave);
		int poschave = buscarPos(pag, chave);
		inserir(chave, pag, poschave);
	}
	
	/*
	 * M�todo(Sobrecarregado) auxiliar de inserir
	 * faz a inser��o no vetor de chaves da p�gina ideal
	 * e testa se o mesmo est� cheio 
	 * caso estiver cheio, chamar� o m�todo respons�vel pela
	 * reorganiza��o da �rvore, 'maquiavel()'
	 */
	private void inserir(int chave, Pagina pag, int poschave) {
		pag.chaves.add(poschave, chave);
		if(pag.chaves.size() == ordem - 1){
			maquiavel(pag, chave, poschave);
		}
	}
	
	/*
	 * Faz a divis�o da p�gina que est� cheia em duas
	 * e insere a chave que ir� 'subir' para a p�gina pai
	 */
	private void maquiavel(Pagina pag, int chave, int poschave){
		Pagina x = new Pagina(pag.pai);
		Pagina z = new Pagina(pag.pai);
		int termocentral=(ordem/2);
		
		//'x' e 'z' ser�o as duas p�ginas criadas
		for(int i=0; i<termocentral; i++){
			x.chaves.add(pag.chaves.get(i));
		}
		for(int i=termocentral+1; i<ordem;i++){
			z.chaves.add(pag.chaves.get(i));
		}
		
		//busca a posi��o ideal das p�ginas no vetor de filhos da p�gina pai
		int posf = 0;
		while(pag.pai.filhos.get(posf) != pag){
			posf++;
		}
		
		//liga��es e desligamentos dos ponteiros
		pag.pai.filhos.add(posf,x);
		pag.pai.filhos.add(posf+1,z);
		pag.filhos=null;
		pag.pai=null;
		
		//posi��o ideal do termo que ir� 'subir' na p�gina pai
		int posc = 0;
		while(pag.chaves.get(posc) > pag.chaves.get(termocentral)){
			posc++;
		}
		inserir(pag.chaves.get(termocentral),x.pai,posc);
	}
	
	//metodo teste
	public int pos(int chave, List<Integer> ll){ 
		int poschave = 0;
		while(poschave < ll.size() &&  chave > (int)ll.get(poschave)){
			poschave++;}
		
		return poschave ;
	}
}
