package com.tong.agentmain;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

public class AttachMain {
    public static void main(String[] args) throws IOException {
        List<VirtualMachineDescriptor> machines = VirtualMachine.list();
        machines.stream().forEach(vmd -> {
            if (vmd.displayName().endsWith("AgentMain-1.0-SNAPSHOT.jar")) {
                try {
                    System.out.println("attach:"+ vmd.displayName());
                    VirtualMachine attach = VirtualMachine.attach(vmd);
                    attach.loadAgent("/home/tong/workspace/java-ws/test-demo/java-agent/target/AgentMain-Demo-1.0-SNAPSHOT.jar", "ccc");
                    System.out.println("ok");
                    attach.detach();
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
