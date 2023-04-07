import java.util.Date;
import java.util.List;

public class ClientePJ extends Cliente {
	private final String cnpj;
	private Date dataFundacao;
	private static final int[] multiplicadores = {9, 8, 7, 6, 5, 4, 3, 2, 9, 8, 7, 6, 5};

	public static boolean validarCNPJ(String cnpj) {
		var digitos = cnpjToArray(cnpj);
		if (digitos == null) return false;
		int primeiroDigito = getDV(digitos, 12);
		if (primeiroDigito != digitos[12]) return false;
		int segundoDigito = getDV(digitos, 13);
		if (segundoDigito != digitos[13]) return false;
		return true;
	}

	private static int getDV(int[] digitos, int m) {
		int soma = 0;
		for (int i = 0; i < m; i++) {
			soma += multiplicadores[i] * digitos[i];
		}
		int dv = soma % 11;
		return dv == 10 ? 0 : dv;
	}

	private static int[] cnpjToArray(String cnpj) {
		cnpj = cnpj.replaceAll("[^0-9]", "");
		if (cnpj.length() != 14) {
			return null;
		}
		var array = new int[14];
		for (int i = 0; i < 14; i++) {
			array[i] = cnpj.charAt(i) - '0';
		}
		return array;
	}


	public ClientePJ(String nome, String endereco, String educacao, String genero, String classeEconomica,
			List<Veiculo> veiculos, String cnpj, Date dataFundacao) {
		super(nome, endereco, educacao, genero, classeEconomica, veiculos);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
	}

	public String getCnpj() {
		return cnpj;
	}

	public Date getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(Date dataFundacao) {
		this.dataFundacao = dataFundacao;
	}

	@Override
	public String toString() {
		return "ClientePJ [nome=" + nome + ", endereco=" + endereco + ", educacao=" + educacao + ", genero=" + genero
				+ ", classeEconomica=" + classeEconomica + ", veiculos=" + veiculos + ", cnpj=" + cnpj
				+ ", dataFundacao=" + dataFundacao + "]";
	}
}
