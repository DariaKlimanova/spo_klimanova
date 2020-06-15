import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

import lang.exception.LangLexException;
import lang.exception.LangParseException;
import lang.exception.VarTableException;
import lang.lexer.Lexer;
import lang.parser.Parser;
import lang.rpn.RPN;
import lang.token.Token;
import lang.token.TokenType;
import vm.VM;

public class Main {



        public static void main(String[] args) throws LangParseException, LangLexException, IOException, VarTableException {
            System.out.println("Start lang UI");
            Scanner in = new Scanner(System.in);
            System.out.print("Input a path to source: ");
            String program = readProgram(in.nextLine());

            List<Token> tokens = Lexer.tokenize(program);
  /*            int i = 0;
                for (Token token : tokens) {
                System.out.println(i+"\t"+token.getType()+"\t"+token.getValue());
                i++;
            }*/

 Parser.parse(tokens);

            tokens = RPN.getRPN(tokens);
            tokens.add(new Token(TokenType.EOF,"\0"));
           int i = 0;
            for (Token token : tokens) {
                System.out.println(i+"\t"+token.getType()+"\t"+token.getValue());
                i++;

            }
            VM.init();
            VM.loadProgram(tokens);
            VM.run();

            in.close();
        }


    private static String readProgram(String path) throws IOException {
        String value = "";
        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);
        while(scan.hasNextLine()){
            value += scan.nextLine();
        }
        scan.close();
        fr.close();
        return value;
    }
}
