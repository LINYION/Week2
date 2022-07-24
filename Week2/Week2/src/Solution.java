import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {
    static int sum;
    static List<Integer> arr;
    public static void main(String[] args) {
        int num = new Scanner(System.in).nextInt();
        arr = new ArrayList<Integer>();
        dfs(num, 1);
    }

    static void dfs(int num, int head){
        if(num == 0 && arr.size() != 1){
            for (int i = 0; i < arr.size()-1; i++) {
                System.out.print(arr.get(i));
                System.out.print("+");
            }
            System.out.println(arr.get(arr.size()-1));
        }
        for(int i = head;i <= num;i++){
            int n = num-i;
            if(n < 0) continue;
            arr.add(i);
            dfs(n, i);
            arr.remove(arr.size()-1);
        }
    }
}