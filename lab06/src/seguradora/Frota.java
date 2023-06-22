package seguradora;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa uma frota de uma empresa.
 */
public class Frota implements MkString {
	private final String code;
	private String nome;
	private List<Veiculo> veiculos = new ArrayList<>();

	private static String genCode() {
		return UUID.randomUUID().toString();
	}

	public Frota(String nome, Iterable<Veiculo> veiculos) {
		this(genCode(), nome, veiculos);
	}

	public Frota(String code, String nome, Iterable<Veiculo> veiculos) {
		this.code = code;
		this.nome = nome;
		if (veiculos != null) {
			for (Veiculo veiculo : veiculos) {
				this.veiculos.add(veiculo);
			}
		}
	}

	public boolean addVeiculo(Veiculo veiculo) {
		return veiculos.add(veiculo);
	}

	public boolean removeVeiculo(Veiculo veiculo) {
		return veiculos.remove(veiculo);
	}

	public String getCode() {
		return code;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String toString() {
		return "Frota [code=" + code + ", Nº veículos=" + veiculos.size() + "]";
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return prefixo + "Código: " + code + sep + "Nome: " + nome + sep + "Nº veículos=" + veiculos.size() + sufixo;
	}

}
