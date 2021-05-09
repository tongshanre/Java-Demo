package leedcode;

import java.util.Stack;

public class StackQueue {

    class CQueue {
        Stack<Integer> base = new Stack<Integer>();
        Stack<Integer> tmp = new Stack<Integer>();

        public CQueue() {

        }

        public void appendTail(int value) {
            base.push(value);
        }

        public int deleteHead() {
            if(base.empty())
                return -1;
            while (!base.empty()) {
                tmp.push(base.pop());
            }
            Integer value = tmp.pop();
            while (!tmp.empty()) {
                base.push(tmp.pop());
            }
            return value;
        }
    }
}
