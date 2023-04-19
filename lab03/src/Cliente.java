import java.util.List;

public class Cliente {
	protected String nome, endereco;
	protected List<Veiculo> veiculos;

	public Cliente(String nome, String endereco,
			List<Veiculo> veiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.veiculos = veiculos;
	}

	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "Nome: " + nome + sep + "Endereco: " + endereco + sep + "Veiculos: " + veiculos + sufixo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", endereco=" + endereco + ", educacao=" + ", veiculos=" + veiculos + "]";
	}
}
