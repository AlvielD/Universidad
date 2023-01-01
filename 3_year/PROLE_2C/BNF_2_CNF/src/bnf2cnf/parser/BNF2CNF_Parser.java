/* Generated By:JavaCC: Do not edit this line. BNF2CNF_Parser.java */
                             /* definici�n del nombre del analizador */

/* codigo Java utilizado en la descripci�n del analizador */

package bnf2cnf.parser;

import java.io.*;
import java.util.ArrayList;

import bnf2cnf.ast.Symbols.*;
import bnf2cnf.ast.*;

public class BNF2CNF_Parser implements BNF2CNF_ParserConstants {

        //----------------------------------------------------------------//
        //                        Miembros privados                       //
        //----------------------------------------------------------------//
        /**
	 * Contador de errores
	 */
        private int errorCount;

        /**
	 * Mensaje de errores
	 */
        private String errorMsg;

        //----------------------------------------------------------------//
        //       M�todos relacionados con el tratamiento de errores       //
        //----------------------------------------------------------------//
        /**
	 * Analiza un fichero ".bnc" a�adiendo la informaci�n del cuerpo de los m�todos
	 */
        public void BNF2CNF_Parser()
        {
            this.errorCount = 0;
            this.errorMsg = "";
        }
        /**
	 * Obtiene el n�mero de errores del an�lisis
	 * @return
	 */
        public int getErrorCount()
        {
                return this.errorCount;
        }

        /**
	 * Obtiene el mensaje de error del an�lisis
	 * @return
	 */
        public String getErrorMsg()
        {
                return this.errorMsg;
        }

        /**
	 * Almacena un error de an�lisis
	 * @param ex
	 */
        private void catchError(Exception ex)
        {
                this.errorCount++;
                this.errorMsg += ex.toString();
        }

/* ESPECIFICACI�N SINT�CTICA DE LA GRAM�TICA */
  final public Gramatica Gramatica() throws ParseException {
  int[] lsync = { NOTERMINAL, TERMINAL, SEMICOLON, BAR, EQ };
  int[] rsync = { EOF };
  Gramatica G;
  Definicion D = null;
  ArrayList<Definicion> Definiciones = new ArrayList<Definicion>();
    try {
      label_1:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NOTERMINAL:
          ;
          break;
        default:
          jj_la1[0] = jj_gen;
          break label_1;
        }
        D = Definicion();
                             Definiciones.add(D);
      }
      jj_consume_token(0);
    } catch (Exception ex) {
                catchError(ex);
                skipTo(lsync,rsync);
    }
      G = new Gramatica(Definiciones);
      {if (true) return G;}
    throw new Error("Missing return statement in function");
  }

  final public Definicion Definicion() throws ParseException {
  int[] lsync = { SEMICOLON };
  int[] rsync = { };
  Definicion D;
  Token tk;
  NTSymbol NT = null;
  ArrayList<Regla> LR = new ArrayList<Regla>();
    try {
      tk = jj_consume_token(NOTERMINAL);
                                    NT = new NTSymbol(tk.image);
      jj_consume_token(EQ);
      ListaReglas(LR);
      jj_consume_token(SEMICOLON);
    } catch (Exception ex) {
                catchError(ex);
        skipTo(lsync,rsync);
    }
            D = new Definicion(NT, LR);
            {if (true) return D;}
    throw new Error("Missing return statement in function");
  }

  final public void ListaReglas(ArrayList<Regla> LR) throws ParseException {
  int[] lsync = { };
  int[] rsync = { SEMICOLON };
  Regla R = null;
    try {
      R = Regla(LR);
      label_2:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case BAR:
          ;
          break;
        default:
          jj_la1[1] = jj_gen;
          break label_2;
        }
        jj_consume_token(BAR);
        R = Regla(LR);
      }
    } catch (Exception ex) {
            catchError(ex);
        skipTo(lsync,rsync);
    }
  }

  final public Regla Regla(ArrayList<Regla> LR) throws ParseException {
        int[] lsync = { };
        int[] rsync = { BAR, SEMICOLON };
        Token tk = null;
        Symbol S;
        Regla R;
        ArrayList<Symbol> Simbolos = new ArrayList<Symbol>();
    try {
      label_3:
      while (true) {
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NOTERMINAL:
        case TERMINAL:
          ;
          break;
        default:
          jj_la1[2] = jj_gen;
          break label_3;
        }
        switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
        case NOTERMINAL:
          tk = jj_consume_token(NOTERMINAL);
                                  S = new NTSymbol(tk.image);   Simbolos.add(S);
          break;
        case TERMINAL:
          tk = jj_consume_token(TERMINAL);
                                          S = new TSymbol(tk.image);    Simbolos.add(S);
          break;
        default:
          jj_la1[3] = jj_gen;
          jj_consume_token(-1);
          throw new ParseException();
        }
      }
    } catch (Exception ex) {
            catchError(ex);
        skipTo(lsync,rsync);
    }
        R = new Regla(Simbolos);
        LR.add(R);
                {if (true) return R;}
    throw new Error("Missing return statement in function");
  }

  void skipTo(int[] left, int[] right) throws ParseException {
  Token prev = getToken(0);
  Token next = getToken(1);
  boolean flag = false;
  if(prev.kind == EOF || next.kind == EOF) flag = true;
  for(int i=0; i<left.length; i++) if(prev.kind == left[i]) flag = true;
  for(int i=0; i<right.length; i++) if(next.kind == right[i]) flag = true;

  while(!flag)
  {
    getNextToken();
    prev = getToken(0);
    next = getToken(1);
    if(prev.kind == EOF || next.kind == EOF) flag = true;
    for(int i=0; i<left.length; i++) if(prev.kind == left[i]) flag = true;
    for(int i=0; i<right.length; i++) if(next.kind == right[i]) flag = true;
  }
  }

  /** Generated Token Manager. */
  public BNF2CNF_ParserTokenManager token_source;
  SimpleCharStream jj_input_stream;
  /** Current token. */
  public Token token;
  /** Next token. */
  public Token jj_nt;
  private int jj_ntk;
  private int jj_gen;
  final private int[] jj_la1 = new int[4];
  static private int[] jj_la1_0;
  static {
      jj_la1_init_0();
   }
   private static void jj_la1_init_0() {
      jj_la1_0 = new int[] {0x40,0x200,0xc0,0xc0,};
   }

  /** Constructor with InputStream. */
  public BNF2CNF_Parser(java.io.InputStream stream) {
     this(stream, null);
  }
  /** Constructor with InputStream and supplied encoding */
  public BNF2CNF_Parser(java.io.InputStream stream, String encoding) {
    try { jj_input_stream = new SimpleCharStream(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source = new BNF2CNF_ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream) {
     ReInit(stream, null);
  }
  /** Reinitialise. */
  public void ReInit(java.io.InputStream stream, String encoding) {
    try { jj_input_stream.ReInit(stream, encoding, 1, 1); } catch(java.io.UnsupportedEncodingException e) { throw new RuntimeException(e); }
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor. */
  public BNF2CNF_Parser(java.io.Reader stream) {
    jj_input_stream = new SimpleCharStream(stream, 1, 1);
    token_source = new BNF2CNF_ParserTokenManager(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(java.io.Reader stream) {
    jj_input_stream.ReInit(stream, 1, 1);
    token_source.ReInit(jj_input_stream);
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Constructor with generated Token Manager. */
  public BNF2CNF_Parser(BNF2CNF_ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  /** Reinitialise. */
  public void ReInit(BNF2CNF_ParserTokenManager tm) {
    token_source = tm;
    token = new Token();
    jj_ntk = -1;
    jj_gen = 0;
    for (int i = 0; i < 4; i++) jj_la1[i] = -1;
  }

  private Token jj_consume_token(int kind) throws ParseException {
    Token oldToken;
    if ((oldToken = token).next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    if (token.kind == kind) {
      jj_gen++;
      return token;
    }
    token = oldToken;
    jj_kind = kind;
    throw generateParseException();
  }


/** Get the next Token. */
  final public Token getNextToken() {
    if (token.next != null) token = token.next;
    else token = token.next = token_source.getNextToken();
    jj_ntk = -1;
    jj_gen++;
    return token;
  }

/** Get the specific Token. */
  final public Token getToken(int index) {
    Token t = token;
    for (int i = 0; i < index; i++) {
      if (t.next != null) t = t.next;
      else t = t.next = token_source.getNextToken();
    }
    return t;
  }

  private int jj_ntk() {
    if ((jj_nt=token.next) == null)
      return (jj_ntk = (token.next=token_source.getNextToken()).kind);
    else
      return (jj_ntk = jj_nt.kind);
  }

  private java.util.List<int[]> jj_expentries = new java.util.ArrayList<int[]>();
  private int[] jj_expentry;
  private int jj_kind = -1;

  /** Generate ParseException. */
  public ParseException generateParseException() {
    jj_expentries.clear();
    boolean[] la1tokens = new boolean[11];
    if (jj_kind >= 0) {
      la1tokens[jj_kind] = true;
      jj_kind = -1;
    }
    for (int i = 0; i < 4; i++) {
      if (jj_la1[i] == jj_gen) {
        for (int j = 0; j < 32; j++) {
          if ((jj_la1_0[i] & (1<<j)) != 0) {
            la1tokens[j] = true;
          }
        }
      }
    }
    for (int i = 0; i < 11; i++) {
      if (la1tokens[i]) {
        jj_expentry = new int[1];
        jj_expentry[0] = i;
        jj_expentries.add(jj_expentry);
      }
    }
    int[][] exptokseq = new int[jj_expentries.size()][];
    for (int i = 0; i < jj_expentries.size(); i++) {
      exptokseq[i] = jj_expentries.get(i);
    }
    return new ParseException(token, exptokseq, tokenImage);
  }

  /** Enable tracing. */
  final public void enable_tracing() {
  }

  /** Disable tracing. */
  final public void disable_tracing() {
  }

}