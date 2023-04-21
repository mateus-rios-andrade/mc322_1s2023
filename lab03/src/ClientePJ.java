import java.util.Date;
import java.util.List;

public class ClientePJ extends Cliente {
	private final String cnpj;
	private Date dataFundacao;
	private static final int[] multiplicadores = {6,5,4,3,2,9,8,7,6,5,4,3,2};

	public static boolean validarCNPJ(String cnpj) {
		var digitos = cnpjToArray(cnpj);
		if (digitos == null) return false;
		int primeiroDigito = getDV(digitos, 1);
		if (primeiroDigito != digitos[12]) return false;
		int segundoDigito = getDV(digitos, 0);
		if (segundoDigito != digitos[13]) return false;
		return true;
	}

	private static int getDV(int[] digitos, int m) {
		int soma = 0;
		for (int i = 0; i < 13 - m; i++) {
			soma += multiplicadores[i + m] * digitos[i];
		}
		int dv = 11 - (soma % 11);
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


	public ClientePJ(String nome, String endereco, List<Veiculo> veiculos, String cnpj, Date dataFundacao) {
		super(nome, endereco, veiculos);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
	}

	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "CNPJ: " + cnpj + sep + "Data de fundação: " + dataFundacao + sufixo;
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
		return "ClientePJ [nome=" + nome + ", endereco=" + endereco + ", educacao=" + ", veiculos=" + veiculos + ", cnpj=" + cnpj
				+ ", dataFundacao=" + dataFundacao + "]";
	}

	@Override
	public int hashCode() {
		return cnpj.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientePJ other = (ClientePJ) obj;
		return other != null && cnpj.equals(other.cnpj);
	}
}
