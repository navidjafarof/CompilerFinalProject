import Semantic.AST.Expression.SizeOf;

public class test {
    public static void main(String[] var0) {
        start();
    }

    static {
    }

    public static void start() {
        byte var0 = 2;
        byte var10000 = var0;
        int var2 = var0 + 1;
        byte var1 = var10000;
        int var3 = var1 - 2;
        System.out.println(var2);
        System.out.println(var3);
    }


}
