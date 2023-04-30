package seguradora;

import static seguradora.Utils.*;

import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public enum MenuCadastrar {
	CLIENTE(1),
	VEICULO(2),
	SEGURADORA(3),
	VOLTAR(4),
	INVALIDO(-1);

	public static void cadastrar(Scanner sc, Map<String, Seguradora> seguradoras) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("1.1 - Cadastrar Cliente PF/PJ");
			System.out.println("1.2 - Cadastrar Veiculo");
			System.out.println("1.3 - Cadastrar Seguradora");
			System.out.println("1.4 - Voltar");
			MenuCadastrar op = getOpcao(getInt(sc, ""));
			switch (op) {
				case CLIENTE -> cliente(sc, seguradoras);
				case VEICULO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case SEGURADORA -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case VOLTAR -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
	}

	private static void cliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		if (seguradoras.size() == 0) {
			System.out.println("Não há seguradoras registradas.");
			return;
		}
		Seguradora seg = getSeguradora(sc, seguradoras);
		String nome = getString(sc, "Nome: ");
		String endereco = getString(sc, "Endereço: ");
		String tipo;
		while (true) {
			tipo = getString(sc, "Tipo: ").toUpperCase();
			if (!tipo.equals("PF") && !tipo.equals("PJ")) {
				System.out.println("Tipo inválido.");
			} else {
				break;
			}
		}
		Cliente cliente = switch (tipo) {
			case "PF" ->
				new ClientePF(
						nome,
						endereco,
						new ArrayList<>(),
						getString(sc, "Edudação: "),
						getString(sc, "Gênero: "),
						getString(sc, "Classe econômica: "),
						getID(sc, "CPF: ", Validacao::validarCPF),
						getDate(sc, "Data de Nascimento: "),
						getDate(sc, "Data de Licença: "));
			case "PJ" ->
				new ClientePJ(
						nome,
						endereco,
						new ArrayList<>(),
						getID(sc, "CNPJ: ", Validacao::validarCNPJ),
						getDate(sc, "Data de Fundação: "),
						getInt(sc, "Quantidade de funcionários: "));
			default -> null;
		};
		seg.cadastrarCliente(cliente);
	}

	public static MenuCadastrar getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTE;
			case 2 -> VEICULO;
			case 3 -> SEGURADORA;
			case 4 -> VOLTAR;
			default -> INVALIDO;
		};
	}

	private final int index;

	MenuCadastrar(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}