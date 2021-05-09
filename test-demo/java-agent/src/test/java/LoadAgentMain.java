import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;

import java.io.IOException;

public class LoadAgentMain {
    public static void main(String[] args) {
        VirtualMachine.list().stream().forEach(vmd -> {
            System.out.println(vmd.displayName());
            if (vmd.displayName().equals("java-agent-1.0-SNAPSHOT-jar-with-dependencies.jar")) {
                System.out.println("find it");
                try {
                    VirtualMachine vm = VirtualMachine.attach(vmd.id());
                    vm.loadAgent("D:\\workspace\\git-ws\\Java-Demo\\test-demo\\java-agent\\target\\java-agent-1.0-SNAPSHOT-jar-with-dependencies.jar");
                    System.out.println("load finish");
                    vm.detach();
                } catch (AttachNotSupportedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (AgentLoadException e) {
                    e.printStackTrace();
                } catch (AgentInitializationException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
