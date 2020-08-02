import Semantic.AST.Expression.SizeOf;

public class test {
    static int h;

    public static void main(String[] var0) {
        start();
    }

    public static void start() {
        String var0 = "12345r";
        System.out.println(var0.charAt(2));
        System.out.println(var0);
        char[] var1 = new char[50];
        var1[2] = var0.charAt(2);
    }
}
