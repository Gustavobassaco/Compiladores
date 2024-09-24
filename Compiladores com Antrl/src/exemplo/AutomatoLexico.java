package exemplo;

public class AutomatoLexico {
	private String string = "";
	private int estado =  1;
	
	public Token Automato(char caro, boolean aux ,int linha) {
		Token retorno = new Token(TipoToken.Delim,"inicio");

		/* controle = 0: processando
		   controle = 1: estado final
		   controle = 2: erro
		*/
		int controle = 0;
		String erro="";
		
		switch (estado) {
		case 1:
			// Operadores Relacionais
			if(caro == '<') estado = 2;
			else if(caro == '=') estado = 5;
			else if(caro == '>') estado = 7;
			else if(caro == '!') estado = 10;
			
			// Palavras-Chave
			else if(caro == 'D') estado = 12;
			else if(caro == 'P') estado = 15;
			else if(caro == 'I') estado = 19;
			else if(caro == 'L') estado = 30;
			else if(caro == 'S') estado = 33;
			else if(caro == 'E') {
				if(aux) {
					estado = 35;	
				}else {
					// E (And)
					estado = 50;
				}
			}
			else if(caro == 'F') estado = 43;
			else if(caro == 'R') estado = 46;
			
			// Operadores Booleanos
			else if(caro == 'O') estado = 51;
			
			// Operadores Aritiméticos
			else if(caro == '*') estado = 53;
			else if(caro == '/') estado = 54;
			else if(caro == '+') estado = 55;
			else if(caro == '-') estado = 56;
			
			// Delimitador e Atribuição
			else if(caro == ':') estado = 57;
			
			// Parêntesis
			else if(caro == '(') estado = 59;
			else if(caro == ')') estado = 60;
			
			// Número Inteiro e Número Real
			else if(Character.isDigit(caro)) {
				string = string + caro;
				estado = 61;
				
			}
			//else if(caro == ' ') estado = 1;
							
			// Variável
			else if(Character.isLowerCase(caro) && caro != ' ' ) { 
				estado = 63;
				string = string + caro;
			}
			// Cadeia de Caracteres
			else if(caro == '"') estado = 64;
			else if(caro == ' ' || caro == '\n' || caro == '\t' || caro == '\s') {
				controle = 0;
				estado = 1;
			}else {
				erro = "caractere inválido";
				controle = 2;
			}
			
			
			break;
		case 2:
			if(caro == '=') estado = 3;
			else estado = 4;
	
			break;
		case 3:
			retorno = new Token(TipoToken.OpRelMenorIgual, "<=");
			controle = 1;
			break;
		case 4:
			retorno = new Token(TipoToken.OpRelMenor, "<");
			controle = 1;

			break;
		case 5:
			if(caro == '=') estado = 6;
			else {
				erro = "erro léxico no sinal de igualdade";
				controle = 2;
			}

			break;
		case 6:
			retorno = new Token(TipoToken.OpRelIgual, "==");
			controle = 1;

			break;
		case 7:
			if(caro == '=') estado = 8;
			else estado = 9;
			
			break;
		case 8:
			retorno = new Token(TipoToken.OpRelMaiorIgual, ">=");
			controle = 1;

			break;
		case 9:
			retorno = new Token(TipoToken.OpRelMaior, ">");
			controle = 1;

			break;
		case 10:
			if(caro == '=') estado = 11;

			break;
		case 11:
			retorno = new Token(TipoToken.OpRelDif, "!=");
			controle = 1;

			break;
		case 12:
			if(caro == 'E') estado = 13;
			else {
				erro = "erro léxico DEC";
				controle = 2;
			}

			break;
		case 13:
			if(caro == 'C') estado = 14;
			else {
				erro = "erro léxico DEC";
				controle = 2;
			}

			break;
		case 14:
			retorno = new Token(TipoToken.PCDec, "DEC");
			controle = 1;

			break;
		case 15:
			if(caro == 'R') estado = 16;
			else {
				erro = "erro léxico PROG";
				controle = 2;
			}

			break;
		case 16:
			if(caro == 'O') estado = 17;
			else {
				erro = "erro léxico PROG";
				controle = 2;
			}

			break;
		case 17:
			if(caro == 'G') estado = 18;
			else {
				erro = "erro léxico PROG";
				controle = 2;
			}

			break;
		case 18:
			retorno = new Token(TipoToken.PCProg, "PROG");
			controle = 1;

			break;
		case 19:
			if(caro == 'N') estado = 20;
			else if(caro == 'M') estado = 23;
			else {
				erro = "erro léxico após 'I'";
				controle = 2;
			}

			break;
		case 20:
			if(caro == 'I') estado = 21;
			else if(caro == 'T') estado = 22;
			else {
				erro = "erro léxico após 'IN'";
				controle = 2;
			}

			break;
		case 21:
			retorno = new Token(TipoToken.PCIni, "INI");
			controle = 1;

			break;
		case 22:
			retorno = new Token(TipoToken.PCInt, "INT");
			controle = 1;

			break;
		case 23:
			if(caro == 'P') estado = 24;
			else {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 24:
			if(caro == 'R') estado = 25;
			else {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 25:
			if(caro == 'I') estado = 26;
			else  {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 26:
			if(caro == 'M') estado = 27;
			else  {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 27:
			if(caro == 'I') estado = 28;
			else  {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 28:
			if(caro == 'R') estado = 29;
			else  {
				erro = "erro léxico IMPRIMIR";
				controle = 2;
			}

			break;
		case 29:
			retorno = new Token(TipoToken.PCImprimir, "IMPRIMIR");
			controle = 1;

			break;
		case 30:
			if(caro == 'E') estado = 31;
			else  {
				erro = "erro léxico LER";
				controle = 2;
			}

			break;
		case 31:
			if(caro == 'R') estado = 32;
			else  {
				erro = "erro léxico LER";
				controle = 2;
			}

			break;
		case 32:
			retorno = new Token(TipoToken.PCLer, "LER");
			controle = 1;

			break;
		case 33:
			if(caro == 'E') estado = 34;
			else  {
				erro = "erro léxico SE";
				controle = 2;
			}

			break;
		case 34:
			retorno = new Token(TipoToken.PCSe, "SE");
			controle = 1;

			break;
		case 35:
			if(caro == 'N') estado = 36;
			else  {
				erro = "erro léxico após 'E'";
				controle = 2;
			}

			break;
		case 36:
			if(caro == 'Q') estado = 37;
			else if(caro == 'T') estado = 40;
			else  {
				erro = "erro léxico após 'EN'";
				controle = 2;
			}

			break;
		case 37:
			if(caro == 'T') estado = 38;
			else {
				erro = "erro léxico ENQTO";
				controle = 2;
			}
			break;
		case 38:
			if(caro == 'O') estado = 39;
			else{
				erro = "erro léxico ENQTO";
				controle = 2;
			}

			break;
		case 39:
			retorno = new Token(TipoToken.PCEnqto, "ENQTO");
			controle = 1;

			break;
		case 40:
			if(caro == 'A') estado = 41;
			else{
				erro = "erro léxico ENTAO";
				controle = 2;
			}

			break;
		case 41:
			if(caro == 'O') estado = 42;
			else {
				erro = "erro léxico ENTAO";
				controle = 2;
			}

			break;
		case 42:
			retorno = new Token(TipoToken.PCEntao, "ENTAO");
			controle = 1;

			break;
		case 43:
			if(caro == 'I') estado = 44;
			else {
				erro = "erro léxico FIM";
				controle = 2;
			}
			
			break;
		case 44:
			if(caro == 'M') estado = 45;
			else {
				erro = "erro léxico FIM";
				controle = 2;
			}

			break;
		case 45:
			retorno = new Token(TipoToken.PCFim, "FIM");
			controle = 1;

			break;
		case 46:
			if(caro == 'E') estado = 47;
			else {
				erro = "erro léxico REAL";
				controle = 2;
			}
			break;
		case 47:
			if(caro == 'A') estado = 48;
			else{
				erro = "erro léxico REAL";
				controle = 2;
			}

			break;
		case 48:
			if(caro == 'L') estado = 49;
			else{
				erro = "erro léxico REAL";
				controle = 2;
			}

			break;
		case 49:
			retorno = new Token(TipoToken.PCReal, "REAL");
			controle = 1;

			break;
		case 50:
			retorno = new Token(TipoToken.OpBoolE, "E");
			controle = 1;

			break;
		case 51:
			if(caro == 'U') estado = 52;
			else {
				erro = "erro léxico OU";
				controle = 2;
			}
			break;
		case 52:
			retorno = new Token(TipoToken.OpBoolOu, "OU");
			controle = 1;

			break;
		case 53:
			retorno = new Token(TipoToken.OpAritMult, "*");
			controle = 1;

			break;
		case 54:
			retorno = new Token(TipoToken.OpAritDiv, "/");
			controle = 1;

			break;
		case 55:
			retorno = new Token(TipoToken.OpAritSoma, "+");
			controle = 1;

			break;
		case 56:
			retorno = new Token(TipoToken.OpAritSub, "-");
			controle = 1;

			break;
		case 57:
			if(caro == '=') estado = 58;
			else {
				retorno = new Token(TipoToken.Delim, ":");
				controle = 1;
			}
			break;
		case 58:
			retorno = new Token(TipoToken.Atrib, ":=");
			controle = 1;

			break;
		case 59:
			retorno = new Token(TipoToken.AbrePar, "(");
			controle = 1;

			break;
		case 60:
			retorno = new Token(TipoToken.FechaPar, ")");
			controle = 1;

			break;
		case 61:
			if(caro == '0' ||caro == '1' ||caro == '2' ||caro == '3' ||caro == '4' ||caro == '5' ||caro == '6' ||caro == '7' ||caro == '8' ||caro == '9') {
				string = string + caro;
				estado = 61;
			}
			else if(caro == '.') {
				string = string + caro;
				estado = 62;
			}
			else {
				retorno = new Token(TipoToken.NumInt, string);
				controle = 1;
				string = "";
			}

			break;
		case 62:
			if(caro == '0' ||caro == '1' ||caro == '2' ||caro == '3' ||caro == '4' ||caro == '5' ||caro == '6' ||caro == '7' ||caro == '8' ||caro == '8') {
				string = string + caro;
				estado = 62;
				
			}
			else if(caro == '.'){ // caso haja dois pontos seguidos, retira o ponto da string
				if(string.indexOf('.') == (string.length() - 1)) {
					string = string.substring(0, string.length() - 1);
				}
				retorno = new Token(TipoToken.NumReal, string);
				controle = 1;
				string = "";
			}else {
				retorno = new Token(TipoToken.NumReal, string);
				controle = 1;
				string = "";
			}
			
			break;
		case 63:
			if(caro == '\n') {
				retorno = new Token(TipoToken.Var, string);
				controle = 1;
				string = "";
				
			}else if(Character.isLetter(caro) || Character.isDigit(caro)) {
				estado = 63;
				string = string + caro;
			}
			else {
				retorno = new Token(TipoToken.Var, string);
				controle = 1;
				string = "";
			}

			break;
		case 64:
			if(caro != '"') {
				string = string + caro;
				estado = 64;
			}
			
			else estado = 65;
			break;
		case 65:
			retorno = new Token(TipoToken.Cadeia, string);
			controle = 1;
			string = "";
			break;
		
		}
		
		if(controle == 1) {
			System.out.println(retorno.toString());
			controle = 0;
			estado = 1;
			return retorno;
			
		}
		if(controle == 2) {
			System.out.println("================>>>>> "+ erro + " <<<<<=================");
			controle = 0;
			estado = 1;
			return null;
		}
		else return null;
		
	}
	
}
