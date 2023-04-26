public enum MenuOperacao implements Operacao {
    SAIR(0),
	CADASTRAR(1),
	LISTAR(2),
	EXCLUIR(3),
	GERAR_SINISTRO(4),
	TRANSFERIR_SEGURO(5);

	private final int index;

	MenuOperacao(int index) {
		this.index = index;
	}

	public static MenuOperacao getOpcao(int index) {
		return switch (index) {
			case 0 -> SAIR;
			case 1 -> CADASTRAR;
			case 2 -> LISTAR;
			case 3 -> EXCLUIR;
			case 4 -> GERAR_SINISTRO;
			case 5 -> TRANSFERIR_SEGURO;
			default -> null;
		};
	}

    @Override
	public int getIndex() {
		return index;
	}
    
}