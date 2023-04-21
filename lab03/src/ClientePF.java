import java.util.Date;
import java.util.List;

public class ClientePF extends Cliente {
	private String educacao, genero, classeEconomica;
    private final String cpf;
	private Date dataNascimento, dataLicenca;

	public ClientePF(String nome, String endereco, List<Veiculo> veiculos, String educacao, String genero,
			String classeEconomica, String cpf, Date dataNascimento, Date dataLicenca) {
		super(nome, endereco, veiculos);
		this.educacao = educacao;
		this.genero = genero;
		this.classeEconomica = classeEconomica;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.dataLicenca = dataLicenca;
	}

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

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "Educacao: " + educacao + sep + "Gênero: " + genero + sep + "Classe econômica: " 
		+ classeEconomica + sep + "CPF: " + cpf + sep + "Data de Nascimento: " + dataNascimento + sep + "Data da Licença: " + dataLicenca + sufixo;
	}

	public Date getDataLicenca() {
		return dataLicenca;
	}

	public void setDataLicenca(Date dataLicenca) {
		this.dataLicenca = dataLicenca;
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
	public String getEducacao() {
        return educacao;
    }

    public void setEducacao(String educacao) {
        this.educacao = educacao;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getClasseEconomica() {
        return classeEconomica;
    }

    public void setClasseEconomica(String classeEconomica) {
        this.classeEconomica = classeEconomica;
    }

	@Override
	public String toString() {
		return "ClientePF [nome=" + nome + ", endereco=" + endereco + ", educacao=" + educacao + ", genero=" + genero
				+ ", classeEconomica=" + classeEconomica + ", veiculos=" + veiculos + ", cpf=" + cpf
				+ ", dataNascimento=" + dataNascimento + "]";
	}

	@Override
	public int hashCode() {
		return cpf.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClientePF other = (ClientePF) obj;
		return other != null && cpf.equals(other.cpf);
	}
}
