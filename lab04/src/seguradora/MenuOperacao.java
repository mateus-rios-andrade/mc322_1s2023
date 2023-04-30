package seguradora;
import java.util.Map;
import java.util.Scanner;
import static seguradora.Utils.*;

public enum MenuOperacao {
    SAIR(0),
	CADASTRAR(1),
	LISTAR(2),
	EXCLUIR(3),
	GERAR_SINISTRO(4),
	TRANSFERIR_SEGURO(5),
	CALCULAR_RECEITA(6),
	INVALIDO(-1);

	public static void menuPrincipal(Map<String, Seguradora> seguradoras) {
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
				case CADASTRAR -> MenuCadastrar.cadastrar(sc, seguradoras);
				case LISTAR -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case EXCLUIR -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case GERAR_SINISTRO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case TRANSFERIR_SEGURO -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case CALCULAR_RECEITA -> throw new UnsupportedOperationException("Unimplemented case: " + op);
				case SAIR -> ficar = false;
				default -> System.out.println("Comando inválido.");
			}
		}
		sc.close();
	}

	public static MenuOperacao getOpcao(int index) {
		return switch (index) {
			case 0 -> SAIR;
			case 1 -> CADASTRAR;
			case 2 -> LISTAR;
			case 3 -> EXCLUIR;
			case 4 -> GERAR_SINISTRO;
			case 5 -> TRANSFERIR_SEGURO;
			case 6 -> CALCULAR_RECEITA;
			default -> INVALIDO;
		};
	}

	private final int index;

	MenuOperacao(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
    
}