package seguradora;

import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public sealed interface MenuOperacao
		permits
		MenuOperacao.Sair,
		MenuOperacao.Cadastrar,
		MenuOperacao.Listar,
		MenuOperacao.Excluir,
		MenuOperacao.GerarSeguro,
		MenuOperacao.GerarSinistro,
		MenuOperacao.TransferirSeguro,
		MenuOperacao.CalcularReceita {
	public static final MenuOperacao[] operacoes = {
			new Sair(),
			new Cadastrar(),
			new Listar(),
			new Excluir(),
			new GerarSeguro(),
			new GerarSinistro(),
			new TransferirSeguro(),
			new CalcularReceita() };

	public int codigo();

	public void acao(Scanner sc, Map<String, Seguradora> seguradoras);

	public final class Sair implements MenuOperacao {
		private final int codigo = 0;

		private Sair() {

		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {
			sc.close();
			System.out.println("Tchau!");
		}

	}

	public final class Cadastrar implements MenuOperacao {
		private final int codigo = 1;

		private Cadastrar() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class Listar implements MenuOperacao {
		private final int codigo = 2;

		private Listar() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class Excluir implements MenuOperacao {
		private final int codigo = 3;

		private Excluir() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class GerarSeguro implements MenuOperacao {
		private final int codigo = 4;

		private GerarSeguro() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class GerarSinistro implements MenuOperacao {
		private final int codigo = 5;

		private GerarSinistro() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class TransferirSeguro implements MenuOperacao {
		private final int codigo = 6;

		private TransferirSeguro() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class CalcularReceita implements MenuOperacao {
		private final int codigo = 7;

		private CalcularReceita() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

}
/*
 * public enum MenuOperacao {
 * SAIR(0),
 * CADASTRAR(1),
 * LISTAR(2),
 * EXCLUIR(3),
 * GERAR_SINISTRO(4),
 * TRANSFERIR_SEGURO(5),
 * CALCULAR_RECEITA(6),
 * INVALIDO(-1);
 * 
 * public static void menuPrincipal(Map<String, Seguradora> seguradoras) {
 * Scanner sc = new Scanner(System.in);
 * boolean ficar = true;
 * while (ficar) {
 * System.out.println("1 - Cadastros");
 * System.out.println("2 - Listar");
 * System.out.println("3 - Excluir");
 * System.out.println("4 - Gerar Sinistro");
 * System.out.println("5 - Transferir Seguro");
 * System.out.println("6 - Calcular Receita Seguradora");
 * System.out.println("0 - Sair");
 * MenuOperacao op = MenuOperacao.getOpcao(getInt(sc, ""));
 * switch (op) {
 * case CADASTRAR -> MenuCadastrar.cadastrar(sc, seguradoras);
 * case LISTAR -> MenuListar.listar(sc, seguradoras);
 * case EXCLUIR -> MenuExcluir.excluir(sc, seguradoras);
 * case GERAR_SINISTRO -> gerarSinistro(sc, seguradoras);
 * case TRANSFERIR_SEGURO -> transferirSeguro(sc, seguradoras);
 * case CALCULAR_RECEITA -> calcularReceita(sc, seguradoras);
 * case SAIR -> ficar = false;
 * default -> System.out.println("Comando inválido.");
 * }
 * }
 * sc.close();
 * }
 * 
 * private static void gerarSinistro(Scanner sc, Map<String, Seguradora>
 * seguradoras) {
 * Seguradora seg = getSeguradora(sc, seguradoras);
 * if (seg == null) {
 * return;
 * }
 * Cliente cliente = getCliente(sc, seg);
 * if (cliente == null) {
 * return;
 * }
 * int i = getItem(sc, cliente.getVeiculos(), "Veículo");
 * if (i < 0) {
 * System.out.println("Esse cliente não possui veículos.");
 * return;
 * }
 * seg.gerarSinistro(
 * getDate(sc, "Data do ocorrido: "),
 * getString(sc, "Endereço"),
 * cliente.getVeiculos().get(i),
 * cliente);
 * }
 * 
 * private static void transferirSeguro(Scanner sc, Map<String, Seguradora>
 * seguradoras) {
 * Seguradora seg = getSeguradora(sc, seguradoras);
 * if (seg == null) {
 * return;
 * }
 * Cliente clienteOriginal = getCliente(sc, seg);
 * if (clienteOriginal == null) {
 * return;
 * }
 * Cliente novoCliente = criarCliente(sc);
 * if (clienteOriginal instanceof ClientePF && novoCliente instanceof ClientePJ
 * || clienteOriginal instanceof ClientePJ && novoCliente instanceof ClientePF)
 * {
 * System.out.
 * println("Só é possível transferir seguros entre clientes do mesmo tipo.");
 * } else {
 * seg.trocarCliente(clienteOriginal, novoCliente);
 * }
 * 
 * }
 * 
 * private static void calcularReceita(Scanner sc, Map<String, Seguradora>
 * seguradoras) {
 * Seguradora seg = getSeguradora(sc, seguradoras);
 * if (seg == null) {
 * return;
 * }
 * System.out.printf("R$ %.2f\n", seg.calcularReceita());
 * }
 * 
 * public static MenuOperacao getOpcao(int index) {
 * return switch (index) {
 * case 0 -> SAIR;
 * case 1 -> CADASTRAR;
 * case 2 -> LISTAR;
 * case 3 -> EXCLUIR;
 * case 4 -> GERAR_SINISTRO;
 * case 5 -> TRANSFERIR_SEGURO;
 * case 6 -> CALCULAR_RECEITA;
 * default -> INVALIDO;
 * };
 * }
 * 
 * private final int index;
 * 
 * MenuOperacao(int index) {
 * this.index = index;
 * }
 * 
 * public int getIndex() {
 * return index;
 * }
 * 
 * }
 */
