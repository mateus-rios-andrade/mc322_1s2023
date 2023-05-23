package seguradora;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class ClientePF extends Cliente {
	private String educacao, genero, classeEconomica;
	private final String cpf;
	private LocalDate dataNascimento, dataLicenca;
	private List<Veiculo> veiculos = new ArrayList<>();

	public ClientePF(String nome, String endereco, Collection<Veiculo> veiculos, String educacao, String genero,
			String classeEconomica, String cpf, LocalDate dataNascimento, LocalDate dataLicenca) {
		super(nome, endereco);
		this.educacao = educacao;
		this.genero = genero;
		this.classeEconomica = classeEconomica;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.dataLicenca = dataLicenca;
		this.veiculos.addAll(veiculos);
	}

	public boolean cadastrarVeiculo(Veiculo veiculo) {
		return veiculos.add(veiculo);
	}

	public boolean removerVeiculo(Veiculo veiculo) {
		return veiculos.remove(veiculo);
	}

	public int getIdade() {
		return Period.between(dataNascimento, LocalDate.now()).normalized().getYears();
	}

	@Override
	public String getID() {
		return cpf;
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "Educacao: " + educacao + sep + "Gênero: " + genero + sep + "Classe econômica: " 
		+ classeEconomica + sep + "CPF: " + cpf + sep + "Data de Nascimento: " + dataNascimento + sep + "Data da Licença: " + dataLicenca + sufixo;
	}

	public List<Veiculo> getVeiculos() {
		return veiculos;
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
		return "ClientePF [nome=" + nome + ", cpf=" + cpf + ", idade=" + getIdade() + "]";
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
