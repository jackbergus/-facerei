package it.giacomobergami.facerei.utils.trees;

import it.giacomobergami.facerei.utils.iterators.ArrayIterator;
import it.giacomobergami.facerei.utils.URI.ResourceIDPointer;

import java.util.*;

/**
 * Represents both documents and documents' folksonomies
 */
public class Tree<T> implements  ITree<T> {
    public T value;
    public Collection<Tree<T>> siblings;
    public Tree(T value) {
        this.value = value;
        this.siblings = new LinkedList<Tree<T>>();
    }

    public static <T> Tree<T> NIL() {
        return null;
    }

    public static <T> Tree<T> leaf(T value) {
        return new Tree(value);
    }

    public T getValue() {
        return value;
    }

    public T setValue(T newValue) {
        T old = value;
        this.value = newValue;
        return old;
    }

    public Iterable<ITree<T>> getSiblings() {
        return () -> new Iterator<ITree<T>>() {

            Iterator<Tree<T>> it = siblings.iterator();

            @Override
            public boolean hasNext() {
                return it.hasNext();
            }

            @Override
            public ITree<T> next() {
                return it.next();
            }
        };
    }

    public int getSiblingsSize() {
        return siblings.size();
    }

    public ITree<T> addSibling(ITree<T> elem) {
        ITree<T> toadd = elem;
        siblings.add((Tree<T>)toadd);
        return toadd;
    }

    @Override
    public boolean addPath(T[] array) {
        boolean found = false;
        for (Tree<T> t : siblings) {
            found = found || t.equals(array[0]);
        }
        if ((!found)) {
            return ((Tree<T>)addSibling(array[0])).addPath(array,0);
        } else {
            Iterator<Tree<T>> it = siblings.iterator();
            found = false;
            while (it.hasNext()&&(!found)) {
                found = it.next().addPath(array,0);
            }
            return found;
        }
    }

    private boolean addPath(T[] array, int i) {
        if (array.length<=i) return false;
        else {
            if (value.equals(array[i])) {
                boolean found = false;
                for (Tree<T> t : siblings) {
                    found = found || t.equals(array[i+1]);
                }
                if ((!found)&&(array.length>i+1)) {
                    return ((Tree<T>)addSibling(array[i+1])).addPath(array,i+1);
                } else {
                    found = false;
                    Iterator<Tree<T>> it = siblings.iterator();
                    while (it.hasNext()) {
                        found = found || it.next().addPath(array,i+1);
                    }
                }
            } else return false;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof String) return value.equals(o); else
        if (o instanceof Tree) return value.equals(((ITree)o).getValue()); else
        return false;
    }

    private void parser(StringBuilder sb, String tabs) {
        sb.append(tabs).append(value).append("\n");
        siblings.forEach(x->x.parser(sb,tabs+tabs.substring(0,1)));
    }

    public ResourceIDPointer convertToPointer(Iterator<String> struc, String resource, Stack<Integer> toBuildUp) {
        if (struc.hasNext()) {
            String elem = struc.next();
            int count = 0;
            for (Tree<T> t : siblings) {
                if (t.getValue().equals(elem)) {
                    toBuildUp.push(count);
                    ResourceIDPointer toret = t.convertToPointer(struc,resource,toBuildUp);
                    if (toret!=null) return toret;
                    else toBuildUp.remove(toBuildUp.size()-1);
                } else count++;
            }
            toBuildUp.pop();
            return null;
        } else {
            Queue<Integer> q = new LinkedList<>();
            while (!toBuildUp.empty()) {
                q.add(toBuildUp.firstElement());
                toBuildUp.remove(0);
            }
            return new ResourceIDPointer(resource,q);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        parser(sb,".");
        return sb.toString();
    }

    public static void main(String args[]) {
        Tree<String> t = new Tree(null);
        String[] s = new String[]{"this","is","the","end","of","the","story"};
        t.addPath(s);
        String[] u = new String[]{"this","is","the","end","of","my","life"};
        t.addPath(u);
        String[] v = new String[]{"this","is","it"};
        t.addPath(v);
        String[] w = new String[]{"I","did","this"};
        t.addPath(w);
        System.out.println(t.convertToPointer(new ArrayIterator(s),"CURRENT",new Stack<>()));
        System.out.println(t.convertToPointer(new ArrayIterator(u),"CURRENT",new Stack<>()));
        System.out.println(t.convertToPointer(new ArrayIterator(v),"CURRENT",new Stack<>()));
        System.out.println(t.convertToPointer(new ArrayIterator(w),"CURRENT",new Stack<>()));
    }


    public boolean isLeaf() {
        return siblings.isEmpty();
    }
}
