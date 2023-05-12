package seguradora;

import java.util.ArrayList;
import java.util.List;

public class Frota {
	private String code;
	private List<Veiculo> veiculos = new ArrayList<>();

	public Frota(String code, Iterable<Veiculo> veiculos) {
		this.code = code;
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

	public void setCode(String code) {
		this.code = code;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
	}

}
