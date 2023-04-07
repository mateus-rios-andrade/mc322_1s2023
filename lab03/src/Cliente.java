import java.util.List;

public class Cliente {
	protected String nome, endereco, educacao, genero, classeEconomica;
	protected List<Veiculo> veiculos;

	public Cliente(String nome, String endereco, String educacao, String genero, String classeEconomica,
			List<Veiculo> veiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.educacao = educacao;
		this.genero = genero;
		this.classeEconomica = classeEconomica;
		this.veiculos = veiculos;
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

	public String getEducacao() {
		return educacao;
	}

	public void setEducacao(String educacao) {
		this.educacao = educacao;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getClasseEconomica() {
		return classeEconomica;
	}

	public void setClasseEconomica(String classeEconomica) {
		this.classeEconomica = classeEconomica;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public void setVeiculos(List<Veiculo> veiculos) {
		this.veiculos = veiculos;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + ", endereco=" + endereco + ", educacao=" + educacao + ", genero=" + genero
				+ ", classeEconomica=" + classeEconomica + ", veiculos=" + veiculos + "]";
	}
}
