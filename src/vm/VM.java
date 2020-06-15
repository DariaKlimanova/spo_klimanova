package vm;

import java.util.ArrayList;

import java.util.List;
import java.util.Stack;


import lang.exception.VarTableException;
import lang.token.Token;
import lang.token.TokenType;

public class VM {

    private static List<Token> programMemory;
    private static int PC;
    private static Stack<Token> dataStack;
    private static Stack<Token> returnStack;

    private static String value_first;
    private static String value_second;
    private static String value_result;
    
 
  
   
    private static LinkedList<String> lLink;
    private static String listInUse="";
    private static testHash HS;
     private static int element;


    public static void run() throws VarTableException {
        while(!getToken(PC).getType().equals(TokenType.EOF)){
            switch(getToken(PC).getType()){
                case ADD:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    try{
                        value_result = Integer.toString(Integer.valueOf(value_first) + Integer.valueOf(value_second));
                    }catch(NumberFormatException not_int){
                        value_result = Double.toString(Double.valueOf(value_first) + Double.valueOf(value_second));
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case SUB:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    try{
                        value_result = Integer.toString(Integer.valueOf(value_second) - Integer.valueOf(value_first));
                    }catch(NumberFormatException not_int){
                        value_result = Double.toString(Double.valueOf(value_second) - Double.valueOf(value_first));
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case STAR:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    try{
                        value_result = Integer.toString(Integer.valueOf(value_second) * Integer.valueOf(value_first));
                    }catch(NumberFormatException not_int){
                        value_result = Double.toString(Double.valueOf(value_second) * Double.valueOf(value_first));
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case SLASH:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    try{
                        value_result = Integer.toString(Integer.valueOf(value_second) / Integer.valueOf(value_first));
                    }catch(NumberFormatException not_int){
                        value_result = Double.toString(Double.valueOf(value_second) / Double.valueOf(value_first));
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case SYS_CALL:
                    PC++;
                    returnStack.push(new Token(TokenType.ADDRESS,Integer.toString(PC+1)));
                    PC = Integer.valueOf(getToken(PC).getValue());
                    break;
                case IF:
                    PC++;
                    if(Integer.valueOf(dataStack.pop().getValue()) == 0){
                        PC = Integer.valueOf(getToken(PC).getValue());
                    }else{
                        PC++;
                    }
                    break;
                case AND:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_first) > 0 && Integer.valueOf(value_second) > 0){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case OR:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_first) > 0 || Integer.valueOf(value_second) > 0){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case ASSIGNMENT:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    VarTable.putVariable(value_second, value_first);
                    PC++;
                    break;
                case MOEQ:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_second) >= Integer.valueOf(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case MORE: 
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_second) > Integer.valueOf(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case LOEC:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_second) <= Integer.valueOf(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case LESS:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(Integer.valueOf(value_second) < Integer.valueOf(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case EQUAL:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(value_second.equals(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case NOTEQUAL:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    if(value_second.matches("[A-Za-z_]+")){
                        value_second = VarTable.getVariable(value_second);
                    }
                    if(!value_second.equals(value_first)){
                        value_result = "1";
                    }else{
                        value_result = "0";
                    }
                    dataStack.push(new Token(TokenType.NUM, value_result));
                    PC++;
                    break;
                case POPFROMRETURNSTACK:
                    dataStack.push(returnStack.pop());
                    PC++;
                    break;
                case PUSHTORETURNSTACK:
                    returnStack.push(dataStack.pop());
                    PC++;
                    break;
                case DROP:
                    dataStack.pop();
                    PC++;
                    break;
                case PRINTLINE:
                    value_first = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    System.out.println(value_first);
                    PC++;
                    break;
                case NEWLIST:
                    value_first=dataStack.pop().getValue();
                    newList(value_first);
                    PC++;
                    break;
                case ADDlAST:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    lLink.addLast(value_first);
                    lList(value_second);
                    PC++;
                    break;
                case ADDFIRST:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z]+")){
                        value_first = VarTable.getVariable(value_first);
                    }
                    lLink.addFirst(value_first);
                    lList(value_second);
                    PC++;
                    break;
                    case REMOVElAST:
                    value_first=dataStack.pop().getValue();
                    checklist(value_first);
                    lLink.removeLast();
                    lList(value_first);
                    PC++;
                    break;
                case REMOVEFIRST:
                    value_first=dataStack.pop().getValue();
                    checklist(value_first);
                    lLink.removeFirst();
                    lList(value_first);
                    PC++;
                    break;
                case GETFIRST:
                    value_first=dataStack.pop().getValue();
                    value_second=dataStack.pop().getValue();
                    checklist(value_second);
                    VarTable.putVariable(value_first, lLink.getbyin(0));
                    System.out.println("First value is: "+lLink.getbyin(0));
                    PC++;
                    break;
                case GETlAST:
                    value_first=dataStack.pop().getValue();
                    value_second=dataStack.pop().getValue();
                    checklist(value_second);
                    VarTable.putVariable(value_first, lLink.getbyin(lLink.listSize()-1));
                    System.out.println("Last value is: "+lLink.getbyin(lLink.listSize()-1));
                    PC++;
                    break;
                case PRINTLIST:
                    value_first=dataStack.pop().getValue();
                    checklist(value_first);
                    for(String s : lLink)
                    {
                     System.out.println(""+s);
                    }
                    PC++;
                    break;
                case NEWHASH:
                    value_first=dataStack.pop().getValue();
                    newHash(value_first);
                    PC++;
                    break;
                case FINDHASH:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                    value_first = VarTable.getVariable(value_first);
                     }
                     element = Integer.valueOf(value_first);
                     System.out.println("Найден элемент "+element+" = "+HS.checkvalueset(element));
                     PC++;
                    break;
                case ADDELEM:
                    value_first = dataStack.pop().getValue();
                    value_second = dataStack.pop().getValue();
                    if(value_first.matches("[A-Za-z_]+")){
                    value_first = VarTable.getVariable(value_first);
                     }
                     element = Integer.valueOf(value_first);
                     HS.addelem(element);
                    PC++;
                    break;
                case PRINTHASH:
                    value_first=dataStack.pop().getValue();
                    HS.loadelements();
                    PC++;
                    break;
                   
                default:
                    dataStack.push(getToken(PC));
                    PC++;
                    break;
            }
        }

        System.out.println("Program end succesfuly.");
    }

    public static void lList(String name)
    {
        VarTable.putList(name, lLink);
    }
    public static void newList(String namer){
        listInUse=namer;
        lLink=new  LinkedList<>();
        lList(namer);
    }
    public static void newHash(String namer){
        
        HS = new  testHash();
        
    }
    private static void checklist(String inList)
    {
        if(inList!=listInUse)
        {
            SwapLists(inList);
        }

    }
    private static void SwapLists(String newName)
    {
        lList(listInUse);
        listInUse=newName;
        try{
            lLink=VarTable.getDataList(newName);
        }catch(Exception e) {
            newList(newName);
        }

    }
    public static void loadProgram(List<Token> tokens){
        programMemory = tokens;
    }

    public static void init(){
        programMemory = new ArrayList<>();
        PC = 0;
        dataStack = new Stack<>();
        returnStack = new Stack<>();
        listInUse="nolist";

    }
    private static Token getToken(int pos){
        return programMemory.get(pos);
    }


}