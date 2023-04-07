public class App {
	public static void main(String[] args) {
		var seguradora = new Seguradora("Seguro", "2137344427", "seguradorasegura@gmail.com", "Avenida Brasil, 527");
		var jose = new Cliente("José", "16557446746", "01/01/1980", "Rua Siqueira Campos 146", 43);
		var carro = new Veiculo("LKD2368", "Volkswagen", "Fusca");
		var sinistro = new Sinistro(357939, "21/01/2023", "Rua do Catete, 34");
		System.out.println(seguradora);
		System.out.println(jose);
		System.out.println(carro);
		System.out.println(sinistro);
	}
}
