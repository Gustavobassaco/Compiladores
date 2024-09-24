package exemplo;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class GyhLex {
	
	BufferedReader br;
	List<Token> tokens = new ArrayList<Token>();
	

	
	public List<Token> LerLinhas(String arquivo) throws FileNotFoundException {
		System.out.println("\n ---  Analisador Léxico  --- \n");

		String linha = "";
		List<String> linhas = new ArrayList<String>();
		Token token;
		AutomatoLexico analisadorLexico = new AutomatoLexico();
		
		BufferedReader br = new BufferedReader(new FileReader(arquivo));

		try  {
			while ((linha = br.readLine()) != null) {
			     linhas.add(linha); // pega todas as linhas do arquivo
			}
			br.close();
		} catch (Exception e) {
			    e.printStackTrace();	
		}
		char caractere;
		boolean aux;
		String string;
		
		for(int i = 0; i< linhas.size(); i++ ) { 					// percorre cada linha
			for(int j = 0; j < linhas.get(i).length() + 1; j++  ) {	// percorre cada caractere de cada linha
				token = null;
				aux = false;
				
				// adiciona ' ' no final de cada linha pois não tem '\n'
				if(j == linhas.get(i).length()) {
					caractere = ' ';
				}else { // caso contrário pega o caractere da linha em questão
					caractere = linhas.get(i).charAt(j);

				}
				
				if(caractere == '#')break; // se for # é um comentario, então basta pupar para a proxima linha
				
				/*
					Para saber se o proximo token é um E booleano ou outro token, basta verificar se
					o caracter seguinte é uma letra maiúscula. Caso não seja então é E booleano 
				*/
				if(caractere == 'E') { 
					string = linhas.get(i).charAt(j+1) + "";
					if(string.matches("[A-Z]")) { // verifica se o proximo é letra maiuscula
						aux = true; // booleano auxiliar
					}
				}
				token = analisadorLexico.Automato(caractere, aux, i);
				if(token != null) {
					tokens.add(token);
						j--;
						tokens.get(tokens.size() - 1).linha = i;
						/*
						 Para aceitar um token, gasta-se uma repetição, então
						 ao validar um token, volta-se para o anterior.
						*/
				}		
			}	
		}
		return tokens; // retorna a lista de tokens
	}
}