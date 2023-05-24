package seguradora;

import static seguradora.Utils.*;

import java.util.Map;
import java.util.Scanner;

public sealed interface MenuCadastrar
	permits 
	MenuCadastrar.Cadastrar,
	MenuCadastrar.Veiculo,
	MenuCadastrar.Seguradora,
	MenuCadastrar.Condutor,
	MenuCadastrar.Voltar {
	public int codigo();
	
	public void acao(Scanner sc, Map<String, Seguradora> seguradoras);

	public final class Cadastrar implements MenuCadastrar {
		private final int codigo = 2;

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

	public final class Veiculo implements MenuCadastrar {
		private final int codigo = 2;

		private Veiculo() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class Seguradora implements MenuCadastrar {
		private final int codigo = 2;

		private Seguradora() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class Condutor implements MenuCadastrar {
		private final int codigo = 2;

		private Condutor() {
		}

		@Override
		public int codigo() {
			return codigo;
		}

		@Override
		public void acao(Scanner sc, Map<String, Seguradora> seguradoras) {

		}
	}

	public final class Voltar implements MenuCadastrar {
		private final int codigo = 2;

		private Voltar() {
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
public enum MenuCadastrar {
	CLIENTE(1),
	VEICULO(2),
	SEGURADORA(3),
	VOLTAR(4),
	INVALIDO(-1);

	public static void cadastrar(Scanner sc, Map<String, Seguradora> seguradoras) {
		boolean ficar = true;
		while (ficar) {
			System.out.println("1.1 - Cadastrar Cliente PF/PJ");
			System.out.println("1.2 - Cadastrar Veiculo");
			System.out.println("1.3 - Cadastrar Seguradora");
			System.out.println("1.4 - Voltar");
			MenuCadastrar op = getOpcao(getInt(sc, ""));
			switch (op) {
				case CLIENTE -> cliente(sc, seguradoras);
				case VEICULO -> veiculo(sc, seguradoras);
				case SEGURADORA -> seguradora(sc, seguradoras);
				case VOLTAR -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
	}

	private static void cliente(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		seg.cadastrarCliente(criarCliente(sc));
	}

	private static void veiculo(Scanner sc, Map<String, Seguradora> seguradoras) {
		Seguradora seg = getSeguradora(sc, seguradoras);
		if (seg == null) {
			return;
		}
		Cliente cliente = getCliente(sc, seg);
		if (cliente == null) {
			return;
		}
		var veiculos = getVeiculos(sc);
		cliente.getVeiculos().addAll(veiculos);
	}

	private static void seguradora(Scanner sc, Map<String, Seguradora> seguradoras) {
		String nome = getString(sc, "Nome: ");
		seguradoras.put(nome, new Seguradora(
				nome,
				getString(sc, "Telefone: "),
				getString(sc, "Email: "),
				getString(sc, "Endereço: ")));
	}

	public static MenuCadastrar getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTE;
			case 2 -> VEICULO;
			case 3 -> SEGURADORA;
			case 4 -> VOLTAR;
			default -> INVALIDO;
		};
	}

	private final int index;

	MenuCadastrar(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}
*/
