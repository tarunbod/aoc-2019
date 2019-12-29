import java.io.File;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

class PipeIntcode extends Intcode {
    PipeIntcode(int[] ins) {
        super(ins);
    }

    @Override
    public int input() {
        return Main.getInput();
    }

    @Override
    public void output(int o) {
        Main.setOutput(o);
    }
}

public class Main {
    private static int[] code;

    private static List<Integer> inputs;

    private static int lastOutput;

    public static int getInput() {
        int x = inputs.remove(0);
        return x;
    }

    public static void setOutput(int o) {
        lastOutput = o;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No input file given. Exiting.");
            return;
        }
        inputs = new ArrayList<>();
        try {
            Scanner input = new Scanner(new File(args[0]));
            String line = input.nextLine();
            input.close();
            String[] nums = line.split(",");
            code = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                code[i] = Integer.parseInt(nums[i]);
            }
        } catch (Exception e) {
            System.out.println("Error happened:");
            e.printStackTrace();
            return;
        }

        System.out.println(getMaxOutput());
    }

    private static int getMaxOutput() {
        int max = -1;
        for (int[] sequence : allPhaseSequences) {
            int output = run(sequence);
            if (max == -1 || output > max) {
                max = output;
            }
        }
        return max;
    }

    private static int getMaxOutputFeedback() {
        int max = -1;
        for (int[] sequence : allPhaseSequences) {
            int output = runFeedback(sequence);
            if (max == -1 || output > max) {
                max = output;
            }
        }
        return max;
    }

    private static int run(int[] phases) {
        PipeIntcode ic1 = new PipeIntcode(code);

        inputs.clear();
        inputs.add(phases[0]);
        inputs.add(0);
        ic1.process();
        inputs.add(phases[1]);
        inputs.add(lastOutput);
        ic1.reset();
        ic1.process();
        inputs.add(phases[2]);
        inputs.add(lastOutput);
        ic1.reset();
        ic1.process();
        inputs.add(phases[3]);
        inputs.add(lastOutput);
        ic1.reset();
        ic1.process();
        inputs.add(phases[4]);
        inputs.add(lastOutput);
        ic1.reset();
        ic1.process();
        return lastOutput;
    }

    private static int runFeedback(int[] phases) {
        PipeIntcode ic1 = new PipeIntcode(code);
        PipeIntcode ic2 = new PipeIntcode(code);
        PipeIntcode ic3 = new PipeIntcode(code);
        PipeIntcode ic4 = new PipeIntcode(code);
        PipeIntcode ic5 = new PipeIntcode(code);

        return 0;
    }

    private static int[][] allPhaseSequences =
    {
        {0, 1, 2, 3, 4},
        {0, 1, 2, 4, 3},
        {0, 1, 3, 2, 4},
        {0, 1, 3, 4, 2},
        {0, 1, 4, 2, 3},
        {0, 1, 4, 3, 2},
        {0, 2, 1, 3, 4},
        {0, 2, 1, 4, 3},
        {0, 2, 3, 1, 4},
        {0, 2, 3, 4, 1},
        {0, 2, 4, 1, 3},
        {0, 2, 4, 3, 1},
        {0, 3, 1, 2, 4},
        {0, 3, 1, 4, 2},
        {0, 3, 2, 1, 4},
        {0, 3, 2, 4, 1},
        {0, 3, 4, 1, 2},
        {0, 3, 4, 2, 1},
        {0, 4, 1, 2, 3},
        {0, 4, 1, 3, 2},
        {0, 4, 2, 1, 3},
        {0, 4, 2, 3, 1},
        {0, 4, 3, 1, 2},
        {0, 4, 3, 2, 1},
        {1, 0, 2, 3, 4},
        {1, 0, 2, 4, 3},
        {1, 0, 3, 2, 4},
        {1, 0, 3, 4, 2},
        {1, 0, 4, 2, 3},
        {1, 0, 4, 3, 2},
        {1, 2, 0, 3, 4},
        {1, 2, 0, 4, 3},
        {1, 2, 3, 0, 4},
        {1, 2, 3, 4, 0},
        {1, 2, 4, 0, 3},
        {1, 2, 4, 3, 0},
        {1, 3, 0, 2, 4},
        {1, 3, 0, 4, 2},
        {1, 3, 2, 0, 4},
        {1, 3, 2, 4, 0},
        {1, 3, 4, 0, 2},
        {1, 3, 4, 2, 0},
        {1, 4, 0, 2, 3},
        {1, 4, 0, 3, 2},
        {1, 4, 2, 0, 3},
        {1, 4, 2, 3, 0},
        {1, 4, 3, 0, 2},
        {1, 4, 3, 2, 0},
        {2, 0, 1, 3, 4},
        {2, 0, 1, 4, 3},
        {2, 0, 3, 1, 4},
        {2, 0, 3, 4, 1},
        {2, 0, 4, 1, 3},
        {2, 0, 4, 3, 1},
        {2, 1, 0, 3, 4},
        {2, 1, 0, 4, 3},
        {2, 1, 3, 0, 4},
        {2, 1, 3, 4, 0},
        {2, 1, 4, 0, 3},
        {2, 1, 4, 3, 0},
        {2, 3, 0, 1, 4},
        {2, 3, 0, 4, 1},
        {2, 3, 1, 0, 4},
        {2, 3, 1, 4, 0},
        {2, 3, 4, 0, 1},
        {2, 3, 4, 1, 0},
        {2, 4, 0, 1, 3},
        {2, 4, 0, 3, 1},
        {2, 4, 1, 0, 3},
        {2, 4, 1, 3, 0},
        {2, 4, 3, 0, 1},
        {2, 4, 3, 1, 0},
        {3, 0, 1, 2, 4},
        {3, 0, 1, 4, 2},
        {3, 0, 2, 1, 4},
        {3, 0, 2, 4, 1},
        {3, 0, 4, 1, 2},
        {3, 0, 4, 2, 1},
        {3, 1, 0, 2, 4},
        {3, 1, 0, 4, 2},
        {3, 1, 2, 0, 4},
        {3, 1, 2, 4, 0},
        {3, 1, 4, 0, 2},
        {3, 1, 4, 2, 0},
        {3, 2, 0, 1, 4},
        {3, 2, 0, 4, 1},
        {3, 2, 1, 0, 4},
        {3, 2, 1, 4, 0},
        {3, 2, 4, 0, 1},
        {3, 2, 4, 1, 0},
        {3, 4, 0, 1, 2},
        {3, 4, 0, 2, 1},
        {3, 4, 1, 0, 2},
        {3, 4, 1, 2, 0},
        {3, 4, 2, 0, 1},
        {3, 4, 2, 1, 0},
        {4, 0, 1, 2, 3},
        {4, 0, 1, 3, 2},
        {4, 0, 2, 1, 3},
        {4, 0, 2, 3, 1},
        {4, 0, 3, 1, 2},
        {4, 0, 3, 2, 1},
        {4, 1, 0, 2, 3},
        {4, 1, 0, 3, 2},
        {4, 1, 2, 0, 3},
        {4, 1, 2, 3, 0},
        {4, 1, 3, 0, 2},
        {4, 1, 3, 2, 0},
        {4, 2, 0, 1, 3},
        {4, 2, 0, 3, 1},
        {4, 2, 1, 0, 3},
        {4, 2, 1, 3, 0},
        {4, 2, 3, 0, 1},
        {4, 2, 3, 1, 0},
        {4, 3, 0, 1, 2},
        {4, 3, 0, 2, 1},
        {4, 3, 1, 0, 2},
        {4, 3, 1, 2, 0},
        {4, 3, 2, 0, 1},
        {4, 3, 2, 1, 0}
    };
}