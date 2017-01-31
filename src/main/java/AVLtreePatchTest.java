import java.util.*;

/**
 * Created by Serog on 30.01.2017.
 */

public class AVLtreePatchTest {

    public static void main(String[] args) throws InterruptedException {
        Random rnd = new Random(666);
        AVLtree avlTree = new AVLtree();
        AVLtreePatch avlTreePatch = new AVLtreePatch();
        List<Integer> integerList;
        TreeSet<Integer> set = new TreeSet<Integer>();
        int[] numberOfElements = {1000, 10000, 50000};

        for (int number: numberOfElements) {
            integerList = new ArrayList<Integer>();

            for (int i = 0; i < number; ) {
                integerList.add(++i);
            }

            Collections.shuffle(integerList);

            System.out.println("Testing on " + number + " elements");

            runTest(avlTreePatch, integerList, number);

            runTest(avlTree, integerList, number);

            System.out.println("");
        }

        System.out.println("Starting \"Is a Set\" test in 5 seconds");
        Thread.sleep(5000);

        while (true) {
            boolean operation = rnd.nextBoolean();
            int k = rnd.nextInt(2000);
            if (operation) {
                avlTreePatch.insert(k);
                set.add(k);
            } else {
                avlTreePatch.delete(k);
//                if (set.contains(k)) {
//                    System.out.print("false ");
//                } else {
//                    System.out.print("true ");
//                }
                set.remove(k);
            }

            if ( eqToSet(avlTreePatch, set) ) {
                System.out.println("pass");
            } else {
                System.out.println("error " + set.size() + "  " + k);
                break;
            }
        }
    }

    private static void runTest(Tree tree, List<Integer> integerList, int number) {

        System.out.print(tree.getClass().getSimpleName() + " ... ");

        long startTimeStamp = new Date().getTime();

        for (Integer integer : integerList){
            tree.insert(integer);
        }

        for (int j = 0; j < number; ) {
            tree.delete(++j);
        }

        long timeInMls = new Date().getTime() - startTimeStamp;

        System.out.println("time elapsed: " + timeInMls + " mls");
    }

    static boolean eqToSet(AVLtreePatch tree, Set<Integer> set) {
        TreeSet<Integer> set2 = new TreeSet<Integer>();
        try {
            eq(tree.root, set, set2);
        } catch (RuntimeException ex){
            return false;
        }
        return set.size() == set2.size();
    }

    private static void eq(AVLtreePatch.Node root, Set<Integer> set, TreeSet<Integer> set2) {
        if(root!= null){
            if(!set.contains(root.key)) throw new RuntimeException();
            if(set2.contains(root.key)) throw new RuntimeException();
            set2.add(root.key);
            eq(root.left, set, set2);
            eq(root.right, set, set2);
        }
    }

}
