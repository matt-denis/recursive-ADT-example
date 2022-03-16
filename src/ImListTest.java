import list.ImList;

public class ImListTest {
    
    public static void main(String[] args) {
        ImList<Integer> nil = ImList.empty();
        ImList<Integer> x = nil.cons(2).cons(1).cons(0);
        System.out.print("x = nil.cons(2).cons(1).cons(0): ");
        System.out.println(x);
        System.out.println("x.first(): " + x.first());
        System.out.println("x.rest(): " + x.rest());
        System.out.println("x.rest().first(): " + x.rest().first());
        System.out.println("x.rest().rest(): " + x.rest().rest());
        System.out.println("x.rest().rest().first(): " + x.rest().rest().first());
        System.out.println("x.rest().rest().rest(): " + x.rest().rest().rest());
        ImList<Integer> y = x.rest().cons(4);
        System.out.print("y = x.rest().cons(4): ");
        System.out.println(y);
    }
}
