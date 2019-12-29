import java.util.Scanner;

public class CodeForces1263E {

    static int[] max;
    static int[] min;
    static int[] lazy;
    static int[] leftIndex;
    static int[] rightIndex;

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        in.nextLine();
        char[] a = in.nextLine().toCharArray();
        initTree(a.length);
        int index = 1, length = a.length, temp;
        char[] result = new char[length + 1];
        int paired = 0;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            if (a[i] == 'R') {
                index++;
            } else if (a[i] == 'L') {
                index = Math.max(1, index - 1);
            } else {
                temp = 0;
                if (a[i] == ')') {
                    temp--;
                } else if (a[i] == '(') {
                    temp++;
                }
                if (result[index] == '(') {
                    temp--;
                } else if (result[index] == ')') {
                    temp++;
                }
                paired += temp;
                if (temp != 0) {
                    add(temp, 1, index, n);
                }
                result[index] = a[i];
            }
            if (paired == 0 && getMin() >= 0) {
                sb.append(getMax()).append(' ');
            } else {
                sb.append(-1).append(' ');
            }
        }

        System.out.println(sb.toString().trim());
    }

    public static void initTree(int n) {
        max = new int[4 * n];
        min = new int[4 * n];
        lazy = new int[4 * n];
        leftIndex = new int[4 * n];
        rightIndex = new int[4 * n];
        initInternal(1, leftIndex, rightIndex, 1, n);
    }

    public static void initInternal(int index, int[] l, int[] r, int left, int right) {
        l[index] = left;
        r[index] = right;
        if (left == right) {
            return;
        }
        initInternal(2 * index, l, r, left, (left + right) / 2);
        initInternal(2 * index + 1, l, r, (left + right) / 2 + 1, right);
    }

    public static void add(int value, int index, int l, int r) {
        // 刚好
        if (leftIndex[index] == l && rightIndex[index] == r) {
            lazy[index] += value;
            max[index] += value;
            min[index] += value;
            return;
        }
        add(lazy[index], 2 * index, leftIndex[2 * index], rightIndex[2 * index]);
        add(lazy[index], 2 * index + 1, leftIndex[2 * index + 1], rightIndex[2 * index + 1]);
        lazy[index] = 0;
        if (r <= rightIndex[2 * index]) {// 全在左儿子
            add(value, 2 * index, l, r);
        } else if (l >= leftIndex[2 * index + 1]) {// 全在右儿子
            add(value, 2 * index + 1, l, r);
        } else {// 左右都有
            add(value, 2 * index, l, rightIndex[2 * index]);
            add(value, 2 * index + 1, leftIndex[2 * index + 1], r);
        }
        max[index] = Math.max(max[2 * index], max[2 * index + 1]);
        min[index] = Math.min(min[2 * index], min[2 * index + 1]);
    }

    public static int getMax() {
        return max[1];
    }

    public static int getMin() {
        return min[1];
    }

}
