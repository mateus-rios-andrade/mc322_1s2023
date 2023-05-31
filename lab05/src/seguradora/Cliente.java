package seguradora;

/**
 * Define a base de um cliente no sistema.
 */
public sealed abstract class Cliente implements MkString permits ClientePF, ClientePJ {
	/*
	 * Representa o tipo de um cliente.
	 */
	enum Tipo {
		PF, PJ;
	}

	private String nome, telefone, endereco, email;

	public Cliente(String nome, String telefone, String endereco, String email) {
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
	}

	public abstract Tipo getTipo();

	/**
	 * Retorna a identifcação única de cada cliente no formato de string.
	 */
	public abstract String getID();

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "Nome: " + nome + sufixo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", endereco=" + endereco + ", educacao=" + "]";
	}
}
