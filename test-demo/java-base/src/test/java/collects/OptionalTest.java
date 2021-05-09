package collects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OptionalTest {

    @Test
    public void testOptional() {
        List<String> zoo = new ArrayList<>();
        Optional.ofNullable("bird").ifPresent(zoo::add);
        System.out.println(zoo);

    }

    public static void main(String[] args) {
        Integer a = new Integer(1023);
        Integer b = new Integer(1023);
        System.out.println(a.equals(b));
    }


}
