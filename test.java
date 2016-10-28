package br.com.ed.arvoreb;
public class test {

	public static void main(String[] args) {
		
		ArvoreB b = new ArvoreB(5);
	
		b.inserir(10);
		b.inserir(20);
		b.inserir(30);
		b.inserir(40);		
		b.inserir(50);
		b.inserir(15);
		b.inserir(60);
		b.inserir(70);
		b.inserir(80);
		b.inserir(90);
		b.inserir(100);
		b.inserir(110);
		
		b.imprimir();
		//System.out.println(b.buscar(10).chaves.get(0));
		
//		System.out.println(b.raiz.chaves);
//		System.out.println(b.raiz.getFilho(0).chaves);
//		System.out.println(b.raiz.getFilho(1).chaves);
//		System.out.println(b.raiz.getFilho(0).getFilho(0).chaves);
//		System.out.println(b.raiz.getFilho(0).getFilho(1).chaves);
//		System.out.println(b.raiz.getFilho(1).getFilho(0).chaves);
//		System.out.println(b.raiz.getFilho(1).getFilho(1).chaves);		
	}
}
