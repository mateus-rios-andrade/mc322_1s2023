package seguradora;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

public final class ClientePF extends Cliente {
	private String educacao, genero, classeEconomica;
    private final String cpf;
	private LocalDate dataNascimento, dataLicenca;

	public ClientePF(String nome, String endereco, List<Veiculo> veiculos, String educacao, String genero,
			String classeEconomica, String cpf, LocalDate dataNascimento, LocalDate dataLicenca) {
		super(nome, endereco, veiculos);
		this.educacao = educacao;
		this.genero = genero;
		this.classeEconomica = classeEconomica;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.dataLicenca = dataLicenca;
	}

	public int getIdade() {
		return Period.between(LocalDate.now(), dataNascimento).normalized().getYears();
	}

	@Override
	public String getID() {
		return cpf;
	}

	@Override
	public double calcularScore() {
		return CalcSeguro.VALOR_BASE.getValor() * CalcSeguro.deIdade(getIdade()).getValor() * veiculos.size();
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "Educacao: " + educacao + sep + "Gênero: " + genero + sep + "Classe econômica: " 
		+ classeEconomica + sep + "CPF: " + cpf + sep + "Data de Nascimento: " + dataNascimento + sep + "Data da Licença: " + dataLicenca + sufixo;
	}

	public LocalDate getDataLicenca() {
		return dataLicenca;
	}

	public void setDataLicenca(LocalDate dataLicenca) {
		this.dataLicenca = dataLicenca;
	}

	public String getCpf() {
		return cpf;
	}

	public LocalDate getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(LocalDate dataNascimento) {
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
