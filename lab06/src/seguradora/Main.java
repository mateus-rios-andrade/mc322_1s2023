package seguradora;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
	public static void main(String[] args) {
		Map<String, Seguradora> seguradoras = new HashMap<>();
		seguradoras.put("teste", new Seguradora("teste", "2135954863", "teste@teste.com", "Rua dos Testes 42"));
		teste(seguradoras);
	}

	/**
	 * Função que inicializa o estado inicial do programa e realiza testes.
	 */
	private static void teste(Map<String, Seguradora> seguradoras) {
		Seguradora seguradora = seguradoras.get("teste");
		List<Condutor> condutores = seguradora.lerDados();
		if (condutores == null) {
			System.err.println("Erro ao ler dados.");
		} else {
			Cliente cliente = seguradora.getClientes().get(0);
			Seguro seguro;
			if (cliente instanceof ClientePF c) {
				seguro = seguradora.gerarSeguro(c, c.getVeiculos().get(0), List.of(condutores.get(0)), LocalDate.of(2023, 6, 21), LocalDate.of(2024, 6, 21));
			} else {
				ClientePJ c = (ClientePJ)cliente;
				seguro = seguradora.gerarSeguro(c, c.getFrotas().get(0), List.of(condutores.get(1)), LocalDate.of(2022, 1, 1), LocalDate.of(2026, 1, 1));
			}
			seguradora.gerarSinistro(seguro, seguro.getCondutores().get(0), LocalDate.of(2023, 6, 1), "Rua dos Testes 42");
		}
		interativo(seguradoras, condutores.stream().collect(Collectors.toMap(Condutor::getCpf, Function.identity(), (x, y) -> x, () -> new HashMap<>())));
		seguradora.gravarDados();
		seguradora.getSeguros().forEach(System.out::println);
		seguradora.listarSinistros().forEach(System.out::println);
	}

	/**
	 * Função que inicia o menu interativo.
	 */
	private static void interativo(Map<String, Seguradora> seguradoras, Map<String, ICondutor> condutores) {
		System.out.println("Bem vindo ao menu interativo.");
		MenuOperacao.menuPrincipal(seguradoras, condutores);
		System.out.println("Tchau.");
	}

}