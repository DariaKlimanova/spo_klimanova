package lang.parser;

import java.util.List;

import lang.exception.LangParseException;
import lang.token.Token;
import lang.token.TokenType;

public class Parser{

    private static int pos;

    private static List<Token> tokens;




    //program                -> do_block EOF
    //do_block               -> (LEFT_BRACE term RIGHT_BRACE)
    //term                   -> (IF if_description term)|
    //                          (WHILE while_description term)|
    //                          (IDENTIFIER ASSIGNMENT expression SEMICOLON term)|
    //                          (PRINTLINE println_operand term)|
    //                          (NEWLIST listname SEMICOLON term)|
    //                          (listmethod SEMICOLON term)|
    //                          empty
    //listmethod             -> addlast|addfirst|getlast|getfirst|removelast|removefirst
    
    //if_description         -> LEFT_BRACKET expression RIGHT_BRACKET do_block else_if_description
    //else_if_description    -> (ELSE (IF if_description)|(do_block))|empty
    //while_description      -> LEFT_BRACKET expression RIGHT_BRACKET do_block
    //println_operand        -> LEFT_BRACKET expression RIGHT_BRACKET SEMICOLON
    //expression             -> operand operator_operand
    //operator_operand       -> (operator operand operator_operand)|empty
    //operator               -> RALPH_AND|OR|PLUS|MINUS|STAR|SLASH|EQUAL|GT|GE|LT|LE|NOTEQUAL
    //operand                -> (NUMERIC double_part)|CHARACTER_STRING|BOOLEAN|
    //                          (IDENTIFIER)|
    //                          (LEFT_BRACKET expression RIGHT_BRACKET)
    //add_last                  ADDLAST name SEMICOLON
    //add_first                 ADDFIRST name SEMICOLON
    //double_part            -> (DOT NUMERIC)|empty

    public static void parse(List<Token> tokenlist) throws LangParseException {
        tokens = tokenlist;
        pos = 0;
        program();
    }

 

    private static void program() throws LangParseException{

        do_block();
        matchToken(getToken(), TokenType.EOF);
    }


    private static void do_block() throws LangParseException {

        matchToken(getToken(), TokenType.LEFT_BRACE);
        term();
        matchToken(getToken(), TokenType.RIGHT_BRACE);
    }

    private static void term() throws LangParseException {
        try{
            matchToken(getToken(), TokenType.IF);
        }catch(LangParseException not_if){
            try{
                matchToken(getToken(), TokenType.WHILE);
            }catch(LangParseException not_while){
                try{
                    matchToken(getToken(), TokenType.NEWLIST);
                }catch(LangParseException not_identifier){
                    try{
                        matchToken(getToken(), TokenType.IDENTIFIER);
                    }catch(LangParseException not_println){
                        try{
                            matchToken(getToken(), TokenType.PRINTLINE);
                        }catch (LangParseException not_list){
                            try{
                                matchToken(getToken(), TokenType.ADDlAST);
                            }catch (LangParseException not_addl) {
                                try{
                                    matchToken(getToken(), TokenType.ADDFIRST);
                                }catch (LangParseException not_addf){
                                    try{
                                        matchToken(getToken(), TokenType.REMOVElAST);
                                    }catch (LangParseException not_reml){
                                        try{
                                            matchToken(getToken(), TokenType.REMOVEFIRST);
                                        }catch (LangParseException not_remf){
                                            try{
                                                matchToken(getToken(), TokenType.GETlAST);
                                            }catch (LangParseException getl){
                                                try{
                                                    matchToken(getToken(), TokenType.GETFIRST);
                                                }catch (LangParseException getf){
                                                     try{
                                                     matchToken(getToken(), TokenType.PRINTLIST);
                                                     } catch(LangParseException not_printlist){
                                                         try{
                                                     
                                                         matchToken(getToken(), TokenType.ADDELEM);
                                                         }catch(LangParseException addelem){
                                                             try{
                                                              matchToken(getToken(), TokenType.NEWHASH);   
                                                              }catch(LangParseException newhash){
                                                                
                                                                 try{
                                                                 matchToken(getToken(), TokenType.FINDHASH);
                                                                 }catch(LangParseException not_find){
                                                                     try {
                                                                         matchToken(getToken(), TokenType.PRINTHASH);
                                                                     }catch(LangParseException not_print)
                                                                     {
                                                                         return;
                                                                     }
                                                                 name();
                                                                 matchToken(getToken(), TokenType.SEMICOLON);
                                                                 term();
                                                                 return;
                                                                 }
                                                                 name();
                                                                 operand();
                                                                 matchToken(getToken(), TokenType.SEMICOLON);
                                                                 term();
                                                                 return;
                                                             }
                                                             name();
                                                             matchToken(getToken(), TokenType.SEMICOLON);
                                                             term();
                                                             return;
                                                         }
                                                            
                                                         name();
                                                         operand();
                                                         matchToken(getToken(), TokenType.SEMICOLON);
                                                         term();
                                                         return;
                                                     }
                                                    name();
                                                    matchToken(getToken(), TokenType.SEMICOLON);
                                                    term();
                                                    return;
                                                }
                                                name();
                                                operand();
                                                matchToken(getToken(), TokenType.SEMICOLON);
                                                term();
                                                return;
                                            }
                                            name();
                                            operand();
                                            matchToken(getToken(), TokenType.SEMICOLON);
                                            term();
                                            return;
                                        }
                                        name();
                                        matchToken(getToken(), TokenType.SEMICOLON);
                                        term();
                                        return;
                                    }
                                    name();
                                    matchToken(getToken(), TokenType.SEMICOLON);
                                    term();
                                    return;
                                }
                                name();
                                operand();
                                matchToken(getToken(), TokenType.SEMICOLON);
                                term();
                                return;
                            }
                            name();
                            operand();
                            matchToken(getToken(), TokenType.SEMICOLON);
                            term();
                            return;
                        }
                        println_operand();
                        term();
                        return;
                    }
                    matchToken(getToken(), TokenType.ASSIGNMENT);
                    expression();
                    matchToken(getToken(), TokenType.SEMICOLON);
                    term();
                    return;
                }
                name();
                matchToken(getToken(), TokenType.SEMICOLON);
                term();
                return;
            }
            while_description();
            term();
            return;
        }
        if_description();
        term();
        return;
    }

    private static void if_description() throws LangParseException {
        matchToken(getToken(), TokenType.LEFT_BRACKET);
        expression();
        matchToken(getToken(), TokenType.RIGHT_BRACKET);
        do_block();
        else_if_description();
    }

    private static void else_if_description() throws LangParseException {
        try{
            matchToken(getToken(), TokenType.ELSE);
        }catch(LangParseException not_else){
            return;
        }
        try{
            matchToken(getToken(), TokenType.IF);
        }catch(LangParseException not_if){
            do_block();
            return;
        }
        if_description();
    }

    private static void while_description() throws LangParseException {
        matchToken(getToken(), TokenType.LEFT_BRACKET);
        expression();
        matchToken(getToken(), TokenType.RIGHT_BRACKET);
        do_block();
    }

    private static void println_operand() throws LangParseException {
        matchToken(getToken(), TokenType.LEFT_BRACKET);
        expression();
        matchToken(getToken(), TokenType.RIGHT_BRACKET);
        matchToken(getToken(), TokenType.SEMICOLON);
    }

    private static void expression() throws LangParseException {
        operand();
        operator_operand();
    }

    private static void operator_operand() throws LangParseException {
        try{
            operator();
        }catch(LangParseException not_operator){
            return;
        }
        operand();
        operator_operand();
    }

    private static void operator() throws LangParseException {
        try{
            matchToken(getToken(), TokenType.AND);
        }catch(LangParseException not_and){
            try{
                matchToken(getToken(), TokenType.OR);
            }catch(LangParseException not_or){
                try{
                    matchToken(getToken(), TokenType.ADD);
                }catch(LangParseException not_plus){
                    try{
                        matchToken(getToken(), TokenType.SUB);
                    }catch(LangParseException not_minus){
                        try{
                            matchToken(getToken(), TokenType.STAR);
                        }catch(LangParseException not_star){
                            try{
                                matchToken(getToken(), TokenType.SLASH);
                            }catch(LangParseException not_slash){
                                try{
                                    matchToken(getToken(), TokenType.EQUAL);
                                }catch(LangParseException not_equal){
                                    try{
                                        matchToken(getToken(), TokenType.MORE);
                                    }catch(LangParseException not_gt){
                                        try{
                                            matchToken(getToken(), TokenType.MOEQ);
                                        }catch(LangParseException not_ge){
                                            try{
                                                matchToken(getToken(), TokenType.LESS);
                                            }catch(LangParseException not_lt){
                                                try{
                                                    matchToken(getToken(), TokenType.LOEC);
                                                }catch(LangParseException not_le){
                                                    matchToken(getToken(), TokenType.NOTEQUAL);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private static void  name() throws LangParseException{
        try {
            matchToken(getToken(), TokenType.NAME);
        }catch (LangParseException not_name){
            return;
        }
    }
    private static void operand() throws LangParseException {
        try{
            matchToken(getToken(), TokenType.NUM);
        }catch(LangParseException not_numeric){
            try{
                matchToken(getToken(), TokenType.STR);
            }catch(LangParseException not_character_string){
                try{
                    matchToken(getToken(), TokenType.BOOLEAN);
                }catch(LangParseException not_boolean){
                    try{
                        matchToken(getToken(), TokenType.IDENTIFIER);
                    }catch(LangParseException not_identifier){
                        matchToken(getToken(),TokenType.LEFT_BRACKET);
                        expression();
                        matchToken(getToken(),TokenType.RIGHT_BRACKET);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        double_part();
    }

    private static void double_part() throws LangParseException {
        try{
            matchToken(getToken(), TokenType.DOT);
        }catch(LangParseException not_dot){
            return;
        }
        pos--;
        tokens.remove(pos);
        matchToken(getToken(), TokenType.NUM);
        pos--;
        tokens.get(pos-1).setValue(tokens.get(pos-1).getValue()+"."+getToken().getValue());
        tokens.remove(pos);
    }

    private static void matchToken(Token token, TokenType type) throws LangParseException {
        if (!token.getType().equals(type)) {
            throw new LangParseException(type
                    + " expected but "
                    + token.getType().name() + ": " + token.getValue()
                    + " found");
        }
        nextToken();
    }

    private static Token getToken(){
        return tokens.get(pos); 
    }

    private static void nextToken(){
        pos++;
    }
    
}