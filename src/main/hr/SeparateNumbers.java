import java.math.BigInteger;

public class SeparateNumbers {

    public static String check(String s, int firstLen) {
        int startPos = 0;

        String startStr = s.substring(0, firstLen);
        BigInteger expInt = new BigInteger(startStr).add(new BigInteger("1"));
        String expStr = expInt.toString();

        int endPos = startPos + firstLen + expStr.length();

        while (endPos <= s.length()) {
            String actualStr = s.substring(endPos - expStr.length(), endPos);
            if (!expStr.equals(actualStr)) {
                break;
            } else if (endPos == s.length()) {
                return "YES " + startStr;
            } else {
                expInt = expInt.add(new BigInteger("1"));;
                expStr = expInt.toString();
                endPos += expStr.length();
            }
        }
        return "";
    }

    public static void main(String[] args) {
        String s = "99100101";
//        99910001001
//        7891011
//        9899100
//        999100010001
        int len = 1;
        boolean found = false;
        while (len <= s.length() / 2) {
            String res = check(s, len);
            if (res.length() > 0) {
                System.out.println(res);
                found = true;
                break;
            }
            len++;
        }
        if (!found) {
           System.out.println("NO");
        }
    }
}
