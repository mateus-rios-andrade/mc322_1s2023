package seguradora;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		Map<String, ICondutor> condutores = new HashMap<>();
		teste(seguradoras, condutores);
		interativo(seguradoras, condutores);
	}

	private static void teste(Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		seguradoras.put("teste", new Seguradora("teste", "1936457845", "teste@teste.com", "Rua teste, 66"));
		Seguradora seguradora = seguradoras.get("teste");
		ICondutor condutor1 = new Condutor(
				"831.756.770-29",
				"José",
				"21986586559",
				"Rua do José",
				"jose@gmail.com",
				LocalDate.of(1987, 2, 28),
				Collections.emptyList());
		condutores.put(condutor1.getCpf(), condutor1);
		ICondutor condutor2 = new Condutor(
				"008.601.610-56",
				"José",
				"21986586559",
				"Rua do José",
				"jose@gmail.com",
				LocalDate.of(1987, 2, 28),
				Collections.emptyList());
		condutores.put(condutor2.getCpf(), condutor2);
		ClientePF pf = new ClientePF(
				"null",
				"null",
				"null", "null",
				List.of(new Veiculo("ABC1234", "null", "null", 2012)),
				"null",
				"null",
				"null",
				"397.556.610-85",
				LocalDate.of(2000, 2, 2),
				LocalDate.of(2020, 3, 4),
				Collections.emptyList());
		condutores.put(pf.getCpf(), pf);
		ClientePJ pj = new ClientePJ(
				"null",
				"null",
				"null",
				"null",
				List.of(
						new Frota("Caminhões", List.of(
								new Veiculo("ABC1D23", "null", "Caminhão", 2005),
								new Veiculo("JIK1234", "null", "Caminhão", 2020))),
						new Frota("Vans", List.of(
								new Veiculo("OIU1234", "null", "Van", 2022),
								new Veiculo("HJK1234", "null", "Van", 2000)))),
				"50.656.819/0001-80",
				LocalDate.of(2005, 1, 1),
				100);
		seguradora.cadastrarCliente(pf);
		seguradora.cadastrarCliente(pj);
		var spf = seguradora.gerarSeguro(
				pf,
				pf.getVeiculos().get(0),
				List.of(pf, condutor1),
				LocalDate.of(2018, 1, 1),
				LocalDate.of(2023, 1, 1));
		var spj = seguradora.gerarSeguro(
				pj,
				pj.getFrotas().get(0),
				List.of(condutor1, condutor2),
				LocalDate.of(2019, 1, 1),
				LocalDate.of(2024, 1, 1));
		spj.autorizarCondutor(pf);
		seguradora.gerarSinistro(spf, pf,LocalDate.of(2023, 5, 29), "Rua");
		seguradora.gerarSinistro(spj,  condutor2, LocalDate.of(2022, 2, 23), "Estrada");
		System.out.println(seguradora.getClientes());
		System.out.printf("Receita da seguradora: %.2f\n", seguradora.calcularReceita());
	}

	private static void interativo(Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		System.out.println("Bem vindo ao menu interativo.");
		MenuOperacao.menuPrincipal(seguradoras, condutores);
		System.out.println("Tchau.");
	}

}