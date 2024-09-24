package exemplo;

import java.io.IOException;
import java.util.List;

/* Trabalho de Compiladores
 * Compilador Léxico.
 * Compilador Sintático
 * 	Gustavo Rodrigues Bassaco	RA: 2207206
 * 	Rodrigo Koichi Irie			RA: 2104610	
 * 
 */

public class MainClass {

	public static void main(String[] args) throws IOException{
		
		List<Token> lexemas;  // lista de lexemas
	    lexemas = new GyhLex().LerLinhas(args[0]); // recebe a lista do retorno da função lerlinhas
		GyhSintatico sin = new GyhSintatico(lexemas); // analisador sintatico
		
		
	}
}
