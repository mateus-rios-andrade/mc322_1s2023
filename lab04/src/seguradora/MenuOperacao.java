package seguradora;

import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public enum MenuOperacao {
	SAIR(0),
	CADASTRAR(1),
	LISTAR(2),
	EXCLUIR(3),
	GERAR_SINISTRO(4),
	TRANSFERIR_SEGURO(5),
	CALCULAR_RECEITA(6),
	INVALIDO(-1);

	public static void menuPrincipal(Map<String, Seguradora> seguradoras) {
		Scanner sc = new Scanner(System.in);
		boolean ficar = true;
		while (ficar) {
			System.out.println("1 - Cadastros");
			System.out.println("2 - Listar");
			System.out.println("3 - Excluir");
			System.out.println("4 - Gerar Sinistro");
			System.out.println("5 - Transferir Seguro");
			System.out.println("6 - Calcular Receita Seguradora");
			System.out.println("0 - Sair");
			MenuOperacao op = MenuOperacao.getOpcao(getInt(sc, ""));
			switch (op) {
				case CADASTRAR -> MenuCadastrar.cadastrar(sc, seguradoras);
				case LISTAR -> MenuListar.listar(sc, seguradoras);
				case EXCLUIR -> MenuExcluir.excluir(sc, seguradoras);
				case GERAR_SINISTRO -> gerarSinistro(sc, seguradoras);
				case TRANSFERIR_SEGURO -> transferirSeguro(sc, seguradoras);
				case CALCULAR_RECEITA -> calcularReceita(sc, seguradoras);
				case SAIR -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
		sc.close();
	}

	private static void gerarSinistro(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		int i = getItem(sc, cliente.getVeiculos(), "Veículo");
		if (i < 0) {
			System.out.println("Esse cliente não possui veículos.");
			return;
		}
		seg.gerarSinistro(
				getDate(sc, "Data do ocorrido: "),
				getString(sc, "Endereço"),
				cliente.getVeiculos().get(i),
				cliente);
	}

	private static void transferirSeguro(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente clienteOriginal = getCliente(sc, seg);
		if (clienteOriginal == null) {
			return;
		}
		Cliente novoCliente = criarCliente(sc);
		if (clienteOriginal instanceof ClientePF && novoCliente instanceof ClientePJ
				|| clienteOriginal instanceof ClientePJ && novoCliente instanceof ClientePF) {
			System.out.println("Só é possível transferir seguros entre clientes do mesmo tipo.");
		} else {
			seg.trocarCliente(clienteOriginal, novoCliente);
		}

	}

	private static void calcularReceita(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		System.out.printf("%.2f\n", seg.calcularReceita());
	}

	public static MenuOperacao getOpcao(int index) {
		return switch (index) {
			case 0 -> SAIR;
			case 1 -> CADASTRAR;
			case 2 -> LISTAR;
			case 3 -> EXCLUIR;
			case 4 -> GERAR_SINISTRO;
			case 5 -> TRANSFERIR_SEGURO;
			case 6 -> CALCULAR_RECEITA;
			default -> INVALIDO;
		};
	}

	private final int index;

	MenuOperacao(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

}