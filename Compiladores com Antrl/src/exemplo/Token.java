package exemplo;

public class Token {

	
		public TipoToken sigla; //sigla do token
		public String lexema; // lexema do token
		public int linha;  // linha do token no arquivo
		
		public Token(TipoToken sigla, String lexema) {
			this.sigla = sigla;
			this.lexema = lexema;
			
		}
		
		@Override
		public String toString() { // retorna o token formatado
			return "<"+sigla +",'" + lexema+"'>"; 
			
		}
		
	

}
