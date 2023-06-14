package seguradora;

/**
 * Interface que garante que se pode obter uma representação formatada
 * dos objetos implementadores.
 */
public interface MkString {
	public String mkString(String prefixo, String sep, String sufixo);
}