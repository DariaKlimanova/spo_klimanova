package vm;

import java.util.HashMap;

import java.util.Map;



import lang.exception.VarTableException;

public class VarTable {

    private static Map<String, String> variables = new HashMap<>();

    public static void putVariable(String name, String value){
        variables.put(name, value);
    }

    public static String getVariable(String name) throws VarTableException {
        try{
            return variables.get(name);
        }catch(NullPointerException undefined){
            throw new VarTableException(name + " is undefined");
        }
    }
    private static Map<String, LinkedList<String>> lists = new HashMap<>();
    public static void putList(String name, LinkedList<String> list){
        lists.put(name, list);
      
    }
    public static LinkedList<String> getDataList(String name) throws VarTableException {
        try{
            return lists.get(name);
        }catch(NullPointerException undefined){
            throw new VarTableException(name + " is undefined");
        }
    }

}