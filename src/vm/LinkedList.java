package vm;


import java.util.Iterator;

public class LinkedList<Elem> implements Iterable<Elem>{

    private Node<Elem> fstNode;
    private Node<Elem> lstNode;
    private int size=0;

    public LinkedList(){
        lstNode = new Node<Elem>(null, fstNode, null);
        fstNode = new Node<Elem>(null, null, lstNode);
    }

    public void addLast(Elem e)
    {
        Node<Elem> prev = lstNode;
        prev.setcur(e);
        lstNode=new Node<Elem>(null,prev,null);
        prev.setnext(lstNode);
        size++;
    }

    public void removeFirst()
    {
        Node<Elem> step = fstNode.getnext();
        fstNode=step;
        step=step.getnext();
        step.setprev(fstNode);
        size--;
    }

    public void removeLast()
    {
        Node<Elem> step = lstNode.getprev();
        lstNode=step;
        step=step.getprev();
        step.setnext(lstNode);
        size--;
    }

    public void addFirst(Elem e)
    {
        Node<Elem> next = fstNode;
        next.setcur(e);
        fstNode=new Node<>(null,null,next);
        next.setprev(fstNode);
        size++;
    }


    public Elem getbyin(int counter)
    {
        Node<Elem> target = fstNode.getnext();
        for(int i=0; i<counter; i++)
        {
            target=getnext(target);
        }
        return target.getcur();
    }

    public boolean checkElementByValue(Elem value){
        boolean res=false;
        Node<Elem> target = fstNode.getnext();
        for(int i=0; i<listSize(); i++)
        {
            if(value.equals(target.getcur()))
            {
                return true;
            }else {
                target=getnext(target);
            }
        }
        return res;
    }

    private Node<Elem> getnext(Node<Elem> current)
    {
        return current.getnext();
    }
    private Node<Elem> getprev(Node<Elem> current)
    {
        return current.getprev();
    }

    public Iterator<Elem> iterator(){
        return new Iterator<Elem>() {
            int counter = 0;
            @Override
            public boolean hasNext() {
                return counter<size;
            }

            @Override
            public Elem next() {
                return getbyin(counter++);
            }
        };
    }



    public int listSize()
    {
        return size;
    }

    private class Node<Elem>{
        private Elem curElem;
        private Node<Elem> nextElem;
        private Node<Elem> prevElem;

        private Node(Elem curElem, Node<Elem> prevElem, Node<Elem> nextElem){
            this.curElem = curElem;
            this.nextElem = nextElem;
            this.prevElem = prevElem;
        }
        public Elem getcur(){return curElem;}
        public void setcur(Elem curElem) {this.curElem = curElem; }
        public Node<Elem> getnext() {return nextElem;}
        public void setnext(Node<Elem> nextElem) {this.nextElem = nextElem;}
        public Node<Elem> getprev() {return prevElem;}
        public void setprev(Node<Elem> prevElem) {this.prevElem = prevElem; }

    }
}
