
package vm;


public class testHash extends LinkedList {
    private LinkedList[] korzina=new LinkedList[8];

    public testHash(){
        for (int i=0;i<8;i++)
        {
            korzina[i]=new LinkedList();
        }
    }

    private int hashfunc(int value){ return value%korzina.length; }

    public void addelem(int elem){
        boolean flag = checkvalueset(elem);
        if (flag){
       System.out.println("В корзине уже есть значение "+hashfunc(elem)); 
        }
        else{
       System.out.println("Добавлено значение"+hashfunc(elem)); 
       System.out.println("Hashcode = "+hashfunc(elem));
        korzina[hashfunc(elem)].addLast(elem);
        }
    }

    public boolean checkvalueset(int elem){ return korzina[hashfunc(elem)].checkElementByValue(elem); }
    public void loadelements()
    {
        for(int i=0;i<korzina.length;i++)
        {
            System.out.println("Корзина "+i+":");
            for (Object s : korzina[i]){
                System.out.println(""+s.toString());
            }
        }

    }

}