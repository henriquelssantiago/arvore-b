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
		if(pag.chaves.size() == ordem){
			maquiavel(pag);
		}
	}

	/*
	 * Faz a divis�o da p�gina que est� cheia em duas('x' e 'z')
	 * e insere a chave que ir� 'subir' para a p�gina pai
	 */
	private void maquiavel(Pagina pag){//pag = raiz
		Pagina x = new Pagina(pag);
		Pagina z = new Pagina(pag);
		int termocentral=(ordem/2);

		//'x' e 'z' ser�o as duas p�ginas criadas
		for(int i=0; i<termocentral; i++){
			x.chaves.add(pag.chaves.get(i));
		}
		for(int i=termocentral+1; i<ordem; i++){
			z.chaves.add(pag.chaves.get(i));
		}
		int posf = 0;
		//se for raiz, a p�gina n�o sumir�
		//		if(pag == raiz){
		//			for(int i=0; i<termocentral; i++){
		//				pag.chaves.removeFirst();
		//			}
		//			for(int i=1; i<termocentral; i++){
		//				pag.chaves.remove(i);
		//			}
		//		}else{
		//			//busca a posi��o ideal das p�ginas no vetor de filhos da p�gina pai
		//			while(posf < pag.pai.filhos.size() && pag.pai.filhos.get(posf) != pag){
		//				posf++;
		//			}
		//		}

		if(pag != raiz){
			while(pag.pai.filhos.get(posf) != pag){
				posf++;
			}
		}else{
			posf = 0;
		}

		//caso n�o seja folha, as p�ginas 'x' e 'z' ter�o filhos	
		if(!pag.folha){
			for(int i=0; i<=termocentral; i++){
				x.filhos.add(i, pag.filhos.get(i));
				x.filhos.get(i).pai = x;
			}
			for(int i=termocentral+1; i<=ordem; i++){
				z.filhos.add(i,pag.filhos.get(i));
				z.filhos.get(i).pai = z;
			}
		}

		//posi��o ideal do termo que ir� 'subir' na p�gina pai
		int posc = 0;
		if(pag != raiz){
			while(posc < pag.pai.chaves.size() && pag.pai.chaves.get(posc) > pag.chaves.get(termocentral)){
				posc++;
			}
		}

		//liga��es e desligamentos dos ponteiros
		if(pag != raiz){
			pag.pai.filhos.add(posf,x);
			pag.pai.filhos.add(posf+1,z);
			pag.filhos=null;
			pag.pai=null;
		}else{
			raiz = new Pagina(null);
			x.pai = raiz;
			z.pai = raiz;
			raiz.filhos.add(posf,x);
			raiz.filhos.add(posf+1,z);

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
	
	public void imprimir(Pagina pagina){
		for(int i=0; i<pagina.chaves.size(); i++){
			System.out.println(pagina.chaves.get(i));
		}
		
		for(int i=0; i<pagina.filhos.size(); i++){
			System.out.println();
			imprimir(pagina.filhos.get(i));
		}
	}
	
}
