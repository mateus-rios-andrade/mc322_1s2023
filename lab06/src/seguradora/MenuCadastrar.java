package seguradora;

import static seguradora.Utils.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe que agrupa os métodos referentes ao submenu cadastrar.
 */
public class MenuCadastrar {
	/**
	 * Mantém o loop do submenu e as rotas de navegação.
	 */
	public static void cadastrar(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("1.1 - Cadastrar Cliente PF/PJ");
			System.out.println("1.2 - Cadastrar Veiculo");
			System.out.println("1.3 - Cadastrar Frota");
			System.out.println("1.4 - Cadastrar Seguradora");
			System.out.println("1.5 - Cadastrar Condutor");
			System.out.println("1.6 - Voltar");
			int op = getInt(sc, "");
			switch (op) {
				case 1 -> cliente(sc, seguradoras, condutores);
				case 2 -> veiculo(sc, seguradoras);
				case 3 -> frota(sc, seguradoras);
				case 4 -> seguradora(sc, seguradoras);
				case 5 -> condutor(sc, condutores);
				case 6 -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
	}

	private static void condutor(Scanner sc, Map<String, ICondutor> condutores) {
		String cpf = getID(sc, "CPF: ", Validacao::validarCPF);
		if (condutores.containsKey(cpf)) {
			System.out.println("Já foi registrado um condutor com esse cpf.");
			return;
		}
		var condutor = new Condutor(
				cpf,
				getID(sc, "Nome: ", Validacao::validarNome),
				getString(sc, "Telefone: "),
				getString(sc, "Endereço: "),
				getString(sc, "Email: "),
				getDate(sc, "Data de Nascimento: "),
				Collections.emptyList());
		condutores.put(cpf, condutor);
	}

	private static void cliente(Scanner sc, Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = criarCliente(sc);
		seg.cadastrarCliente(cliente);
		if (cliente instanceof ClientePF c) {
			condutores.putIfAbsent(c.getCpf(), c);
		}
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
		List<Veiculo> veiculos = getVeiculos(sc);
		if (cliente instanceof ClientePF c) {
			c.getVeiculos().addAll(veiculos);
		} else if (cliente instanceof ClientePJ c) {
			System.out.println("Escolha a frota.");
			int i = getItem(sc, c.getFrotas(), "frota");
			if (i < 0) {
				System.out.println("Esse cliente não possui frotas registradas.");
			}
			Frota frota = c.getFrotas().get(i);
			veiculos.forEach(frota::addVeiculo);
		}
	}

	private static void frota(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		} else if (cliente instanceof ClientePJ c) {
			c.cadastrarFrota(criarFrota(sc));
		} else {
			System.out.println("Frotas só podem ser adicionadas à clientes pj");
		}
	}

	private static void seguradora(Scanner sc, Map<String, Seguradora> seguradoras) {
		String nome = getString(sc, "Nome: ");
		seguradoras.put(nome, new Seguradora(
				nome,
				getString(sc, "Telefone: "),
				getString(sc, "Email: "),
				getString(sc, "Endereço: ")));
	}
}
