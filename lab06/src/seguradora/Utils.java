package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

/**
 * Define várias funções úteis em geral, com ênfase em abstrair as operações
 * de input e output do menu.
 */
public class Utils {
	public static final DateTimeFormatter formatoPadrao = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public static <T> String iterToString(Iterable<T> iter) {
		StringBuilder sb = new StringBuilder();
		boolean primeiraVez = true;
		for (T a : iter) {
			if (primeiraVez) {
				primeiraVez = false;
			} else {
				sb.append(';');
			}
			sb.append(a.toString());			
		}
		return sb.toString();
	}

	public static List<Veiculo> getVeiculos(Scanner sc) {
		int nVeiculos = getInt(sc, "Qtd. de veículos a registrar: ");
		var veiculos = new ArrayList<Veiculo>();
		for (int i = 1; i <= nVeiculos; i++) {
			System.out.println("Veiculo " + i + " :");
			veiculos.add(new Veiculo(
					getString(sc, "Placa: "),
					getString(sc, "Marca: "),
					getString(sc, "Modelo: "),
					getInt(sc, "Ano de Fabricação: ")));
		}
		return veiculos;
	}

	public static String getString(Scanner sc, String texto) {
		System.out.print(texto);
		String ret = sc.nextLine();
		return ret;
	}

	public static int getInt(Scanner sc, String texto) {
		System.out.print(texto);
		int ret = sc.nextInt();
		sc.nextLine();
		return ret;
	}

	public static LocalDate getDate(Scanner sc, String texto) {
		System.out.print(texto);
		LocalDate ret = null;
		boolean voltar;
		do {
			voltar = false;
			try {
				ret = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("d/M/u"));
			} catch (DateTimeParseException e) {
				voltar = true;
				System.out.println("Data inválida.");
			}
		} while (voltar);
		return ret;
	}

	public static Seguradora getSeguradora(Scanner sc, Map<String, Seguradora> seguradoras) {
		if (seguradoras.size() == 0) {
			System.out.println("Não há seguradoras registradas.");
			return null;
		}
		Seguradora seguradora;
		while (true) {
			String nomeSeg = getString(sc, "Seguradora: ");
			seguradora = seguradoras.get(nomeSeg);
			if (seguradora == null) {
				System.out.println("Não existe seguradora com esse nome.");
			} else {
				break;
			}
		}
		return seguradora;
	}

	public static String getID(Scanner sc, String texto, Predicate<String> f) {
		String id;
		while (true) {
			id = getString(sc, texto);
			if (!f.test(id)) {
				System.out.println("Identificação inválida.");
			} else {
				break;
			}
		}
		return id;
	}

	public static Cliente criarCliente(Scanner sc) {
		String nome = getID(sc, "Nome: ", Validacao::validarNome);
		String telefone = getString(sc, "Telefone: ");
		String endereco = getString(sc, "Endereço: ");
		String email = getString(sc, "Email: ");
		String tipo;
		while (true) {
			tipo = getString(sc, "Tipo: ").toUpperCase();
			if (!tipo.equals("PF") && !tipo.equals("PJ")) {
				System.out.println("Tipo inválido.");
			} else {
				break;
			}
		}
		return switch (tipo) {
			case "PF" ->
				new ClientePF(
						nome,
						telefone,
						endereco,
						email,
						new ArrayList<>(),
						getString(sc, "Edudação: "),
						getString(sc, "Gênero: "),
						getString(sc, "Classe econômica: "),
						getID(sc, "CPF: ", Validacao::validarCPF),
						getDate(sc, "Data de Nascimento: "),
						getDate(sc, "Data de Licença: "),
						Collections.emptyList());
			case "PJ" ->
				new ClientePJ(
						nome,
						telefone,
						endereco,
						email,
						new ArrayList<>(),
						getID(sc, "CNPJ: ", Validacao::validarCNPJ),
						getDate(sc, "Data de Fundação: "),
						getInt(sc, "Quantidade de funcionários: "));
			default -> null;
		};
	}

	public static Frota criarFrota(Scanner sc) {
		return new Frota(getString(sc, "Nome: "), getVeiculos(sc));
	}

	public static Cliente getCliente(Scanner sc, Seguradora seguradora) {
		if (seguradora.getClientes().isEmpty()) {
			System.out.println("Não há clientes registrados nessa seguradora.");
			return null;
		}
		while (true) {
			String id = getString(sc, "CPF/CNPJ do cliente: ");
			for (var c : seguradora.getClientes()) {
				if (c.getID().replaceAll("[^0-9]", "").equals(id.replaceAll("[^0-9]", ""))) {
					return c;
				}
			}
			System.out.println("Não existe cliente com essa identificação.");
		}
	}

	public static ICondutor getCondutor(Scanner sc, Map<String, ICondutor> condutores) {
		String cpf = getID(sc, "CPF: ", Validacao::validarCPF);
		return condutores.get(cpf);
	}

	public static void mostrarClientes(Seguradora seguradora) {
		System.out.println("Clientes registrados:");
		for (var cliente : seguradora.getClientes()) {
			System.out.println(cliente.getID() + " - " + cliente.getNome());
		}
	}

	public static int getItem(Scanner sc, Iterable<? extends MkString> iter, String nome) {
		if (!iter.iterator().hasNext()) {
			return -1;
		}
		int i = 0;
		for (MkString x : iter) {
			System.out.println(i++ + " - " + x.mkString("", ", ", ""));
		}
		int ret;
		while (true) {
			ret = getInt(sc, "Índice: ");
			if (ret < 0 || ret > i) {
				System.out.println("Índice inválido.");
			} else {
				break;
			}
		}
		return ret;
	}

	public static List<Integer> getItens(Scanner sc, Iterable<? extends MkString> iter, String nome) {
		if (!iter.iterator().hasNext()) {
			return Collections.emptyList();
		}
		int i = 0;
		for (MkString x : iter) {
			System.out.println(i++ + " - " + x.mkString("", ", ", ""));
		}
		int n = getInt(sc, "Nº de itens: ");
		n = n > i ? i : n;
		var ret = new ArrayList<Integer>();
		for (i = 0; i < n; i++) {
			int j;
			while (true) {
				j = getInt(sc, (i + 1) + "º índice: ");
				if (j < 0 || j > n) {
					System.out.println("Índice inválido.");
				} else {
					break;
				}
			}
			ret.add(j);
		}
		return ret;
	}

	public static void printIter(Iterable<? extends MkString> iter, String nome) {
		int i = 0;
		for (MkString x : iter) {
			System.out.println(x.mkString(nome + " " + i++ + ":\n\t ", ",\n\t", "\n"));
		}
	}
}