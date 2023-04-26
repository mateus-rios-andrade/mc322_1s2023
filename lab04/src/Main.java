import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		interativo(seguradoras);
	}

	private static void interativo(Map<String, Seguradora> seguradoras) {
		System.out.println("Bem vindo ao menu interativo.");
		menuPrincipal(seguradoras);
		System.out.println("Tchau.");
	}

	private static void menuPrincipal(Map<String, Seguradora> seguradoras) {
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
				case CADASTRAR -> cadastrar(sc, seguradoras);
				case EXCLUIR -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case GERAR_SINISTRO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case LISTAR -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case SAIR -> ficar = false;
				case TRANSFERIR_SEGURO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
			}
		}
		sc.close();
	}

	private static void cadastrar(Scanner sc, Map<String, Seguradora> seguradoras) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("1.1 - Cadastrar Cliente PF/PJ");
			System.out.println("1.2 - Cadastrar Veiculo");
			System.out.println("1.3 - Cadastrar Seguradora");
			System.out.println("1.4 - Voltar");
			MenuCadastrar op = MenuCadastrar.getOpcao(getInt(sc, ""));
			switch (op) {
				case CLIENTE -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case SEGURADORA -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case VEICULO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case VOLTAR -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				default -> throw new IllegalArgumentException("Unexpected value: " + op);
			}
		}
	}

	private static List<Veiculo> getVeiculos(Scanner sc) {
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

	private static String getString(Scanner sc, String texto) {
		System.out.print(texto);
		String ret = sc.nextLine();
		return ret;
	}

	private static int getInt(Scanner sc, String texto) {
		System.out.print(texto);
		int ret = sc.nextInt();
		sc.nextLine();
		System.out.println();
		return ret;
	}

	private static LocalDate getDate(Scanner sc, String texto) {
		System.out.print(texto);
		LocalDate ret = null;
		boolean voltar;
		do {
			voltar = false;
			try {
				ret = LocalDate.parse(sc.nextLine());
			} catch (DateTimeParseException e) {
				voltar = true;
				System.out.println("Data inválida.");
			}
		} while (voltar);
		System.out.println();
		return ret;
	}

	private static Cliente getCliente(Scanner sc, Seguradora seguradora) {
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

	private static void mostrarClientes(Seguradora seguradora) {
		System.out.println("Clientes registrados:");
		for (var cliente : seguradora.getClientes()) {
			System.out.println(cliente.getID() + " - " + cliente.getNome());
		}
	}

	private static int listar(Seguradora seguradora, String tipo, int inicio) {
		int i = inicio;
		for (var cliente : seguradora.listarClientes(tipo)) {
			System.out.println(cliente.mkString("Cliente " + i++ + ":\n\t", "\n\t", "\n"));
		}
		return i;
	}
}