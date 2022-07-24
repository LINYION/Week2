public class TestDemo {
    public static void main(String[] args) {
        //计算器
        Calculator calculator = new Calculator();
        int a = calculator.calculate("(1 + 2 / 1)* (2 - 1)");
        System.out.println(a);
        //链表
        Link link = new Link();
        link.add(1);
        link.add(2);
        link.add(3);
        link.add("4");
        link.add(5);
        link.remove(2);
        System.out.println(link.contains("4"));
        System.out.println(link.contains(2));
    }
}
