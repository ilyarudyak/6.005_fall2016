import java.util.Arrays;

/**
 * Created by ilyarudyak on 12/15/16.
 */
public class Part4 {

    static String b = "hello";

    public static void main(String[] args) {

        char vowel0 = 'a';
        final char vowel1 = vowel0;

        String vowel2 = vowel1 + "eiou";
        final String vowel3 = vowel2;

        char[] vowel4 = new char[] { vowel0, 'e', 'i', 'o', 'u' };
        final char[] vowel5 = vowel4;

//        vowel0 = vowel1;
//        System.out.println(vowel0);

//        vowel1 = vowel0;
//        System.out.println(vowel0);

//        vowel3 = vowel2;
//        System.out.println(vowel2);

//        vowel4[0] = 'x';
//        vowel5[0] = 'x';
//        System.out.println(Arrays.toString(vowel4));
//        System.out.println(Arrays.toString(vowel5));

        String a = "hello";
        final String c = a;
        final String d = b;

        a = "x";
        b = "x";
//        c = "x";
//        d = "x";
        System.out.println(a + " " + b);

    }
}
