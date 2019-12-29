import java.io.File;
import java.util.Arrays;
import java.util.Scanner;

public class Intcode {
    private Scanner scanner;
    private int[] instruction_sizes = {
        0, // unused
        4, 4, 2, 2, 3, 3, 4, 4
    };
    private int[] code;
    private int[] ins;

    public Intcode(int[] code) {
        this.scanner = new Scanner(System.in);
        this.code = code;
        reset();
    }

    protected int input() {
        System.out.print("input: ");
        return scanner.nextInt();
    }

    protected void output(int o) {
        System.out.println(o);
    }

    public void reset() {
        this.ins = code.clone();
    }

    public int[] getState() {
        return ins;
    }

    public void process() {
        int i = 0;
        while (i < ins.length) {
            int opcode = ins[i];
            boolean failed = false;

            int op = (opcode % 100);
            if (op == 99) {
                break;
            }

            int p1 = 0, p2 = 0;
            try {
                p1 = (opcode % 1000) / 100 == 1 ? ins[i + 1] : ins[ins[i + 1]];
                p2 = (opcode % 10000) / 1000 == 1 ? ins[i + 2] : ins[ins[i + 2]];
            } catch (IndexOutOfBoundsException ignored) {}

            switch (op) {
                case 1: // add
                    ins[ins[i + 3]] = p1 + p2;
                    break;
                case 2: // multiply
                    ins[ins[i + 3]] = p1 * p2;
                    break;
                case 3: // input
                    ins[ins[i + 1]] = input();
                    break;
                case 4: // output
                    output(p1);
                    break;
                case 5: // jump if non-zero
                    if (p1 != 0) {
                        i = p2;
                        continue;
                    }
                    break;
                case 6: // jump if zero
                    if (p1 == 0) {
                        i = p2;
                        continue;
                    }
                    break;
                case 7: // less than
                    ins[ins[i + 3]] = p1 < p2 ? 1 : 0;
                    break;
                case 8: // equals
                    ins[ins[i + 3]] = p1 == p2 ? 1 : 0;
                    break;
                default:
                    failed = true;
                    break;
            }
            if (failed) {
                break;
            }

            i += instruction_sizes[op];
        }
    }
}
