package seguradora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Predicate;

public class Utils {

	public static List<Veiculo> getVeiculos(Scanner sc) {
		int nVeiculos = getInt(sc, "Veículos a registrar");
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
		return switch (tipo) {
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
			ret = getInt(sc, "Índice do " + nome + ": ");
			if (ret < 0 || ret > i) {
				System.out.println("Índice inválido.");
			} else {
				break;
			}
		}
		return ret;
	}

	public static void printIter(Iterable<? extends MkString> iter, String nome) {
		int i = 0;
		for (MkString x : iter) {
			System.out.println(x.mkString(nome + i++ + ":\n\t ", ",\n\t", "\n"));
		}
	}
}