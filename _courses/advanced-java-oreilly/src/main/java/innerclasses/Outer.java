package innerclasses;

public class Outer {
    private int anInt;
    private String string;

    public int getAnInt() {
        return anInt;
    }

    public void setAnInt(int anInt) {
        this.anInt = anInt;
    }

    public String getString() {
        return string;
    }

    public void setString(String string) {
        this.string = string;
    }

    // we can instantiate this class outside of Outer (see UseInnerClasses)
    // if we make it private - then we can't do this
    public class Inner {
        public void myMethod() {
            // one of the reasons to have this class inside Outer -
            // we have access to private fields of Outer
            System.out.println("anInt=" + anInt + ", string=" + string);
        }
    }

    // no reference to instance of Outer!!!
    public static class InnerStatic {
        public void myMethod() {
            System.out.println("inside a static inner class");
        }
    }
}
