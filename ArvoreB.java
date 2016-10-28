/*
 * 			ÁRVORE B
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
			ramo ++;										//e a chave sendo procurada for menor que as chaves contidas na pagina, o contador de ramos é incrementado 
		}

		if(chave == (int)pag.chaves.get(ramo)){ // a chave está nessa pag
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

	private int buscarPos(Pagina pag, int chave){ // retorna a posição na pagina
		int poschave = 0;
		while(poschave < pag.chaves.size() && chave > (int)pag.chaves.get(poschave))
			poschave++;

		return poschave;
	}
	/*
	 * Único método de Inserção visível ao usuário,
	 * os outros são só auxilares
	 * -Recebe somente a chave
	 * -busca a página(folha) ideal para inserção e
	 * -a respectiva posição no vetor de chaves
	 */
	public void inserir(int chave){
		Pagina pag = buscarPagIdeal(raiz, chave);
		int poschave = buscarPos(pag, chave);
		inserir(chave, pag, poschave);
	}

	/*
	 * Método(Sobrecarregado) auxiliar de inserir
	 * faz a inserção no vetor de chaves da página ideal
	 * e testa se o mesmo está cheio 
	 * caso estiver cheio, chamará o método responsável pela
	 * reorganização da árvore, 'maquiavel()'
	 */
	private void inserir(int chave, Pagina pag, int poschave) {
		pag.chaves.add(poschave, chave);
		if(pag.chaves.size() == ordem){
			maquiavel(pag);
		}
	}

	/*
	 * Dividir para conquistar
	 * Faz a divisão da página que está cheia em duas('x' e 'z')
	 * e insere a chave que irá 'subir' para a página pai
	 */
	private void maquiavel(Pagina pag){//pag = raiz
		Pagina x = new Pagina(pag);
		Pagina z = new Pagina(pag);
		int termocentral=(ordem/2);

		/*
		 * 'x' e 'z' serão as duas páginas criadas
		 * de 0 até o termocentral   serão as chaves da página 'x'
		 *         do termocentral+1 serão as chaves da página 'z'
		 */
		for(int i=0; i<termocentral; i++){
			x.chaves.add(pag.chaves.get(i));
		}
		for(int i=termocentral+1; i<ordem; i++){
			z.chaves.add(pag.chaves.get(i));
		}

		/*
		 * posf   será a posição de x
		 * posf+1 será a posição de z
		 * no vetor de filhos do pai
		 */
		int posf = 0;

		//caso não seja folha, as páginas 'x' e 'z' terão filhos	
		if(!pag.folha){
			//x e z não serão folhas nesse caso
			x.folha = false;
			z.folha = false;
			//cópia dos filhos da página atual para 'x' e 'z'
			for(int i=0; i<=termocentral; i++){
				x.filhos.add(i, pag.filhos.get(i));
				x.filhos.get(i).pai = x;
			}
			for(int i=termocentral+1; i<=ordem; i++){
				z.filhos.add(i-(termocentral+1),pag.filhos.get(i));
				z.filhos.get(i-(termocentral+1)).pai = z;
			}
		}

		//posição ideal do termo que irá 'subir' na página pai
		//chaveUp será a chave que 'subirá'
		int chaveUp = pag.chaves.get(termocentral);
		//posição da chaveUp no vetor de chaves do pai, quando 'subir' 
		int posc = 0;

		//ligações e desligamentos dos ponteiros
		if(pag != raiz){
			while(pag.pai.filhos.get(posf) != pag){
				posf++;
			}
			x.pai = pag.pai;
			z.pai = pag.pai;
			//remoção da página que estamos dividindo 
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
		//chamada recursiva para inserção de chaveUp
		inserir(chaveUp,x.pai,posc);
	}	


	/*
	 * Método imprimir com pré-ordem
	 * Tivemos que utilizar um auxilar por ter que passar a raiz
	 */
	public void imprimir(){
		imprimir(raiz);
	}
	private void imprimir(Pagina pagina){
		//impressão das chaves da página atual
		for(int i=0; i<pagina.chaves.size(); i++){
			System.out.println(pagina.chaves.get(i));
		}

		//passagem da página filho
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