import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		var s = testeSeguradora();
		seguradoras.put(s.getNome(), s);
		interativo(seguradoras);
	}

	private static Seguradora testeSeguradora() {
		var seguradora = new Seguradora("Teste", "1132768497", "teste@teste", "Rua teste");

		var pessoa = new ClientePF("José", "Rua José", new ArrayList<>(), "Joaquim", "Jeremias", "João", "16557446746",
				new Date(0), new Date(1024 * 1024));
		pessoa.getVeiculos().add(new Veiculo("AAA2045", "Fiat", "Uno", 2014));

		var empresa = new ClientePJ("ABC SA", "Rua ABC", new ArrayList<>(), "47.960.950/0001-21", new Date(42962));
		empresa.getVeiculos().add(new Veiculo("AAA2046", "Fiat", "Uno", 2013));
		if (ClientePF.validarCPF(pessoa.getCpf())) {
			seguradora.cadastrarCliente(pessoa);
		} else {
			System.out.println("CPF: " + pessoa.getCpf() + " inválido.");
		}
		if (ClientePJ.validarCNPJ(empresa.getCnpj())) {
			seguradora.cadastrarCliente(empresa);
		} else {
			System.out.println("CNPJ: " + empresa.getCnpj() + " inválido.");
		}
		seguradora.gerarSinistro("acidente", pessoa.getVeiculos().get(0), pessoa);
		seguradora.gerarSinistro("acidente21", empresa.getVeiculos().get(0), empresa);
		for (var cliente : seguradora.listarClientes("PF")) {
			System.out.println(cliente.mkString("Cliente(\n\t", ",\n\t", "\n)"));
		}
		for (var cliente : seguradora.listarClientes("PJ")) {
			System.out.println(cliente.mkString("Cliente(\n\t", ",\n\t", "\n)"));
		}
		seguradora.visualizarSinistro("José");
		System.out.println(seguradora.listarSinistros());
		return seguradora;
	}

	private static void interativo(Map<String, Seguradora> seguradoras) {
		System.out.println("Bem vindo ao menu interativo.");
		loop(seguradoras);
		System.out.println("Tchau.");
	}

	private static void loop(Map<String, Seguradora> seguradoras) {
		Scanner sc = new Scanner(System.in);
		loop: while (true) {
			System.out.println("Comandos disponíveis:");
			System.out.println(" - [Criar] seguradora\n - [Entrar] em uma seguradora\n - [Sair]");
			switch (sc.nextLine().toLowerCase()) {
				case "criar":
					criar(sc, seguradoras);
					break;
				case "entrar":
					entrar(sc, seguradoras);
					break;
				case "sair":
					break loop;
				default:
			}
		}
		sc.close();
	}

	private static void criar(Scanner sc, Map<String, Seguradora> seguradoras) {
		System.out.print("Nome: ");
		String nome = sc.nextLine();
		System.out.print("\nTelefone: ");
		String telefone = sc.nextLine();
		System.out.print("\nEmail: ");
		String email = sc.nextLine();
		System.out.print("\nEndereço: ");
		String endereco = sc.nextLine();
		seguradoras.put(nome, new Seguradora(nome, telefone, email, endereco));
	}

	private static void entrar(Scanner sc, Map<String, Seguradora> seguradoras) {
		if (seguradoras.size() == 0) {
			System.out.println("Nenhuma seguradora foi criada.");
			return;
		}
		for (var s : seguradoras.keySet()) {
			System.out.println(" - " + s);
		}
		Seguradora seguradora;
		while (true) {
			var nome = sc.nextLine();
			seguradora = seguradoras.get(nome);
			if (seguradora != null)
				break;
			System.out.println("Seguradora inexistente. Tente novamente.");
		}
		loop: while (true) {
			System.out.println("Comandos disponíveis:");
			System.out.println(" - Visualizar [clientes]");
			System.out.println(" - [Cadastrar] cliente");
			System.out.println(" - [Remover] cliente");
			System.out.println(" - [Gerar] sinistro");
			System.out.println(" - Visualizar [sinistros]");
			System.out.println(" - [Voltar]");
			switch (sc.nextLine().toLowerCase()) {
				case "clientes":
					clientes(sc, seguradora);
					break;
				case "cadastrar":
					cadastrar(sc, seguradora);
					break;
				case "remover":
					remover(sc, seguradora);
					break;
				case "gerar":
					gerar(sc, seguradora);
					break;
				case "sinistros":
					sinistros(sc, seguradora);
				case "voltar":
					break loop;
				default:
					System.out.println("Comando inexistente.");
			}
		}
	}

	private static void clientes(Scanner sc, Seguradora seguradora) {
		System.out.println("Você deseja vizualizar...");
		System.out.println("...clientes [PF]?");
		System.out.println("...clientes [PJ]?");
		System.out.println("...[todos] os clientes?");
		boolean voltar;
		do {
			voltar = false;
			switch (sc.nextLine().toLowerCase()) {
				case "pf":
					listar(seguradora, "PF", 1);
					break;
				case "pj":
					listar(seguradora, "PJ", 1);
				case "todos":
					System.out.println("Clientes PF:");
					listar(seguradora, "PF", 1);
					System.out.println("Clientes PJ:");
					listar(seguradora, "PJ", 1);
					break;
				default:
					System.out.println("Comando inválido");
					voltar = true;
			}
		} while (voltar);
	}

	private static void cadastrar(Scanner sc, Seguradora seguradora) {
		System.out.println("Tipo ([PF] ou [PJ]): ");
		String tipo;
		while (true) {
			tipo = sc.nextLine().toUpperCase().trim();
			if (tipo.equals("PF") || tipo.equals("PJ"))
				break;
			System.out.println("Tipo inválido.");
		}
		String nome = getString(sc, "Nome: ");
		String endereco = getString(sc, "Endereço: ");
		List<Veiculo> veiculos = getVeiculos(sc);
		switch (tipo) {
			case "PF":
				String cpf;
				while (true) {
					cpf = getString(sc, "CPF: ");
					if (ClientePF.validarCPF(cpf))
						break;
					System.out.println("CPF inválido.");
				}
				seguradora.cadastrarCliente(new ClientePF(
						nome,
						endereco,
						veiculos,
						getString(sc, "Educação: "),
						getString(sc, "Gênero: "),
						getString(sc, "Classe econômica: "),
						cpf,
						getDate(sc, "Data de nascimento: "),
						getDate(sc, "Data de licença: ")));
				break;
			case "PJ":
				String cnpj;
				while (true) {
					cnpj = getString(sc, "CNPJ: ");
					if (ClientePJ.validarCNPJ(cnpj))
						break;
					System.out.println("CNPJ inválido.");
				}
				seguradora.cadastrarCliente(new ClientePJ(
						nome,
						endereco,
						veiculos,
						cnpj,
						getDate(sc, "Data de Fundação: ")));
				break;
		}
	}

	private static void remover(Scanner sc, Seguradora seguradora) {
		if (seguradora.nClientes() == 0) {
			System.out.println("Não há clientes registrados.");
			return;
		}
		mostrarClientes(seguradora);
		Cliente cliente = getCliente(sc, seguradora);
		seguradora.removerCliente(cliente);
	}

	private static void gerar(Scanner sc, Seguradora seguradora) {
		if (seguradora.nClientes() == 0) {
			System.out.println("Não existem clientes para o sinistro poder ser gerado.");
			return;
		}
		String endereco = getString(sc, "Local do ocorrido: ");
		mostrarClientes(seguradora);
		Cliente cliente = getCliente(sc, seguradora);
		if (cliente.getVeiculos().size() == 0) {
			System.out.println("Esse cliente não possui carros registrados.");
			return;
		}
		Veiculo veiculo = null;
		while (veiculo != null) {
			String placa = getString(sc, "Placa do veículo involvido: ");
			for (var v : cliente.getVeiculos()) {
				if (v.getPlaca().equals(placa)) {
					veiculo = v;
					break;
				}
			}
			System.out.println("Esse cliente não possui um veículo com essa placa.");
		}
		seguradora.gerarSinistro(endereco, veiculo, cliente);
	}

	private static void sinistros(Scanner sc, Seguradora seguradora) {
		if (seguradora.nClientes() == 0) {
			System.out.println("Não há clientes registrados.");
			return;
		}
		mostrarClientes(seguradora);
		var cliente = getCliente(sc, seguradora);

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

	private static Date getDate(Scanner sc, String texto) {
		System.out.print(texto);
		var df = new SimpleDateFormat("d/M/y");
		Date ret = null;
		boolean voltar;
		do {
			voltar = false;
			try {
				ret = df.parse(sc.nextLine());
			} catch (ParseException e) {
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