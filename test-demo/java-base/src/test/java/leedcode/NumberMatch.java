package leedcode;

public class NumberMatch {


    /**
     * 初始状态：
     * 0：
     * +      -->  1
     * -      -->  1
     * .      -->  2
     * 0-9
     * 匹配数值
     * 1：
     * .      -->  2
     * 0-9    -->  1
     * e or E -->  3
     * 匹配小数
     * 2:
     * <p>
     * 后导整数
     * 3:
     */

    public boolean isNumber(String s) {
        s=s.trim();
        // 1. 匹配前导符号
        char c = s.charAt(0);
        if (c == '+' || c == '-') {
            s = s.substring(1);
        }
        if (s.contains("e") || s.contains("E")) {
            // 2. 匹配浮点数
            return isFloat(s);
        } else {
            // 3. 匹配整数或小数
            return isInteger(s) || isDecimal(s);
        }
    }

    // 是否为浮点数
    private boolean isFloat(String s) {
        if (s.length() == 1) {
            return false;
        }
        s = s.toLowerCase();
        if(s.length()-s.replace("e","").length()>1){
            return false;
        }
        String[] items = s.split("e");

        if (items.length != 2) {
            return false;
        }

        // 前半部分是整数或整数
        if (!(isInteger(items[0]) || isDecimal(items[0]))) {
            return false;
        }
        // 后半部分是整数
        if (items[1].length() > 1 && (items[1].charAt(0) == '+' || items[1].charAt(0) == '-')) {
            items[1] = items[1].substring(1);
        }
        if (!isInteger(items[1])) {
            return false;
        }
        return true;
    }

    // 是否为整数
    private boolean isInteger(String s) {
        // 长度校验
        if (s.length() == 0) {
            return false;
        }
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    //是否为小数
    private boolean isDecimal(String s) {
        //1. 长度校验
        if (s.length() == 0) {
            return false;
        }
        boolean havDot = false;
        // 2. 只有一个点
        if (s.length() == 1 && s.equals(".")) {
            return false;
        }
        char c;
        for (int i = 0; i < s.length(); i++) {
            c = s.charAt(i);
            if (c == '.') {
                if (havDot) {
                    return false;
                } else {
                    havDot = true;
                }
            } else if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
        String[] items = new String[]{"+100", "5e2", "-123", "3.1416", "-1E-16", "0123"};
        items = new String[]{"12e", "1a3.14", "1.2.3", "+-5", "12e+5.4"};
        /*for (String item : items) {
            System.out.println(item + "\t\t" + new NumberMatch().isNumber(item));
        }*/
        System.out.println(new NumberMatch().isNumber("7e69e"));
    }
}
