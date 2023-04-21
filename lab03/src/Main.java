import java.time.Instant;
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
		if (pessoa.validarCPF(pessoa.getCpf())) {
			seguradora.cadastrarCliente(pessoa);
		} else {
			System.out.println("CPF: " + pessoa.getCpf() + " inválido.");
		}
		if (empresa.validarCNPJ(empresa.getCnpj())) {
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
			switch (sc.next().toLowerCase()) {
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
		String nome = sc.next();
		System.out.print("\nTelefone: ");
		String telefone = sc.next();
		System.out.print("\nEmail: ");
		String email = sc.next();
		System.out.print("\nEndereço: ");
		String endereco = sc.next();
		seguradoras.put(nome, new Seguradora(nome, telefone, email, endereco));
	}

	private static void entrar(Scanner sc, Map<String, Seguradora> seguradoras) {
		for (var s : seguradoras.keySet()) {
			System.out.println(" - " + s);
		}
		var nome = sc.next();
		Seguradora seguradora = seguradoras.get(nome);
		System.out.println("Comandos disponíveis:");
		System.out.println(" - Visualizar [clientes]");
		System.out.println(" - [Cadastrar] cliente");
		System.out.println(" - [Gerar] sinistro");
		System.out.println(" - Visualizar [sinistros]");
		switch (sc.next().toLowerCase()) {
			case "clientes":
				clientes(sc, seguradora);
				break;
			default:
				System.out.println("Comando inexistente.");
		}
	}

	private static void clientes(Scanner sc, Seguradora seguradora) {
		System.out.println("Você deseja vizualizar...");
		System.out.println("...clientes [PF]?");
		System.out.println("...clientes [PJ]?");
		System.out.println("...[todos] os clientes?");
		switch (sc.next().toLowerCase()) {
			case "pf":
				listar(seguradora, "PF");
				break;
			case "pj":
				listar(seguradora, "PJ");
			case "todos":
				System.out.println("Clientes PF:");
				listar(seguradora, "PF");
				System.out.println("Clientes PJ:");
				listar(seguradora, "PJ");
			default:
				break;
		}
	}

	private static void listar(Seguradora seguradora, String tipo) {
		int i = 1;
		for (var cliente : seguradora.listarClientes(tipo)) {
			System.out.println(cliente.mkString("Cliente " + i++ + ":\n\t", "\n\t", "\n\n"));
		}
	}
}