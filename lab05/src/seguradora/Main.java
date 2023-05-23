package seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		teste(seguradoras);
		interativo(seguradoras);
	}

	private static void teste(Map<String, Seguradora> seguradoras) {
		seguradoras.put("teste", new Seguradora("teste", "1936457845", "teste@teste.com", "Rua teste, 66"));
		Seguradora seg = seguradoras.get("teste");
		ClientePF pf;
		ClientePJ pj;
		pf = new ClientePF(
				"testepf",
				"Rua teste, 93",
				new ArrayList<>(List.of(new Veiculo("TES9T123", "Fiat", "Uno", 1999))),
				"Ensino Técnico",
				"Não binário",
				"Classe G",
				"165.574.467-46",
				LocalDate.of(2000, 1, 1),
				LocalDate.of(2010, 1, 1));
		pj = new ClientePJ(
				"testepj",
				"Rua teste 30",
				new ArrayList<>(List.of(new Frota(List.of(new Veiculo("AAA3333", "Fiat", "Uno", 2000))))),
				"46.068.425/0001-33",
				LocalDate.of(1985, 8, 12),
				123);
		seg.cadastrarCliente(pf);
		seg.cadastrarCliente(pj);
		System.out.println(seg.listarClientes(Cliente.Tipo.PF));
		seg.visualizarSinistro(pj);
		System.out.println(seg.listarSinistros());
		System.out.println(seg.calcularReceita());
	}

	private static void interativo(Map<String, Seguradora> seguradoras) {
		System.out.println("Bem vindo ao menu interativo.");
		MenuOperacao.menuPrincipal(seguradoras);
		System.out.println("Tchau.");
	}

}