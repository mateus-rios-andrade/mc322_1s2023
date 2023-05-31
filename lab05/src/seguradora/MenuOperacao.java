package seguradora;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import static seguradora.Utils.*;

/**
 * Agrupa as operações do submenu principal.
 */
public class MenuOperacao {
	/**
	 * Mantém o loop do menu principal.
	 */
	public static void menuPrincipal(Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		Scanner sc = new Scanner(System.in);
		boolean ficar = true;
		while (ficar) {
			System.out.println("1 - Cadastros");
			System.out.println("2 - Listar");
			System.out.println("3 - Excluir");
			System.out.println("4 - Gerar Seguro");
			System.out.println("5 - Gerar Sinistro");
			System.out.println("6 - Transferir Seguros");
			System.out.println("7 - Calcular Receita Seguradora");
			System.out.println("8 - Autorizar Condutor");
			System.out.println("9 - Desautorizar Condutor");
			System.out.println("0 - Sair");
			int op = getInt(sc, "");
			switch (op) {
				case 1 -> MenuCadastrar.cadastrar(sc, seguradoras, condutores);
				case 2 -> MenuListar.listar(sc, seguradoras, condutores);
				case 3 -> MenuExcluir.excluir(sc, seguradoras, condutores);
				case 4 -> gerarSeguro(sc, seguradoras, condutores);
				case 5 -> gerarSinistro(sc, seguradoras, condutores);
				case 6 -> transferirSeguros(sc, seguradoras);
				case 7 -> calcularReceita(sc, seguradoras);
				case 8 -> autorizarCondutor(sc, seguradoras, condutores);
				case 9 -> desautorizarCondutor(sc, seguradoras);
				case 0 -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
		sc.close();
	}

	private static void gerarSeguro(Scanner sc, Map<String, Seguradora> seguradoras,
			Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		List<ICondutor> conduts = List.copyOf(condutores.values());
		System.out.println("Escolha os condutores.");
		var idxs = getItens(sc, conduts, "condutor");
		List<ICondutor> condutoresSeguro = idxs.stream().map(conduts::get).toList();
		LocalDate dataInicio = getDate(sc, "Data início: "), dataFim = getDate(sc, "Data fim: ");
		if (cliente instanceof ClientePF c) {
			System.out.println("Escolha o veículo.");
			int i = getItem(sc, c.getVeiculos(), "veículo");
			if (i < 0) {
				System.out.println("Esse cliente não possui veículos para assegurar.");
				return;
			}
			seg.gerarSeguro(c, c.getVeiculos().get(i), condutoresSeguro, dataInicio, dataFim);
		} else if (cliente instanceof ClientePJ c) {
			System.out.println("Escolha a frota.");
			int i = getItem(sc, c.getFrotas(), "frota");
			if (i < 0) {
				System.out.println("Esse cliente não possui frotas para assegurar.");
				return;
			}
			seg.gerarSeguro(c, c.getFrotas().get(i), condutoresSeguro, dataInicio, dataFim);
		}
	}

	private static void gerarSinistro(Scanner sc, Map<String, Seguradora> seguradoras,
			Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		System.out.println("Escolha o seguro.");
		int idxSeguro = getItem(sc, seg.getSeguros(), "seguro");
		if (idxSeguro < 0) {
			System.out.println("Não existem seguros registrados nessa seguradora.");
			return;
		}
		Seguro seguro = seg.getSeguros().get(idxSeguro);
		System.out.println("Escolha o condutor.");
		int idxCondutor = getItem(sc, seguro.getCondutores(), "condutor");
		if (idxCondutor < 0) {
			System.out.println("Não existem condutores registrados nesse seguro.");
			return;
		}
		ICondutor condutor = seguro.getCondutores().get(idxCondutor);
		seg.gerarSinistro(seguro, condutor, getDate(sc, "Data da ocorrência: "), getString(sc, "Endereço: "));
	}

	private static void transferirSeguros(Scanner sc, Map<String, Seguradora> seguradoras) {
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
		System.out.printf("R$ %.2f\n", seg.calcularReceita());
	}

	private static void autorizarCondutor(Scanner sc, Map<String, Seguradora> seguradoras,
			Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		System.out.println("Escolha o seguro.");
		int idxSeguro = getItem(sc, seg.getSeguros(), "seguro");
		if (idxSeguro < 0) {
			System.out.println("Não existem seguros nessa seguradora.");
		}
		Seguro seguro = seg.getSeguros().get(idxSeguro);
		List<ICondutor> conduts = List.copyOf(condutores.values());
		System.out.println("Escolha o condutor.");
		int idxCondutor = getItem(sc, conduts, "condutor");
		if (idxCondutor < 0) {
			System.out.println("Não existem condutores registrados.");
			return;
		}
		ICondutor condutor = conduts.get(idxCondutor);
		seguro.autorizarCondutor(condutor);
	}

	private static void desautorizarCondutor(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		System.out.println("Escolha o seguro");
		int idxSeguro = getItem(sc, seg.getSeguros(), "seguro");
		if (idxSeguro < 0) {
			System.out.println("Não existem seguros nessa seguradora.");
		}
		Seguro seguro = seg.getSeguros().get(idxSeguro);
		System.out.println("Escolha o condutor.");

		int idxCondutor = getItem(sc, seguro.getCondutores(), "condutor");
		if (idxCondutor < 0) {
			System.out.println("Não existem condutores registrados.");
			return;
		}
		ICondutor condutor = seguro.getCondutores().get(idxCondutor);
		seguro.desautorizarCondutor(condutor);
	}
}
