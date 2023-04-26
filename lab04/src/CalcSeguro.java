public enum CalcSeguro {
	VALOR_BASE(100.0),
	FATOR_18_30(1.2),
	FATOR_30_60(1.0),
	FATOR_60_90(1.5);

	private final double valor;

	public static CalcSeguro deIdade(int idade) {
		if (idade < 18 || idade > 90) return null;
		if (idade < 30) return FATOR_18_30;
		if (idade < 60) return FATOR_30_60;
		return FATOR_60_90;
	}

	CalcSeguro(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}
}
