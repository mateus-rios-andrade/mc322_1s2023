package seguradora;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Frota {
	private final String code;
	private List<Veiculo> veiculos = new ArrayList<>();

	private static String genCode() {
		return UUID.randomUUID().toString();
	}

	public Frota(Iterable<Veiculo> veiculos) {
		code = genCode();
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

	@Override
	public String toString() {
		return "Frota [code=" + code + ", Nº veículos=" + veiculos.size() + "]";
	}

}
