package lang.token;

import java.util.regex.Pattern;

public enum TokenType {

    //Список токенов
    //Зарезервированные системой слова

    NEWLIST("linklist"),
    ADDlAST("addl"),
    ADDFIRST("addf"),
    REMOVElAST("reml"),
    REMOVEFIRST("remf"),
    GETlAST("getl"),
    GETFIRST("getf"),
    NAME("%\\w+%"),
    METHOD("com"),
    PRINTLIST("printlist"),
    
    ADDELEM("addelem"),
    NEWHASH("newhash"),
    FINDHASH("findhash"),
    PRINTHASH("printhash"),

    INTEGER("int"),
    DOUBLE("double"),
    STRING("string"),
    BOOL("boolean"),
    VAR("var"),
    PRINTLINE("println"),
    BREAK("break"),
    EXIT("exit"),
    IF("if"),
    ELSE("else"),
    WHILE("while"),
    AND("&&"),
    OR("|"),
    //Возможные значения данных
    BOOLEAN("true|false"),
    STR("'.*'"),
    NUM("0|([1-9][0-9]*)"),
    //Символы
    LEFT_BRACE("\\{"),
    RIGHT_BRACE("\\}"),
    LEFT_BRACKET("\\("),
    RIGHT_BRACKET("\\)"),
    COMMA(","),
    SEMICOLON(";"),
    DOT("\\."),
    //Символы операций
    ASSIGNMENT("="),
    EQUAL("=="),
    NOTEQUAL("!="),
    MOEQ(">="),
    MORE(">"),
    LOEC("<="),
    LESS("<"),
    SUB("-"),
    ADD("\\+"),
    SLASH("/"),
    STAR("\\*"),
    //Идентификатор
    IDENTIFIER("[A-Za-z_]+"),
    //Пробельные символы, а ткаже символ конца файла
    SPACE(" |\t"),
    EOF("\0"),

    //Токены, используемые программой


    ADDRESS("0|([1-9][0-9]*)"),
    SYS_CALL("call"),
    POPFROMRETURNSTACK("R>"),
    PUSHTORETURNSTACK(">R"),
    DROP("DROP"),
    ENDSCOPE("endscope");


    private Pattern pattern;

    TokenType(String regexp) {
        this.pattern = Pattern.compile(regexp);
    }

    public static TokenType searchType(String value){
        for (TokenType tokenvalue : TokenType.values()) {
            if(tokenvalue.pattern.matcher(value).matches()){
                return tokenvalue;
            }
        }
        return null;
    }
}