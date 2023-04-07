import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		var seguradora = new Seguradora("Ajax", "1937282828", "ajax@seguros.com.br", "Rua das Acácias 27");
		var joao = new ClientePF(
				"João Macedo",
				"Rua das Acácias 47",
				"Superior Completo",
				"M",
				"A",
				new ArrayList<Veiculo>(List.of(new Veiculo("FHV8J23", "Jeep", "Renegade", 2019))),
				"13247422161", new Date(0));
		var advogados = new ClientePJ(
				"Irmãos Santos Advogados",
				"Rua das Acácias",
				null,
				null,
				"",
				new ArrayList<>(),
				"03247586000181",
				Date.from(Instant.parse("2014-03-12T00:00:00.00Z")));
		var novoCarro = new Veiculo("GGD9H72", "Honda", "Fit", 2020);
		if (ClientePF.validarCPF(joao.getCpf())) {
			seguradora.cadastrarCliente(joao);
			joao.getVeiculos().add(novoCarro);
			seguradora.gerarSinistro("Rua das Acácias 102", joao.getVeiculos().get(1), joao);
		}
		if (ClientePJ.validarCNPJ(advogados.getCnpj())) {
			seguradora.cadastrarCliente(advogados);
			advogados.getVeiculos().add(new Veiculo("BLA8043", "Fiat", "Doblô", 2014));
		}
		System.out.println(1);
		System.out.println(seguradora.listarClientes("PF"));
		System.out.println(2);
		System.out.println(seguradora.listarClientes("PJ"));
		System.out.println(3);
		seguradora.visualizarSinistro("João Macedo");
		interativo(seguradora);
	}

	private static void interativo(Seguradora seguradora) {
		var sc = new Scanner(System.in);
		loop: while (true) {
			switch (sc.next()) {
				case "sair":
					System.out.println("Tchau!");
					break loop;
				case "clientes":
					System.out.println(seguradora.listarClientes(sc.next()));
					break;
				case "sinistros":
					System.out.println(seguradora.listarSinistros());
					break;
				default:
					System.out.println("comando não reconhecido.");
			}
			System.out.print("> ");
		}
		sc.close();
	}
}
