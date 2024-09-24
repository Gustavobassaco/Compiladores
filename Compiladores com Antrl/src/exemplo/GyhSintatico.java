package exemplo;

import java.util.List;

public class GyhSintatico {
	
	public int i=0;
	List<Token> tokens;
	
	public GyhSintatico(List<Token> lex) {
		tokens = lex;
		System.out.println("\n ---  Analisador Sintático  --- \n");
	    programa();
	}

	// retorna o elemento  i + k - 1 da lista de tokens
	Token lookahead(int k) {
		if( i >= tokens.size()) {
			return null;
		}
		else if(i + k - 1 >= tokens.size()) {
			return tokens.get(tokens.size() - 1);
		}
		
		else return tokens.get(i + k - 1);
	}
	
	
	void match(TipoToken sigla) {
		if(lookahead(1).sigla == sigla) {
			System.out.println("Lido  " + lookahead(1).lexema +"   Match   " + lookahead(1).sigla);
			
		}else {
			erroSintatico(sigla.toString());
		}
		i++;
		if(i >= tokens.size()) {
			System.out.println("-- Arquivo Sintaticamente Correto --\n");
		}
	}
	
	void erroSintatico(String... toks) {
		String mensagem = "\n\nErro sintático linha " + tokens.get(i).linha + ": esperando um dos seguintes (";
		for(int j = 0; j < toks.length ; j++ ) {
			mensagem += toks[j];
			if(j < toks.length - 1) {
				mensagem += ',';
			}
		}
		mensagem += "), mas foi encontrado (" + lookahead(1).sigla + ").\n";
		throw new RuntimeException(mensagem);
	}
	
//			Programa → ':' 'DEC' ListaDeclaracoes ':' 'PROG' ListaComandos;
		public void programa() {
			match(TipoToken.Delim);
			match(TipoToken.PCDec);
			ListaDeclaracoes();
			match(TipoToken.Delim);
			match(TipoToken.PCProg);
			ListaComandos();
		}
		
//			ListaDeclaracoes → Declaracao AuxListaDec
		void ListaDeclaracoes() {
			Declaracao();
			AuxListaDec();
		}
		
//			AuxListaDec-> ListaDeclaracoes | vazio
		void AuxListaDec() {
			if(lookahead(1).sigla == TipoToken.Var)
				ListaDeclaracoes();
			else {
				// vazio
			}
		}
//			Declaracao → VARIAVEL ':' TipoVar;
		void Declaracao() {
			match(TipoToken.Var);
			match(TipoToken.Delim);
			TipoVar();
		}
//			TipoVar → 'INT' | 'REAL';
		void TipoVar() {
			if(lookahead(1).sigla == TipoToken.PCInt) {
				match(TipoToken.PCInt);
			}else if(lookahead(1).sigla == TipoToken.PCReal) {
				match(TipoToken.PCReal);
			}else {
				erroSintatico("INT", "REAL");
			}
		}
//			ExpressaoAritmetica → TermoAritmetico ExpAritimetica;
		void ExpressaoAritimetica() {
			TermoAritimetico();
			ExpAritimetica();
		}
//			ExpAritimetica → '+' TermoAritmetico ExpAritimetica | '-' TermoAritmetico ExpAritimetica | λ;
		void ExpAritimetica() {
			if(lookahead(1).sigla == TipoToken.OpAritSoma) {
				match(TipoToken.OpAritSoma);
				TermoAritimetico();
				ExpAritimetica();	
			}else if(lookahead(1).sigla == TipoToken.OpAritSub) {
				match(TipoToken.OpAritSub);
				TermoAritimetico();
				ExpAritimetica();
			}else {
				//vazio
			}
			
		}
//			TermoAritmetico → FatorAritmetico TerAritmetico;
		void TermoAritimetico(){
			FatorAritimetico();
			TerAritimetico();
		}
//			TerAritmetico → '*' FatorAritmetico TerAritmetico | '/' FatorAritmetico TerAritmetico | λ;
		void TerAritimetico() {
			if(lookahead(1).sigla == TipoToken.OpAritMult) {
				match(TipoToken.OpAritMult);
				TermoAritimetico();
				ExpAritimetica();	
			}else if(lookahead(1).sigla == TipoToken.OpAritDiv) {
				match(TipoToken.OpAritDiv);
				TermoAritimetico();
				ExpAritimetica();
			}else {
				//vazio
			}
		}
//			FatorAritmetico → NUMINT| NUMREAL | VARIAVEL | '(' ExpressaoAritmetica ')'
		void FatorAritimetico(){
			if(lookahead(1).sigla == TipoToken.NumInt) {
				match(TipoToken.NumInt);

			}else if(lookahead(1).sigla == TipoToken.NumReal) {
				match(TipoToken.NumReal);

			} else if(lookahead(1).sigla == TipoToken.Var) {
				match(TipoToken.Var);

			} else if(lookahead(1).sigla == TipoToken.AbrePar){
				match(TipoToken.AbrePar);
				ExpressaoAritimetica();
				match(TipoToken.FechaPar);
			}else {
				erroSintatico("NUMINT" , "NUMREAL", "VARIAVEL",  "AbrePar");
			}
		}
//			ExpressaoRelacional → TermoRelacional ExpRelacional;
		void ExpressaoRelacional() {
			TermoRelacional();
			ExpRelacional();
		}
//			ExpRelacional → OperadorBooleano TermoRelacional ExpRelacional | λ;
		void ExpRelacional(){
			if(lookahead(1).sigla == TipoToken.OpBoolE || lookahead(1).sigla == TipoToken.OpBoolOu ) {
				OperadorBooleano();
				TermoRelacional();
				ExpRelacional();
			}else {
				//vazio
			}

		}
//			TermoRelacional → ExpressaoAritmetica OP_REL ExpressaoAritmetica | '(' ExpressaoRelacional ')';
		void TermoRelacional() {
			if(lookahead(1).sigla == TipoToken.NumInt || lookahead(1).sigla == TipoToken.NumReal ||
					lookahead(1).sigla == TipoToken.Var || (lookahead(i).sigla == TipoToken.AbrePar &&
					(lookahead(2).sigla == TipoToken.NumInt || lookahead(2).sigla == TipoToken.NumReal ||
					lookahead(2).sigla == TipoToken.Var ) )) {
				ExpressaoAritimetica();
				OpRelacional();
				ExpressaoAritimetica();
			}else if(lookahead(1).sigla == TipoToken.AbrePar) {
				match(TipoToken.AbrePar);
				ExpressaoRelacional();
				match(TipoToken.FechaPar);
			} else {
				erroSintatico("NUMINT" , "NUMREAL", "VARIAVEL",  "AbrePar");
			}
		}
		
// 		Operadores ralacionais
		void OpRelacional() {
			if(lookahead(1).sigla == TipoToken.OpRelDif) {
				match(TipoToken.OpRelDif);
			}else if(lookahead(1).sigla == TipoToken.OpRelIgual) {
				match(TipoToken.OpRelIgual);
			}else if(lookahead(1).sigla == TipoToken.OpRelMaior) {
				match(TipoToken.OpRelMaior);
			}else if(lookahead(1).sigla == TipoToken.OpRelMaiorIgual) {
				match(TipoToken.OpRelMaiorIgual);
			}else if(lookahead(1).sigla == TipoToken.OpRelMenor) {
				match(TipoToken.OpRelMenor);
			}else if(lookahead(1).sigla == TipoToken.OpRelMenorIgual) {
				match(TipoToken.OpRelMenorIgual);
			} else {
				erroSintatico("OpRelMenor", "OpRelMenorIgual", "OpRelMaior", "OpRelMaiorIgual", "OpRelIgual", "OpRelDif");
			}
		}

//			OperadorBooleano → 'E' | 'OU';
		void OperadorBooleano() {
			if(lookahead(1).sigla == TipoToken.OpBoolE  ) {
				match(TipoToken.OpBoolE);
				
			}else if(lookahead(1).sigla == TipoToken.OpBoolOu  ) {
				match(TipoToken.OpBoolOu);
			}else {
				erroSintatico("OpBoolE", "OpBoolOu");
			}
		}
//			ListaComandos → Comando AuxListaCom;
		void ListaComandos() {
			Comando();
			AuxListaCom();
			
		}
//			AuxListaCom -> ListaComandos | vazio
		void AuxListaCom() {
			if(lookahead(1) != null && lookahead(2) != null) // para garantir que o valor n seja null
				if(lookahead(2).sigla == TipoToken.Atrib || lookahead(1).sigla == TipoToken.PCLer ||
					lookahead(1).sigla == TipoToken.PCImprimir || lookahead(1).sigla == TipoToken.PCSe ||
					lookahead(1).sigla == TipoToken.PCEnqto || lookahead(1).sigla == TipoToken.PCIni) {
			ListaComandos();	
			}else {
				//vazio
			}
			
		}
//			Comando → ComandoAtribuicao | ComandoEntrada | ComandoSaida | ComandoCondicao
//			| ComandoRepeticao | SubAlgoritmo;
		void Comando() {
			if(lookahead(2).sigla == TipoToken.Atrib  ) {
				ComandoAtribuicao();
			}else if(lookahead(1).sigla == TipoToken.PCLer  ) {
				ComandoEntrada();
			}else if(lookahead(1).sigla == TipoToken.PCImprimir  ) {
				ComandoSaida();
			}else if(lookahead(1).sigla == TipoToken.PCSe  ) {
				ComandoCondicao();
			}else if(lookahead(1).sigla == TipoToken.PCEnqto  ) {
				ComandoRepeticao();
			}else if(lookahead(1).sigla == TipoToken.PCIni  ) {
				SubAlgoritmo();
			}else {

			}
		}
//			ComandoAtribuicao → VARIAVEL ':=' ExpressaoAritmetica;
		void ComandoAtribuicao() {
			match(TipoToken.Var);
			match(TipoToken.Atrib);
			ExpressaoAritimetica();
		}
//			ComandoEntrada → 'LER' VARIAVEL;
		void ComandoEntrada() {
			match(TipoToken.PCLer);
			match(TipoToken.Var);
		}
//			ComandoSaida → 'IMPRIMIR' ComanSaida;
		void ComandoSaida() {
			match(TipoToken.PCImprimir);
			ComanSaida();
		}
//			ComanSaida → VARIAVEL | CADEIA;
		void ComanSaida() {
			if(lookahead(1).sigla == TipoToken.Var) {
				match(TipoToken.Var);
			}else if(lookahead(1).sigla == TipoToken.Cadeia) {
				match(TipoToken.Cadeia);
			}else {
				erroSintatico("VARIAVEL", "CADEIA");
			}
			
		}
//			ComandoCondicao → 'SE' ExpressaoRelacional 'ENTAO' Comando ComanCondicao;
		void ComandoCondicao() {
			match(TipoToken.PCSe);
			ExpressaoRelacional();
			match(TipoToken.PCEntao);
			Comando();
			ComanCondicao();
		}
//			ComanCondicao → 'SENAO' Comando | vazio;
		void ComanCondicao() {
			if(lookahead(1).sigla == TipoToken.PCSenao) {
				match(TipoToken.PCSenao);
				Comando();
			}else {
				//vazio
			}
		}
//			ComandoRepeticao → 'ENQTO' ExpressaoRelacional Comando;
		void ComandoRepeticao() {
			match(TipoToken.PCEnqto);
			ExpressaoRelacional();
			Comando();
		}
//			SubAlgoritmo → 'INI' ListaComandos 'FIM';
		void SubAlgoritmo() {
			match(TipoToken.PCIni);
			ListaComandos();
			match(TipoToken.PCFim);
		}

}
