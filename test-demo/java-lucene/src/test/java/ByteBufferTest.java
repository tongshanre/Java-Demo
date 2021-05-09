import java.util.concurrent.ExecutionException;

public class ByteBufferTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ThreadLocal<String> param1 = new ThreadLocal<String>();
        ThreadLocal<String> param2 = new ThreadLocal<String>();
        param1.set("Param1");
        param2.set("Param2");
        System.out.println(param1.get() + "\t" + param2.get());
    }
}
