import java.util.Scanner;
import java.io.File;
public class Runner {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("No input file given. Exiting.");
            return;
        }
        try {
            Scanner input = new Scanner(new File(args[0]));
            String line = input.nextLine();
            input.close();
            String[] nums = line.split(",");
            int[] code = new int[nums.length];
            for (int i = 0; i < nums.length; i++) {
                code[i] = Integer.parseInt(nums[i]);
            }
            new Intcode(code).process();
        } catch (Exception e) {
            System.out.println("Error happened:");
            e.printStackTrace();
            return;
        }
    }
}