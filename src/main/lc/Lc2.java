public class Lc2 {
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int i = 0;
        long sum = 0;

        while (true) {
            if (l1 != null) {
                sum += l1.val * Math.pow(10, i);
            }
            if (l2 != null) {
                sum += l2.val * Math.pow(10, i);
            }
            if (l1 == null && l2 == null) {
                break;
            }

            if (l1 != null) {
                l1 = l1.next;
            }

            if (l2 != null) {
                l2 = l2.next;
            }
            i++;
        }

        String sumS = String.valueOf(sum);
        int chr0 =  Integer.parseInt(String.valueOf(sumS.charAt(0)));
        ListNode l3 = new ListNode(chr0);
        for (int j = 1; j<sumS.length(); j++) {
            int chr  = Integer.parseInt(String.valueOf(sumS.charAt(j)));
            ListNode newNode = new ListNode(chr);
            newNode.next = l3;
            l3 = newNode;
        }
        return l3;
    }

    public static void main(String[] args) {
        int[] testLN1 = new int[] {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
        int[] testLN2 = new int[] {5,6,4};

        ListNode l1 = new ListNode(testLN1[0]);
        for (int j = 1; j<testLN1.length; j++) {
            ListNode newNode = new ListNode(testLN1[j]);
            newNode.next = l1;
            l1 = newNode;
        }

        ListNode l2 = new ListNode(testLN2[0]);
        for (int j = 1; j<testLN2.length; j++) {
            ListNode newNode = new ListNode(testLN2[j]);
            newNode.next = l2;
            l2 = newNode;
        }


        Lc2 lc2 = new Lc2();
        ListNode l3 = lc2.addTwoNumbers(l1, l2);

        int i = 0;

        while (l3 != null) {
            System.out.print((i != 0 ? " -> " : "") + l3.val );
            l3 = l3.next;
        }


    }
}
class ListNode {
    int val;
    ListNode next;
    ListNode(int x) {
        val = x;
    }
}


