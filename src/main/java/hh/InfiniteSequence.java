package hh;

public class InfiniteSequence {

    public static void main(String[] args) {
        System.out.println(findSequence(6789));
        System.out.println(findSequence(111));
    }

    public static int findSequence(int sequence) {
        StringBuilder acc = new StringBuilder().append(0);
        int index;
        for (long i = 1; i < Long.MAX_VALUE; i++) {
            acc.append(Long.toString(i));
            index = acc.indexOf(Integer.toString(sequence));
            if (index != -1) return index;
        }
        return 0;
    }
}
