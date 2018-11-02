import java.util.*;
//implement queue using two stacks
public class QS {

    protected Stack<String> a, b;

    public void enqueue(String item) {
        a.push(item);
        b.clear();
        for (String element: a) {
            b.push(element);
        }
    }

    public String dequeue()
    {
       return b.pop();
    }


    public boolean isEmpty()
    {
        return b.isEmpty();
    }

    public static void main(String[] args) {
        QS queue = new QS();
        queue.enqueue("q1");

        queue.enqueue("q2");

        queue.enqueue("q3");

        queue.dequeue();
        queue.dequeue();

        queue.enqueue("q4");
        queue.dequeue();
        queue.dequeue();
    }
}
