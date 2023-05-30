package seguradora;

import java.util.List;

public sealed abstract class Cliente implements MkString permits ClientePF, ClientePJ {
	enum Tipo {
		PF, PJ;
	}

	protected String nome, telefone, endereco, email;

	public Cliente(String nome, String telefone, String endereco, String email) {
		this.nome = nome;
		this.telefone = telefone;
		this.endereco = endereco;
		this.email = email;
	}

	public abstract Tipo getTipo();

	public abstract String getID();

	protected static String formatarVeiculos(List<Veiculo> veiculos, String sep) {
		String str = "[";
		boolean primeiro = true;
		for (Veiculo veiculo : veiculos) {
			if (!primeiro) {
				str += sep;
			}
			str += veiculo;
			primeiro = false;
		}
		return str + "]";
	}

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
