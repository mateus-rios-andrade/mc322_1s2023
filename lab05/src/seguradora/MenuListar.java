package seguradora;

import java.util.Map;
import java.util.Scanner;

import seguradora.Cliente.Tipo;

import static seguradora.Utils.*;

public class MenuListar {
	public static void listar(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("2.1 - Listar Cliente (PF/PJ) por Seg.");
			System.out.println("2.2 - Listar Sinistros por Seguradora");
			System.out.println("2.3 - Listar Sinistros por Cliente");
			System.out.println("2.4 - Listar Veiculos por Cliente");
			System.out.println("2.5 - Listar Veiculos por Seguradora");
			System.out.println("2.6 - Listar Seguros por Seguradora");
			System.out.println("2.7 - Listar Seguros por Cliente");
			System.out.println("2.8 - Listar Veiculos por Frota");
			System.out.println("2.9 - Listar Frotas por Seguradora");
			System.out.println("2.10 - Listar Frotas por Cliente");
			System.out.println("2.11 - Listar Condutores");
			System.out.println("2.12 - Listar Condutores por Seguro");
			System.out.println("2.13 - Voltar");
			int op = getInt(sc, "");
			switch (op) {
				case 1 -> clientesSeg(sc, seguradoras);
				case 2 -> sinistrosSeg(sc, seguradoras);
				case 3 -> sinistrosCliente(sc, seguradoras);
				case 4 -> veiculosCliente(sc, seguradoras);
				case 5 -> veiculosSeg(sc, seguradoras);
				case 6 -> segurosSeg(sc, seguradoras);
				case 7 -> segurosCliente(sc, seguradoras);
				case 8 -> veiculosFrota(sc, seguradoras);
				case 9 -> frotasSeg(sc, seguradoras);
				case 10 -> frotasCliente(sc, seguradoras);
				case 11 -> condutores(sc, condutores);
				case 12 -> condutoresSeguro(sc, seguradoras, condutores);
				case 13 -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
	}

	private static void clientesSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		printIter(seg.getClientes(), "Cliente");
	}

	private static void sinistrosSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Iterable<Sinistro> sinistros = seg.getSeguros().stream().flatMap(s -> s.getSinistros().stream())::iterator;
		printIter(sinistros, "Sinistro");
	}

	private static void sinistrosCliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		seg.visualizarSinistro(cliente);
	}

	private static void veiculosCliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		Iterable<Veiculo> veiculos;
		if (cliente == null) {
			return;
		} else if (cliente instanceof ClientePF c) {
			veiculos = c.getVeiculos();
		} else if (cliente instanceof ClientePJ c) {
			veiculos = c.getFrotas().stream().flatMap(f -> f.getVeiculos().stream())::iterator;
		} else {
			// inalcansável
			return;
		}
		printIter(veiculos, "Veículo");
	}

	private static void veiculosSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Iterable<Veiculo> x = seg.getClientes().stream().flatMap(
				c -> c instanceof ClientePF pf ? pf.getVeiculos().stream()
						: ((ClientePJ) c).getFrotas().stream().flatMap(f -> f.getVeiculos().stream()))::iterator;
		printIter(x, "Veículo");
	}

	private static void segurosSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		printIter(seg.getSeguros(), "Seguro");
	}

	private static void segurosCliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		printIter(seg.getSegurosPorCliente(cliente), "Seguro");
	}

	private static void veiculosFrota(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		} else if (cliente instanceof ClientePJ c) {
			System.out.println("Escolha a frota.");
			int i = getItem(sc, c.getFrotas(), "frota");
			if (i < 0) {
				System.out.println("Esse cliente não possui frotas.");
				return;
			}
			Frota frota = c.getFrotas().get(i);
			printIter(frota.getVeiculos(), "Veículo");
		} else {
			System.out.println("Somente clientes pj possuem frotas.");
		}
	}

	private static void frotasSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Iterable<Frota> frotas = seg.listarClientes(Tipo.PJ).stream()
				.flatMap(c -> ((ClientePJ) c).getFrotas().stream())::iterator;
		printIter(frotas, "Frota");
	}

	private static void frotasCliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		} else if (cliente instanceof ClientePJ c) {
			printIter(c.getFrotas(), "Frota");
		} else {
			System.out.println("Somente clientes pj possuem frotas.");
		}
	}

	private static void condutores(Scanner sc, Map<String, ICondutor> condutores) {
		if (condutores.values().isEmpty()) {
			System.out.println("Não há condutores registrados.");
		} else {
			printIter(condutores.values(), "Condutor");
		}
	}

	private static void condutoresSeguro(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		System.out.println("Escolha o seguro.");
		int i = getItem(sc, seg.getSeguros(), "seguro");
		if (i < 0) {
			System.out.println("Não há seguros registrados.");
			return;
		}
		Seguro seguro = seg.getSeguros().get(i);
		printIter(seguro.getCondutores(), "Condutor");
	}
}