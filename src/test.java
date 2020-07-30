
public class test {
    public static void main(String[] var0) {
        start();
    }

    static {
    }

    public static int f(int var0) {
        return var0 == 1 ? 1 : f(var0 - 1) * var0;
    }

    public static void start() {
        byte var0 = 3;
        System.out.println(f(var0));
    }


}