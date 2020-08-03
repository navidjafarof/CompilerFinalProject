public class Test {
    public static void main(String[] var0) {
        start();
    }

    static {
    }

    public static void start() {
        short var0 = 432;
        long var1 = (long) 1900.0D;
        boolean var10000 = (long) var0 > var1;
        double var3 = 3.14D;
        double var4 = var3 * (double) var0 * (double) var0;
        long var5 = (long) (var3 * (double) var1 * (double) var1);
        System.out.println(var4);
        System.out.println(var5);
        int[] var6 = new int[5];

        int var7;
        for (var7 = 0; var7 < 5; var6[var7] = var7++) {
        }

        var7 = var6[0];

        for (int var8 = 1; var8 < 5; ++var8) {
            if (var6[var8] > var7) {
                var7 = var6[var8];
            }
        }

        System.out.println(var7);
    }
}
