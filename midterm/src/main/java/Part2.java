/**
 * Created by ilyarudyak on 12/15/16.
 */
public class Part2 {

    public static boolean leap(int y) {
        String tmp = String.valueOf(y);
        if (tmp.charAt(2) == '1' || tmp.charAt(2) == '3' || tmp.charAt(2) == 5
                || tmp.charAt(2) == '7' || tmp.charAt(2) == '9') {
            if (tmp.charAt(3) == '2' || tmp.charAt(3) == '6') {
                System.out.println("i'm at R1");
                return true; /*R1*/
            }
            else {
                System.out.println("i'm at R2");
                return false; /*R2*/
            }
        } else {
            if (tmp.charAt(2) == '0' && tmp.charAt(3) == '0') {
                System.out.println("i'm at R3");
                return false; /*R3*/
            }
            if (tmp.charAt(3) == '0' || tmp.charAt(3) == '4' || tmp.charAt(3) == '8') {
                System.out.println("i'm at R4");
                return true; /*R4*/
            }
        }
        System.out.println("i'm at R5");
        return false; /*R5*/
    }

    public static void main(String[] args) {

        System.out.println(leap(2015));
    }
}
