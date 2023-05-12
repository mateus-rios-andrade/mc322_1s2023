package seguradora;
import java.util.List;
import java.time.LocalDate;

public final class ClientePJ extends Cliente {
	private final String cnpj;
	private LocalDate dataFundacao;
	private int qtdFuncionarios;
	
	public ClientePJ(String nome, String endereco, List<Veiculo> veiculos, String cnpj, LocalDate dataFundacao, int qtdFuncionarios) {
		super(nome, endereco, veiculos);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
	}

	@Override
	public String getID() {
		return cnpj;
	}

	@Override
	public double calcularScore() {
		return CalcSeguro.VALOR_BASE.getValor() * (1 + qtdFuncionarios/100) * veiculos.size();
	}

	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "CNPJ: " + cnpj + sep + "Data de fundação: " + dataFundacao + sufixo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public LocalDate getDataFundacao() {
		return dataFundacao;
	}

	public void setDataFundacao(LocalDate dataFundacao) {
		this.dataFundacao = dataFundacao;
	}
	
	public int getQtdFuncionarios() {
		return qtdFuncionarios;
	}

	public void setQtdFuncionarios(int qtdFuncionarios) {
		this.qtdFuncionarios = qtdFuncionarios;
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
