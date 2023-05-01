package seguradora;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public enum MenuExcluir {
	CLIENTE(1),
	VEICULO(2),
	SINISTRO(3),
	VOLTAR(4),
	INVALIDO(-1);

	public static void excluir(Scanner sc, Map<String, Seguradora> seguradoras) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("3.1 - Excluir Cliente");
			System.out.println("3.2 - Excluir Veiculo");
			System.out.println("3.3 - Excluir Sinistro");
			System.out.println("3.4 - Voltar");
			MenuExcluir op = getOpcao(getInt(sc, ""));
			switch (op) {
				case CLIENTE -> cliente(sc, seguradoras);
				case VEICULO -> veiculo(sc, seguradoras);
				case SINISTRO -> sinistro(sc, seguradoras);
				case VOLTAR -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
	}

	private static void cliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		seg.getClientes().remove(cliente);
	}

	private static void veiculo(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		List<Veiculo> veiculos = cliente.getVeiculos();
		int i = getItem(sc, veiculos, "Veículo");
		if (i < 0) {
			System.out.println("Esse cliente não possui veículos.");
			return;
		}
		veiculos.remove(i);
	}

	private static void sinistro(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		List<Sinistro> sinistros = seg.getSinistros().stream().filter(s -> s.getCliente().equals(cliente)).toList();
		int i = getItem(sc, sinistros, "Sinistro");
		if (i < 0) {
			System.out.println("Esse cliente não possui sinistros.");
			return;
		}
		seg.getSinistros().remove(sinistros.get(i));
	}

	public static MenuExcluir getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTE;
			case 2 -> VEICULO;
			case 3 -> SINISTRO;
			case 4 -> VOLTAR;
			default -> INVALIDO;
		};
	}

	final int index;

	MenuExcluir(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}