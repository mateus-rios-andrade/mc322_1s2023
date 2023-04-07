import java.util.Date;
import java.util.List;

public class ClientePF extends Cliente {
	private final String cpf;
	private Date dataNascimento;

	public static boolean validarCPF(String cpf) {
		var digitos = cpfToArray(cpf);
		if (digitos == null)
			return false;
		int soma = 0;
		for (int m = 10, i = 0; m >= 2; m--, i++) {
			soma += m * digitos[i];
		}
		int primeiroDigito = 11 - (soma % 11);
		if (primeiroDigito == 10)
			primeiroDigito = 0;
		if (primeiroDigito != digitos[9])
			return false;

		soma = 0;
		for (int m = 10, i = 1; m >= 2; m--, i++) {
			soma += m * digitos[i];
		}
		int segundoDigito = 11 - (soma % 11);
		if (segundoDigito == 10)
			segundoDigito = 0;
		if (segundoDigito != digitos[10])
			return false;

		return true;
	}

	private static int[] cpfToArray(String cpf) {
		cpf = cpf.replaceAll("[^0-9]", "");
		if (cpf.length() != 11) {
			return null;
		}
		var array = new int[11];
		for (int i = 0; i < 11; i++) {
			array[i] = cpf.charAt(i) - '0';
		}
		return array;
	}

	public ClientePF(String nome, String endereco, String educacao, String genero, String classeEconomica,
			List<Veiculo> veiculos, String cpf, Date dataNascimento) {
		super(nome, endereco, educacao, genero, classeEconomica, veiculos);
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
	}

	public String getCpf() {
		return cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}
	@Override
	public String toString() {
		return "ClientePJ [nome=" + nome + ", endereco=" + endereco + ", educacao=" + educacao + ", genero=" + genero
				+ ", classeEconomica=" + classeEconomica + ", veiculos=" + veiculos + ", cpf=" + cpf
				+ ", dataNascimento=" + dataNascimento + "]";
	}
}
