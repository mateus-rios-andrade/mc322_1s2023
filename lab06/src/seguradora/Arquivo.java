package seguradora;

import java.util.List;

public interface Arquivo<T> {
	boolean gerarArquivo();
	List<T> lerArquivo();

	String getNome();

	List<T> getObjetos();
}
