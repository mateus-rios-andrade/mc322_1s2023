package seguradora;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;

public final class ClientePJ extends Cliente {
	private final String cnpj;
	private LocalDate dataFundacao;
	private int qtdFuncionarios;
	private List<Frota> frotas = new ArrayList<>();

	public ClientePJ(String nome, String telefone, String endereco, String email, List<Frota> frotas, String cnpj, LocalDate dataFundacao, int qtdFuncionarios) {
		super(nome, telefone, endereco, email);
		this.cnpj = cnpj;
		this.dataFundacao = dataFundacao;
		this.qtdFuncionarios = qtdFuncionarios;
		this.frotas.addAll(frotas);
	}

	public List<Veiculo> getVeiculosPorFrota(String code) {
		for (Frota frota : frotas) {
			if (frota.getCode().equals(code)) {
				return frota.getVeiculos();
			}
		}
		return null;
	}

	public boolean cadastrarFrota(Frota frota) {
		if (frotas.contains(frota)) {
			return frotas.add(frota);
		}
		return false;
	}

	public boolean removerFrota(Frota frota) {
		return frotas.remove(frota);
	}

	@Override
	public Tipo getTipo() {
		return Tipo.PJ;
	}

	@Override
	public String getID() {
		return cnpj;
	}

	public String mkString(String prefixo, String sep, String sufixo) {
		return super.mkString(prefixo, sep, "") + sep + "CNPJ: " + cnpj + sep + "Data de fundação: " + dataFundacao + sufixo;
	}

	public String getCnpj() {
		return cnpj;
	}
	
	public List<Frota> getFrotas() {
		return frotas;
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
		return "ClientePJ [nome=" + nome + ", cnpj=" + cnpj + ", Nº funcionários=" + qtdFuncionarios +  "]";
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
