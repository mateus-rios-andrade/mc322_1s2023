package seguradora;
import java.util.List;

public sealed abstract class Cliente permits ClientePF, ClientePJ {
	protected String nome, endereco;
	protected List<Veiculo> veiculos;

	public Cliente(String nome, String endereco,
			List<Veiculo> veiculos) {
		this.nome = nome;
		this.endereco = endereco;
		this.veiculos = veiculos;
	}

	public abstract String getID();

	public abstract double calcularScore();

	private static String formatarVeiculos(List<Veiculo> veiculos, String sep) {
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

	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "Nome: " + nome + sep + "Endereco: " + endereco + sep + "Veiculos: " + formatarVeiculos(veiculos, sep) + sufixo;
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
