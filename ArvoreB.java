/*
 * 			�RVORE B
 * Henrique Luis de Sousa Santiago
 * Francisco Isaac de Alencar Menezes
 * Jorge Wilson Alves de Moura
 * 
 */
package br.com.ed.arvoreb;
public class ArvoreB {

	Pagina raiz;
	Integer ordem;

	ArvoreB(int ordem){
		this.ordem = ordem;
		this.raiz  = new Pagina(null);
	}

	private Pagina buscarPag(Pagina pag, int chave){    //retorna a pagina em que uma chave x deve se encontrar 
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

	private Pagina buscarPagIdeal(Pagina pag, int chave){ // passar a raiz e a chave que se deseja inserir 
		if(pag.folha){
			return pag;
		}
		int ramo = 	0;
		while(ramo < pag.chaves.size() && chave > pag.chaves.get(ramo)){ //ramo vai incrementando 
			ramo++;
		}
		return buscarPagIdeal(pag.getFilho(ramo), chave);
	}

	private int buscarPos(Pagina pag, int chave){ // retorna a posi��o na pagina
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
	 * Dividir para conquistar
	 * Faz a divis�o da p�gina que est� cheia em duas('x' e 'z')
	 * e insere a chave que ir� 'subir' para a p�gina pai
	 */
	private void maquiavel(Pagina pag){//pag = raiz
		Pagina x = new Pagina(pag);
		Pagina z = new Pagina(pag);
		int termocentral=(ordem/2);

		/*
		 * 'x' e 'z' ser�o as duas p�ginas criadas
		 * de 0 at� o termocentral   ser�o as chaves da p�gina 'x'
		 *         do termocentral+1 ser�o as chaves da p�gina 'z'
		 */
		for(int i=0; i<termocentral; i++){
			x.chaves.add(pag.chaves.get(i));
		}
		for(int i=termocentral+1; i<ordem; i++){
			z.chaves.add(pag.chaves.get(i));
		}

		/*
		 * posf   ser� a posi��o de x
		 * posf+1 ser� a posi��o de z
		 * no vetor de filhos do pai
		 */
		int posf = 0;

		//caso n�o seja folha, as p�ginas 'x' e 'z' ter�o filhos	
		if(!pag.folha){
			//x e z n�o ser�o folhas nesse caso
			x.folha = false;
			z.folha = false;
			//c�pia dos filhos da p�gina atual para 'x' e 'z'
			for(int i=0; i<=termocentral; i++){
				x.filhos.add(i, pag.filhos.get(i));
				x.filhos.get(i).pai = x;
			}
			for(int i=termocentral+1; i<=ordem; i++){
				z.filhos.add(i-(termocentral+1),pag.filhos.get(i));
				z.filhos.get(i-(termocentral+1)).pai = z;
			}
		}

		//posi��o ideal do termo que ir� 'subir' na p�gina pai
		//chaveUp ser� a chave que 'subir�'
		int chaveUp = pag.chaves.get(termocentral);
		//posi��o da chaveUp no vetor de chaves do pai, quando 'subir' 
		int posc = 0;

		//liga��es e desligamentos dos ponteiros
		if(pag != raiz){
			while(pag.pai.filhos.get(posf) != pag){
				posf++;
			}
			x.pai = pag.pai;
			z.pai = pag.pai;
			//remo��o da p�gina que estamos dividindo 
			x.pai.filhos.remove(posf);
			x.pai.filhos.add(posf,x);
			x.pai.filhos.add(posf+1,z);
			posc = buscarPos(x.pai, chaveUp);
		}else{
			raiz = new Pagina(null);
			x.pai = raiz;
			z.pai = raiz;
			raiz.filhos.add(posf,x);
			raiz.filhos.add(posf+1,z);
			raiz.folha = false;
		}

		pag = null;
		//chamada recursiva para inser��o de chaveUp
		inserir(chaveUp,x.pai,posc);
	}	


	/*
	 * M�todo imprimir com pr�-ordem
	 * Tivemos que utilizar um auxilar por ter que passar a raiz
	 */
	public void imprimir(){
		imprimir(raiz);
	}
	private void imprimir(Pagina pagina){
		//impress�o das chaves da p�gina atual
		for(int i=0; i<pagina.chaves.size(); i++){
			System.out.println(pagina.chaves.get(i));
		}

		//passagem da p�gina filho
		for(int i=0; i<pagina.filhos.size(); i++){
			System.out.println();
			imprimir(pagina.filhos.get(i));
		}
	}

	public Pagina buscar(int chave){
		return buscar(raiz,chave);
	}

	private Pagina buscar(Pagina pagina, int chave) {

		for(int i=0; i<pagina.chaves.size(); i++){
			if(chave == pagina.chaves.get(i)){
				return pagina;		
			}
		}
		for(int i=0; i<pagina.filhos.size(); i++){
			return buscar(pagina.filhos.get(i),chave);
		}
		return null;
	}



}