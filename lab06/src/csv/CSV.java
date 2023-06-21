package csv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class CSV {
	private final List<List<String>> dados;
	private final String header;

	private CSV(List<List<String>> dados, String header) {
		this.dados = dados;
		this.header = header;
	}

	public static CSV deDados(List<List<String>> dados, String header) {
		return new CSV(
				dados,
				header);
	}

	public static CSV deArquivo(String nomeArquivo, boolean hasHeader) {
		try (FileReader fr = new FileReader(nomeArquivo);
				BufferedReader br = new BufferedReader(fr)) {
			String l;
			boolean primeiraLinha = hasHeader;
			String header = "";
			var dados = new ArrayList<List<String>>();
			while ((l = br.readLine()) != null) {
				if (primeiraLinha) {
					header = l;
					primeiraLinha = false;
				} else {
					List<String> a = new ArrayList<>();
					dados.add(a);
					List<Character> chars = new ArrayList<>();
					boolean aspas = false;
					boolean contrabarra = false;
					var linha = l.toCharArray();
					System.out.println();
					for (char chr : linha) {
						if (aspas) {
							if (contrabarra) {
								chars.add(chr);
								contrabarra = false;
							} else if (chr == '"') {
								aspas = false;
							} else if (chr == '\\') {
								contrabarra = true;
							} else {
								chars.add(chr);
							}
						} else {
							if (chr == '"') {
								aspas = true;
							} else if (chr == ',') {
								var sb = new StringBuilder(chars.size());
								for (char c : chars) {
									sb.append(c);
								}
								a.add(sb.toString());
								chars.clear();
							} else {
								chars.add(chr);
							}
						}
					}
				}
			}
			return new CSV(dados, header);
		} catch (FileNotFoundException e) {
			throw new ReadCSVException("Arquivo " + nomeArquivo + " não encontrado.", e);
		} catch (IOException e) {
			throw new ReadCSVException("Erro de IO com mensagem: " + e.getMessage(), e);
		}
	}

	private static String formatar(String str) {
		if (str.contains("\"") || str.contains(",")) {
			var charArray = str.toCharArray();
			var sb = new StringBuilder(charArray.length + 8);
			sb.append('"');
			for (char chr : charArray) {
				if (chr == '\\' || chr == '"') {
					sb.append('\\');
				}
				sb.append(chr);
			}
			return sb.toString();
		} else {
			return str;
		}
	}

	private static void escrever(List<List<String>> dados, Appendable a) throws IOException {
		for (List<String> linha : dados) {
			boolean primeiraVez = true;
			for (String dado : linha) {
				if (primeiraVez) {
					primeiraVez = false;
				} else {
					a.append(',');
				}
				String str = formatar(dado);
				a.append(str);
			}
			a.append(System.lineSeparator());
		}
	}

	public void gravarEm(String nomeArquivo) {
		try (var fw = new FileWriter(nomeArquivo, StandardCharsets.UTF_8, false);
				var writer = new BufferedWriter(fw)) {
			if (header != "") {
				writer.append(header);
				writer.newLine();
			}
			escrever(dados, writer);
		} catch (IOException e) {
			throw new WriteCSVException("Erro ao gravar arquivo. Msg: " + e.getMessage(), e);
		}
	}

	public List<List<String>> getDados() {
		return dados;
	}

	public String getHeader() {
		return header;
	}
}
