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

		var pessoa = new ClientePF("José", "Rua José", new ArrayList<>(), "Joaquim", "Jeremias", "João", "16557446746", new Date(0), new Date(1024*1024));
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
		var sc = new Scanner(System.in);
		loop: while (true) {
			System.out.println("Comandos disponíveis:");
			System.out.println(" - [Criar] seguradora\n - [Entrar] em uma seguradora\n - [Sair]");
			switch (sc.next().toLowerCase()) {
				case "criar":
				
				case "sair":
					break loop;
				default:
					System.out.println("Comando inexistente.");
			}
		}
		sc.close();
	}
}
