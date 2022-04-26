package com.cc.sp91test.test.jdk.list;
// collections/ListIteration.java

import java.util.*;

public class ListIteration {
    public static void main(String[] args) {
        List<TIist> tIistList = new ArrayList<>();

        for(int i=0;i<8;i++){
            tIistList.add(new TIist(i + "-旧"));
        }

        ListIterator<TIist> it = tIistList.listIterator();
        while(it.hasNext()) {
            System.out.print(it.next() +
                    ", " + it.nextIndex() +
                    ", " + it.previousIndex() + "; ");
        }
        System.out.println();
        // Backwards:
        while(it.hasPrevious()) {
            System.out.print(it.previous().id() + " ");
        }
        System.out.println();
        System.out.println(tIistList);
        it = tIistList.listIterator(3);
        while(it.hasNext()) {
            it.next();
            it.set(new TIist("set一个新的"));
        }
        System.out.println(tIistList);
    }

    public static class TIist{
        public String id;

        public String id(){
            return id;
        }

        public TIist() {
        }

        public TIist(String id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "[" + id + "]";
        }
    }



}
/* Output:
Rat, 1, 0; Manx, 2, 1; Cymric, 3, 2; Mutt, 4, 3; Pug,
5, 4; Cymric, 6, 5; Pug, 7, 6; Manx, 8, 7;
7 6 5 4 3 2 1 0
[Rat, Manx, Cymric, Mutt, Pug, Cymric, Pug, Manx]
[Rat, Manx, Cymric, Cymric, Rat, EgyptianMau, Hamster,
EgyptianMau]
*/
