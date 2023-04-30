package seguradora;
public enum MenuListar {
    CLIENTES_SEG(1),
    SINISTROS_SEG(2),
    SINISTROS_CLIENTE(3),
    VEICULOS_CLIENTES(4),
    VEICULOS_SEG(5),
    VOLTAR(6),
	INVALIDO(-1);

    final int index;

    public static MenuListar getOpcao(int index) {
		return switch (index) {
			case 1 -> CLIENTES_SEG;
			case 2 -> SINISTROS_SEG;
			case 3 -> SINISTROS_CLIENTE;
			case 4 -> VEICULOS_CLIENTES;
			case 5 -> VEICULOS_SEG;
			case 6 -> VOLTAR;
			default -> INVALIDO;
		};
	}

    MenuListar(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}