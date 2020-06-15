package lang.rpn;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import lang.token.Token;
import lang.token.TokenType;


public class RPN {

    private static List<Token> tokens;

    private static int pos;

    private static boolean bracketFirst = true;
    private static int bracketStart;

    public static List<Token> getRPN(final List<Token> tokensIn){
        tokens = tokensIn;                                                                                              //берется список(1) токинов сформированный лексером
        List<Token> tokenList = new ArrayList<>();                                                                      //объявляется новый(2) список токенов
        Stack<Token> opStack = new Stack<>();                                                                           //объяляется стек(С) операций
        tokens.remove(pos);                                                                                             //удаляется л.ф.скоб. (первая)
        while(!getToken(pos).getType().equals(TokenType.RIGHT_BRACE)){                                                  //пока не найдена правая фиг. скобка
            if (getPriorityAndType(getToken(pos)) == -1){                                                               //если приоритет == -1
                operandfind(tokens.remove(pos),tokenList,opStack);                                                      //работа с операндом
            } else {                                                                                                    //иначе
                operationfind(tokens.remove(pos),tokenList,opStack);                                                    //вызов метода operatorfind
            }                                                                                                           //
        }                                                                                                               //
        tokens.remove(pos);                                                                                             //удалить токен по позиции
        pushFromStack(tokenList,opStack,0);                                                                  //вызов pushfromstack со стартом скобок = 0
        return tokenList;                                                                                               //возвращает готовый список(2) токенов
    }

    public static List<Token> getRPNBrackets(final List<Token> tokensIn){
        tokens = tokensIn;                                                                                              //берется список(1) токинов сформированный лексером
        List<Token> tokenList = new ArrayList<>();                                                                      //объявляется новый(2) список токенов
        Stack<Token> opStack = new Stack<>();                                                                           //объяляется стек(С) операций
        while(!getToken(pos).getType().equals(TokenType.RIGHT_BRACKET)){                                                //пока токен не является правой скобкой
            if (getPriorityAndType(getToken(pos)) == -1){                                                               //если приоритет токена на позиции заданной == -1
                operandfind(tokens.remove(pos),tokenList,opStack);                                                      //вызов метода operatorfind
            } else {                                                                                                    //иначе
                operationfind(tokens.remove(pos),tokenList,opStack);                                                    //вызов метода operationfind
            }
        }
        pushFromStack(tokenList,opStack,0);                                                                  //вызов метода pushfromstack
        return tokenList;                                                                                               //возвращает список токенов
    }

    private static void operandfind(final Token newtoken, List<Token> tokenList, Stack<Token> opStack){
        tokenList.add(newtoken);                                                                                        //добавляется операнд в новый список(2)
    }

    private static void operationfind(final Token newtoken, List<Token> tokenList, Stack<Token> opStack){
        if(newtoken.getType().equals(TokenType.RIGHT_BRACKET) || newtoken.getType().equals(TokenType.SEMICOLON)){       //если это правая скобка, или точказапятая
            pushFromStack(tokenList,opStack,bracketStart);                                                              //опустошить стек
        }else if(opStack.empty() || newtoken.getType().equals(TokenType.LEFT_BRACKET) || (getPriorityAndType(newtoken) > getPriorityAndType(opStack.peek()))){ //иначе если стек пуст, или токет==левой скобке,
                                                                                                                                                                //или приор токена > пр токена во глвае стека
            opStack.push(newtoken);                                                                                     //добавить токен в голову стека
            if(newtoken.getType().equals(TokenType.LEFT_BRACKET) && bracketFirst){                                      //если новый токен == левой скобке и это первая скобка
                bracketStart = tokenList.size();                                                                        //позиция скобки = размеру листа(2) токенов
            }
        }else{                                                                                                          //иначе
            do{                                                                                                         //выполнять
                tokenList.add(opStack.pop());                                                                           //выталкнуть голову стека в список(2) токенов
            }while(!opStack.empty() && (getPriorityAndType(newtoken) <= getPriorityAndType(opStack.peek())));           //пока стек не пуст и прироитет токена <= приоритета токена в голове стека
            opStack.push(newtoken);                                                                                     //добавить в голову стека токен
        }
    }

    private static void pushFromStack(List<Token> tokenList, Stack<Token> opStack, int bracketStart) {
        while(!opStack.empty() && !opStack.peek().getType().equals(TokenType.LEFT_BRACKET)){                            //пока стек не пуст и в голове стека не левая скобка
            tokenList.add(opStack.pop());                                                                               //вытолкнуть в список(2) токенов голову стека
        }
        if(!opStack.empty()){                                                                                           //если стек не пуст
            opStack.pop();                                                                                              //вытолкнуть голову
            if(!opStack.empty()){                                                                                       //если стек не пуст
                if(opStack.peek().getType().equals(TokenType.IF)){                                                      //если голова стека == IF
                    getIfRpn(tokenList,opStack);                                                                        //вызов обработки IF блока
                }else if(opStack.peek().getType().equals(TokenType.WHILE)){                                             //если while
                    getWhileRpn(tokenList,opStack, bracketStart);                                                       //вызов обработки while блока
                }
            }
        }
    }

    private static void getWhileRpn(List<Token> tokenList, Stack<Token> opStack, int bracketStart) {
        opStack.pop();                                                                                                  //Выталкиваем хранящееся в opStack слово while
        bracketFirst = false;
        tokenList.add(bracketStart, new Token(TokenType.NUM, "0"));                                               //Добавляем 0
        tokenList.add(bracketStart+1, new Token(TokenType.PUSHTORETURNSTACK, ">R"));                       //Заталкивает значение(0) из стека данных в стек возврата
        tokenList.add(new Token(TokenType.IF,"if"));                                                             //Добавляем слово if после условия
        tokenList.add(new Token(TokenType.ADDRESS, ""));                                                         //Добавляем адрес выхода из цикла на команду очистки стека возврата
        int posDump = tokenList.size()-1;                                                                               //Сохраняем адрес адреса перехода if
        tokenList.add(new Token(TokenType.POPFROMRETURNSTACK,"R>"));                                              //Выталкиваем значение из стека возврата в стек данных
        tokenList.add(new Token(TokenType.DROP,"DROP"));                                                          //Выталкиваем значение из стека данных
        tokenList.addAll(getRPN(tokens));                                                                               //Добавляем в выходной список полученную обратную польскую запись тела while
        tokenList.add(new Token(TokenType.SYS_CALL,"call"));                                                      //Осуществляем переход на начало условия if
        tokenList.add(new Token(TokenType.ADDRESS,Integer.toString(bracketStart+2)));                                //Добавляем адрес для этого if
        tokenList.get(posDump).setValue(Integer.toString(tokenList.size()));                                            //Устанавливаем значение адреса перехода из if
        tokenList.add(new Token(TokenType.POPFROMRETURNSTACK,"R>"));                                              //Выталкиваем значение из стека возврата в стек данных
        tokenList.add(new Token(TokenType.DROP,"DROP"));                                                          //Выталкиваем значение из стека данных
    }

    private static void getIfRpn(List<Token> tokenList, Stack<Token> opStack) {
        tokenList.add(opStack.pop());                                                                                   //выдавить в список(2) токенов голову стека
        tokenList.add(new Token(TokenType.NUM, ""));                                                              //добавить в список(2) токен с именем NUM пока нет значения
        int posDump = tokenList.size()-1;                                                                               //объявление позиции дампа, равной длине списка(2)-1
        tokenList.addAll(getRPN(tokens));                                                                               //добавить в список коллекцию, которую вернет метод getRPN
        if(getToken(pos).getType().equals(TokenType.ELSE)){                                                             //если токен == ELSE
            Token call = new Token(TokenType.SYS_CALL,"call");                                                    //создание нового токена SYS_CALL со значением call
            Token addressOut = new Token(TokenType.ADDRESS,"");                                                   //создание токена ADDRESS (пока без значения) - адрес выхода
            //
            while(getToken(pos).getType().equals(TokenType.ELSE)){                                                      //пока токен == ELSE
                pos++;                                                                                                  //позиция +1
                tokenList.add(call);                                                                                    //добавить токен call в список(2) токенов
                tokenList.add(addressOut);                                                                              //добавить токен addressOUt в список(2) токенов
                tokenList.get(posDump).setValue(Integer.toString(tokenList.size()));                                    //на позицию токена Дампа установить значение = длине списка(2)
                if(getToken(pos).getType().equals(TokenType.IF)){                                                       //если тип токена == IF
                    pos++;                                                                                              //позиция +1
                    tokenList.addAll(getRPNBrackets(tokens));                                                           //добавить коллекцию в список(2) токенов, которую вернет метод getRPNBrackets
                    tokenList.add(new Token(TokenType.IF,"if"));                                                  //добавить токен IF со значением if в список(2) токенов
                    tokenList.add(new Token(TokenType.NUM, ""));                                                  //добавить токен NUM  в список(2) пока без значения
                    posDump = tokenList.size()-1;                                                                       //позиция дампа = размер списка(2)-1
                    tokenList.addAll(getRPN(tokens));                                                                   //добавить в список коллекцию, которую вернет метод getRPN
                    tokenList.get(posDump).setValue(Integer.toString(tokenList.size()));                                //на позицию токена Дампа установить значение = длине списка(2)
                }else{                                                                                                  //иначе
                    tokenList.addAll(getRPN(tokens));                                                                   //добавить в список коллекцию, которую вернет метод getRPN
                }
            }
            //
            tokenList.add(call);                                                                                        //добавить токен call в список(2) токенов
            tokenList.add(addressOut);                                                                                  //добавить токен addressOUt в список(2) токенов
            addressOut.setValue(Integer.toString(tokenList.size()));                                                    //установить значение адреса выхода = размеру списка(2)
            tokenList.add(new Token(TokenType.POPFROMRETURNSTACK,"R>"));                                          //добавить токен POPFROMRETURNSTACK в список(2) со значением R>
            tokenList.add(new Token(TokenType.DROP,"DROP"));                                                      //добавить токен DROP в список (2) со значением DROP
        }else{                                                                                                          //иначе
            tokenList.get(posDump).setValue(Integer.toString(tokenList.size()));                                        //установить значениетокена, на позиции дампа = размеру списка(2)
        }
    }

    private static void getRPNWithList(List<Token> tokenList, Stack<Token> opStack)
    {
        
    }

    private static int getPriorityAndType(final Token value) {
        switch(value.getType()){
            case PRINTLIST: return 0;
            case LEFT_BRACKET: return 0;
            case IF: return 0;
            case WHILE: return 0;
            case  EXIT: return 0;
            case  BREAK: return 0;
            case  SEMICOLON: return 0;
            case  PRINTLINE: return 0;
            case RIGHT_BRACKET: return 1;
            case ASSIGNMENT: return 1;
            case ELSE: return 1;
            case OR: return 2;
            case AND: return 3;
            case LOEC: return 4;
            case LESS: return 4;
            case MOEQ: return 4;
            case MORE: return 4;
            case EQUAL: return 4;
            case NOTEQUAL: return 4;
            case ADD: return 5;
            case SUB: return 5;
            case STAR: return 6;
            case SLASH: return 6;
            case DOT: return 7;
            case GETFIRST: return 0;
            case GETlAST: return 0;
            case ADDlAST: return 0;
            case ADDFIRST: return 0;
            case REMOVElAST: return 0;
            case REMOVEFIRST: return 0;
            case NEWLIST: return 8;
            case NEWHASH: return 8;
            case FINDHASH: return 0;
            case PRINTHASH: return 0;
            case ADDELEM: return 0;
            default: return -1;

        }
    }
    
    private static Token getToken(int pos){
        return tokens.get(pos);
    }
}