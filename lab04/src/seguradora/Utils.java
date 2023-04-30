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
		System.out.print("Quantos veículos o cliente possui? ");
		int nVeiculos = sc.nextInt();
		sc.nextLine();
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

	public static Cliente getCliente(Scanner sc, Seguradora seguradora) {
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

	public static int listar(Seguradora seguradora, String tipo, int inicio) {
		int i = inicio;
		for (var cliente : seguradora.listarClientes(tipo)) {
			System.out.println(cliente.mkString("Cliente " + i++ + ":\n\t", "\n\t", "\n"));
		}
		return i;
	}
}