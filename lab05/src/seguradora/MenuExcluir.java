package seguradora;

import java.util.List;
import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public class MenuExcluir {
	public static void excluir(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("3.1 - Excluir Cliente");
			System.out.println("3.2 - Excluir Veiculo");
			System.out.println("3.3 - Excluir Sinistro");
			System.out.println("3.4 - Excluir Frota");
			System.out.println("3.5 - Excluir Seguro");
			System.out.println("3.6 - Excluir Condutor");
			System.out.println("3.7 - Voltar");
			int op = getInt(sc, "");
			switch (op) {
				case 1 -> cliente(sc, seguradoras);
				case 2 -> veiculo(sc, seguradoras);
				case 3 -> sinistro(sc, seguradoras, condutores);
				case 4 -> frota(sc, seguradoras);
				case 5 -> seguro(sc, seguradoras);
				case 6 -> condutor(sc, seguradoras, condutores);
				case 7 -> ficar = false;
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
		seg.removerCliente(cliente);
	}

	private static void veiculo(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		final Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		List<Veiculo> veiculos;
		if (cliente instanceof ClientePF c){
			veiculos = c.getVeiculos();
		} else if (cliente instanceof ClientePJ c) {
			System.out.println("Escolha a frota.");
			int i = getItem(sc, c.getFrotas(), "frota");
			if (i < 0) {
				System.out.println("Esse cliente não possui frotas registradas.");
			}
			veiculos = c.getFrotas().get(i).getVeiculos();
		} else {
			// Inalcansável
			return;
		}
		System.out.println("Escolha o veículo.");
		int i = getItem(sc, veiculos, "Veículo");
		if (i < 0) {
			System.out.println("Esse cliente/frota não possui veículos registrados.");
			return;
		}
		Veiculo veiculo = veiculos.get(i);
		veiculos.remove(i);
		seg.getSeguros().removeIf(s -> s instanceof SeguroPF pf && pf.getVeiculo().equals(veiculo));
	}

	private static void sinistro(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		List<Sinistro> sinistros = seg.getSinistrosPorCliente(cliente);
		System.out.println("Escolha o sinistro.");
		int i = getItem(sc, sinistros, "Sinistro");
		if (i < 0) {
			System.out.println("Esse cliente não possui sinistros.");
			return;
		}
		Sinistro sinistro = sinistros.get(i);
		for (Seguro seguro : seg.getSegurosPorCliente(cliente)) {
			seguro.getSinistros().removeIf(s -> s.equals(sinistro));
		}
		for (ICondutor condutor : condutores.values()) {
			condutor.getSinistros().removeIf(s -> s.equals(sinistro));
		}
	}

	private static void frota(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		} else if (cliente instanceof ClientePJ c) {
			List<Frota> frotas = c.getFrotas();
			System.out.println("Escolha a frota.");
			int i = getItem(sc, frotas, "frota");
			Frota frota = frotas.get(i);
			frotas.remove(i);
			seg.getSeguros().removeIf(s -> s instanceof SeguroPJ pj && pj.getFrota().equals(frota));
		} else {
			System.out.println("Clientes pf não contém frotas para excluir.");
		}
	}

	private static void seguro(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		System.out.println("Escolha o seguro.");
		int i = getItem(sc, seg.getSeguros(), "seguro");
		if (i < 0) {
			System.out.println("Essa seguradora não possui seguros.");
			return;
		}
		seg.getSeguros().remove(i);
	}

	private static void condutor(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		List<ICondutor> conduts = List.copyOf(condutores.values());
		System.out.println("Escolha o condutor.");
		int i = getItem(sc, conduts, "condutor");
		if (i < 0) {
			System.out.println("Não há condutores registrados.");
			return;
		}
		ICondutor condutor = conduts.get(i);
		condutores.remove(condutor.getCpf());
		for (Seguradora seg : seguradoras.values()) {
			for (Seguro seguro : seg.getSeguros()) {
				seguro.desautorizarCondutor(condutor);
			}
		}
	}
}