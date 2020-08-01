import Semantic.AST.Expression.SizeOf;

public class test {
    public static void main(String[] var0) {
        start();
    }

    static {
    }

    public static void start() {
        int[] var0 = new int[]{1, 0, 2, 0, 0};
        int var1 = var0[2];
        var1 += 5 + var1;
    }
}
