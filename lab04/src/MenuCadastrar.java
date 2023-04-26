public enum MenuCadastrar {
	CLIENTE(1),
	VEICULO(2),
	SEGURADORA(3),
	VOLTAR(4);

	public static MenuCadastrar getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTE;
			case 2 -> VEICULO;
			case 3 -> SEGURADORA;
			case 4 -> VOLTAR;
			default -> null;
		};
	}

	final int index;
	MenuCadastrar(int index) {
		this.index = index;
	}
}