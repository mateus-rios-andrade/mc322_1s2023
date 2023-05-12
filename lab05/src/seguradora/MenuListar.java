package seguradora;

import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public enum MenuListar {
	CLIENTES_SEG(1),
	SINISTROS_SEG(2),
	SINISTROS_CLIENTE(3),
	VEICULOS_CLIENTE(4),
	VEICULOS_SEG(5),
	VOLTAR(6),
	INVALIDO(-1);

	public static void listar(Scanner sc, Map<String, Seguradora> seguradoras) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("2.1 - Listar Cliente (PF/PJ) por Seg.");
			System.out.println("2.2 - Listar Sinistros por Seguradora");
			System.out.println("2.3 - Listar Sinistros por Cliente");
			System.out.println("2.4 - Listar Veiculos por Cliente");
			System.out.println("2.5 - Listar Veiculos por Seguradora");
			System.out.println("2.6 - Voltar");
			MenuListar op = getOpcao(getInt(sc, ""));
			switch (op) {
				case CLIENTES_SEG -> clientesSeg(sc, seguradoras);
				case SINISTROS_SEG -> sinistrosSeg(sc, seguradoras);
				case SINISTROS_CLIENTE -> sinistrosCliente(sc, seguradoras);
				case VEICULOS_CLIENTE -> veiculosCliente(sc, seguradoras);
				case VEICULOS_SEG -> veiculosSeg(sc, seguradoras);
				case VOLTAR -> ficar = false;
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
		printIter(seg.getSinistros(), "Sinistro");
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
		if (cliente == null) {
			return;
		}
		printIter(cliente.getVeiculos(), "Veículo");
	}

	private static void veiculosSeg(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Iterable<Veiculo> x = seg.getClientes().stream()
			.flatMap(cliente -> cliente.getVeiculos().stream())::iterator;
		printIter(x, "Veículo");
	}

	public static MenuListar getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTES_SEG;
			case 2 -> SINISTROS_SEG;
			case 3 -> SINISTROS_CLIENTE;
			case 4 -> VEICULOS_CLIENTE;
			case 5 -> VEICULOS_SEG;
			case 6 -> VOLTAR;
			default -> INVALIDO;
		};
	}

	final int index;

	MenuListar(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}