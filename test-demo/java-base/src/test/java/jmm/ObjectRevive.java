package jmm;

public class ObjectRevive {
    public static ObjectRevive instance;

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        instance = this;
        System.out.println("finalize exec");
    }

    public static void main(String[] args) {
        new ObjectRevive();
        instance = null;
        System.gc();
        System.out.println("first instance:" + instance);
        instance = null;
        System.gc();
        System.out.println("second instance:" + instance);
    }
}
