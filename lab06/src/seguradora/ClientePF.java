package seguradora;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Representa uma pessoa física que também é cliente. Implementa ICondutor para
 * poder ser considerado também um condutor dentro do sistema sem ter que ser representado
 * por dois objetos diferentes.
 */
public final class ClientePF extends Cliente implements ICondutor {
	private String educacao, genero, classeEconomica;
	private final String cpf;
	private LocalDate dataNascimento, dataLicenca;
	private List<Veiculo> veiculos = new ArrayList<>();
	private List<Sinistro> sinistros = new ArrayList<>();

	public ClientePF(String nome, String telefone, String endereco, String email, Collection<Veiculo> veiculos,
			String educacao, String genero,
			String classeEconomica, String cpf, LocalDate dataNascimento, LocalDate dataLicenca,
			Collection<Sinistro> sinistros) {
		super(nome, telefone, endereco, email);
		this.educacao = educacao;
		this.genero = genero;
		this.classeEconomica = classeEconomica;
		this.cpf = cpf;
		this.dataNascimento = dataNascimento;
		this.dataLicenca = dataLicenca;
		this.veiculos.addAll(veiculos);
		this.sinistros.addAll(sinistros);
	}

	/**
	 * Cadastra um veículo. 
	 */
	public boolean cadastrarVeiculo(Veiculo veiculo) {
		return veiculos.add(veiculo);
	}

	/**
	 * Remove um veículo cadastrado.
	 */
	public boolean removerVeiculo(Veiculo veiculo) {
		return veiculos.remove(veiculo);
	}

	
	@Override
	public Tipo getTipo() {
		return Tipo.PF;
	}

	@Override
	public String getID() {
		return cpf;
	}

	@Override
	public String mkString(String prefixo, String sep, String sufixo) {
		return ICondutor.super.mkString(prefixo, sep, sep) + "Nº Veículos: " + veiculos.size() + sufixo;
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
	public List<Sinistro> getSinistros() {
		return sinistros;
	}

	@Override
	public String toString() {
		return "ClientePF [nome=" + getNome() + ", cpf=" + cpf + ", idade=" + getIdade() + "]";
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
		if (obj instanceof ICondutor other) {
			return other != null && cpf.equals(other.getCpf());
		}
		return false;
	}
}
