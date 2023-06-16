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

	private CSV(List<List<String>> dados) {
		this.dados = dados;
	}

	public static CSV deDados(List<List<Object>> dados) {
		return new CSV(
				dados.stream()
						.map(l -> l.stream()
								.map(o -> o.toString()).toList())
						.toList());
	}

	public static CSV deArquivo(String nomeArquivo) {
		try (FileReader fr = new FileReader(nomeArquivo);
				BufferedReader br = new BufferedReader(fr)) {
			String l;
			boolean primeiraLinha = true;
			var dados = new ArrayList<List<String>>();
			while ((l = br.readLine()) != null) {
				if (primeiraLinha) {
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
			return new CSV(dados);
		} catch (FileNotFoundException e) {
			throw new ReadCSVException("Arquivo " + nomeArquivo + " não encontrado.", e);
		} catch (IOException e) {
			throw new ReadCSVException("Erro de IO com mensagem: " + e.getMessage(), e);
		}
	}

	public void gravarEm(String nomeArquivo) {
		try (var fw = new FileWriter(nomeArquivo, StandardCharsets.UTF_8, false);
				var writer = new BufferedWriter(fw)) {
			for (List<String> linha : dados) {
				boolean primeiraVez = true;
				for (String dado : linha) {
					if (primeiraVez) {
						primeiraVez = false;
					} else {
						writer.append(',');
					}
					String str;
					if (dado.contains("\"") || dado.contains(",")) {
						var charArray = dado.toCharArray();
						var sb = new StringBuilder(charArray.length + 10);
						sb.append('"');
						for (char chr : charArray) {
							if (chr == '\\' || chr == '"') {
								sb.append('\\');
							}
							sb.append(chr);
						}
						str = sb.toString();
					} else {
						str = dado;
					}
					writer.append(str);
				}
				writer.newLine();
			}

		} catch (IOException e) {
			
		}
	}

	public List<List<String>> getDados() {
		return dados;
	}
}
