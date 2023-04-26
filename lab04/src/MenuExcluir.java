public enum MenuExcluir {
	CLIENTE(1),
	VEICULO(2),
	SINISTRO(3),
	VOLTAR(4);

	final int index;

	public static MenuExcluir getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTE;
			case 2 -> VEICULO;
			case 3 -> SINISTRO;
			case 4 -> VOLTAR;
			default -> null;
		};
	}

	MenuExcluir(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}
}