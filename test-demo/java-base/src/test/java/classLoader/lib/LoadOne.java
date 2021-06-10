package classLoader.lib;

public class LoadOne {

    public void say() {
        System.out.println(this.getClass().getClassLoader());
        System.out.println(LoadTwo.class.getClassLoader());
        System.out.println("hello4");
    }
}
