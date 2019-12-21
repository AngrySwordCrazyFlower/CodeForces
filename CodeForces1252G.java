import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StreamTokenizer;

public class CodeForces1252G {

    public static int n, m, q;
    public static int[] a, b, bIndex, greater, remain;
    public static int[] max, lazy, leftIndex, rightIndex;

    public static void main(String[] args) throws IOException {
        StreamTokenizer streamTokenizer = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        streamTokenizer.nextToken();
        n = (int) streamTokenizer.nval;

        streamTokenizer.nextToken();
        m = (int) streamTokenizer.nval;

        streamTokenizer.nextToken();
        q = (int) streamTokenizer.nval;

        a = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            streamTokenizer.nextToken();
            a[i] = (int) streamTokenizer.nval;
        }

        b = new int[1100000];
        bIndex = new int[m + 2];
        greater = new int[m + 1];
        int index = 1, temp;
        for (int i = 1; i <= m; i++) {
            streamTokenizer.nextToken();
            temp = (int) streamTokenizer.nval;
            bIndex[i] = index;
            for (int j = 1; j <= temp; j++) {
                streamTokenizer.nextToken();
                b[index] = (int) streamTokenizer.nval;
                if (b[index] > a[1]) {
                    greater[i]++;
                }
                index++;
            }
        }
        bIndex[m + 1] = index;

        remain = new int[m + 1];
        temp = 1;
        for (int i = 2; i <= n; i++) {
            if (a[i] > a[1]) {
                temp++;
            }
        }
        for (int i = 1; i <= m; i++) {
            remain[i] = temp - (n - (bIndex[i + 1] - bIndex[i]));
            temp += greater[i];
        }

        initialTree();

        int x, y, z;
        // temp是主角的能力值
        temp = a[1];
        for (int i = 1; i <= q; i++) {
            streamTokenizer.nextToken();
            x = (int) streamTokenizer.nval;

            streamTokenizer.nextToken();
            y = (int) streamTokenizer.nval;

            streamTokenizer.nextToken();
            z = (int) streamTokenizer.nval;

            if (x < m) {
                int origin = b[bIndex[x] + y - 1];
                if (origin < temp && z > temp) {
                    add(1, 1, x + 1, m);
                } else if (origin > temp && z < temp) {
                    add(-1, 1, x + 1, m);
                }
                b[bIndex[x] + y - 1] = z;
            }

            System.out.println(get() > 0 ? 0 : 1);
        }

    }

    public static void initialTree() {
        max = new int[4 * m];
        lazy = new int[4 * m];
        leftIndex = new int[4 * m];
        rightIndex = new int[4 * m];
        initialTreeInternal(1, 1, m);
    }

    public static void initialTreeInternal(int index, int l, int r) {
        leftIndex[index] = l;
        rightIndex[index] = r;
        if (l == r) {
            max[index] = remain[l];
            return;
        }
        initialTreeInternal(2 * index, l, (l + r) / 2);
        initialTreeInternal(2 * index + 1, (l + r) / 2 + 1, r);
        max[index] = Math.max(max[2 * index], max[2 * index + 1]);
    }

    public static void add(int value, int index, int l, int r) {
        // 刚好
        if (leftIndex[index] == l && rightIndex[index] == r) {
            lazy[index] += value;
            max[index] += value;
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
    }

    public static int get() {
        return max[1];
    }

}
