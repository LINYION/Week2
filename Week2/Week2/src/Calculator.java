import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

public class Calculator {
    private static final int INT_MAX = 2147483647;
    private static final int INT_MIN = -2147483648;
    // 使用 map 设定运算符优先级
    Map<Character, Integer> map = new HashMap<>(){{
        put('-', 1);
        put('+', 1);
        put('*', 2);
        put('/', 2);
    }};
    public int calculate(String s) {
        // 将所有的空格去掉
        s = s.replaceAll(" ", "");
        //将字符串转为字符数组
        char[] cs = s.toCharArray();
        int n = s.length();
        for(int i=0;i<n-1;i++){
            if(cs[i]=='/' &&cs[i+1]=='0'){
                throw new ArithmeticException("除数不能为0");
            }
        }
        // 存放所有的数字
        Deque<Integer> nums = new ArrayDeque<>();
        // 为了防止第一个数为负数，先往 nums 加个 0
        nums.addLast(0);
        // 存放所有「非数字以外」的操作
        Deque<Character> ops = new ArrayDeque<>();
        for (int i = 0; i < n; i++) {
            char c = cs[i];
            //检测到左括号：存入
            if (c == '(') {
                ops.addLast(c);
            //检测到右括号：计算到最近左括号中的算式
            } else if (c == ')') {
                while (!ops.isEmpty()) {
                    //public E peekLast()：检索但不删除此列表的最后一个元素，如果此列表为空，则返回null。
                    if (ops.peekLast() != '(') {
                        calc(nums, ops);
                    } else {
                        ops.pollLast();
                        break;
                    }
                }
            //检测到除括号外的字符：判断是数字还是符号然后分别存入对应栈
            } else {
                if (isNumber(c)) {
                    int u = 0;
                    int j = i;
                    // 将从 i 位置开始后面的连续数字整体取出，加入 nums
                    while (j < n && isNumber(cs[j])) {
                        u = u * 10 + (cs[j++] - '0');
                    }
                    nums.addLast(u);
                    i = j - 1;
                } else {
                    if (i > 0 && (cs[i - 1] == '(' || cs[i - 1] == '+' || cs[i - 1] == '-')) {
                        nums.addLast(0);
                    }
                    // 有一个新操作要入栈时，先把栈内可以算的都算了
                    // 只有满足「栈内运算符」比「当前运算符」优先级高/同等，才进行运算
                    while (!ops.isEmpty() && ops.peekLast() != '(') {
                        char prev = ops.peekLast();
                        if (map.get(prev) >= map.get(c)) {
                            calc(nums, ops);
                        } else {
                            break;
                        }
                    }
                    ops.addLast(c);
                }
            }
        }
        // 将剩余的计算完
        while (!ops.isEmpty()) {
            calc(nums, ops);
        }
        return nums.peekLast();
    }

    /*
    *实现数值计算
    *nums 算式数字双向链表 ops算式字符操作双向链表
     */
    void calc(Deque<Integer> nums, Deque<Character> ops) {
        if (nums.isEmpty() || nums.size() < 2) {
            return;
        }
        if (ops.isEmpty()){
            return;
        }
        //public E pollLast()：检索并删除此列表的最后一个元素，如果此列表为空，则返回null。
        int b = nums.pollLast(), a = nums.pollLast();
        char op = ops.pollLast();
        int ans = 0;
        if (op == '+') {
            ans = a + b;
            if(a > 0 && b > 0 && ans < 0||a < 0 && b < 0 && ans > 0) {
                throw new ArithmeticException("数据溢出");
            }
        }
        else if (op == '-') {
            ans = a - b;
            if(a > 0 && b > 0 && a > INT_MAX - b||a < 0 && b < 0 && a < INT_MIN - b) {
                throw new ArithmeticException("数据溢出");
            }
        }
        else if (op == '*') {
            ans = a * b;
        }
        else if (op == '/') {
                ans = a / b;
            }
        nums.addLast(ans);
    }

    /*
    *判断是否为数字
     */
    boolean isNumber(char c) {
        return Character.isDigit(c);
    }
}
