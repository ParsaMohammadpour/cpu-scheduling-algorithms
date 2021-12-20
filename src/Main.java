import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        File inputDir = new File("TestCases/in");
        File correctOutDir = new File("TestCases/CorrectOutput");
        File myOutDir = new File("TestCases/myOutput");

        File[] input = inputDir.listFiles();
        for (int i = 0; i < input.length; i++) {
            Scanner scanner = new Scanner(input[i]);
            FileWriter writer = new FileWriter(myOutDir.getPath() + "\\" + input[i].getName());
            String[] firstLine = scanner.nextLine().split(" ");
            if (firstLine[0].equals("RR")) {
                RoundRobin roundRobin = new RoundRobin(scanner, writer, Integer.parseInt(firstLine[1]));
                roundRobin.start();
            } else if (firstLine[0].equals("SJF")) {
                SJF sjf = new SJF(scanner, writer);
                sjf.start();
            } else if (firstLine[0].equals("PR_noPREMP")) {
                NonPreemtivePriority nonpreemtive = new NonPreemtivePriority(scanner, writer);
                nonpreemtive.start();
            } else if (firstLine[0].equals("PR_withPREMP")) {
                PreemtivePriority preemtive = new PreemtivePriority(scanner, writer);
                preemtive.start();
            } else {
                System.out.println("Error!");
                System.out.println("No Such Algorithm!");
            }
        }

        File[] correctOut = correctOutDir.listFiles();
        File[] myOut = myOutDir.listFiles();
        outer:
        for (int i = 0; i < correctOut.length; i++) {
            Scanner s1 = new Scanner(correctOut[i]);
            Scanner s2 = new Scanner(myOut[i]);
            while (true) {
                if (s1.hasNextLine() ^ s2.hasNextLine()) {
                    System.out.println("output Path: " + myOut[i].getAbsolutePath() + " failed!");
                    continue outer;
                }
                String firstLine = ignoreWhiteSpaces(s1.nextLine()), secondLine = ignoreWhiteSpaces(s2.nextLine());
                if (!firstLine.equals(secondLine)) {
                    System.out.println("output Path: " + myOut[i].getAbsolutePath() + " failed!");
                    System.out.println("First Line: \t" + firstLine);
                    System.out.println("Second Line: \t" + secondLine);
                    continue outer;
                }
                if ((!s1.hasNextLine()) || (!s2.hasNextLine()))
                    break;
            }
            System.out.println("output Path: " + myOut[i].getAbsolutePath() + " passed:)");
        }
    }

    public static String ignoreWhiteSpaces(String s) {
        String out = "";
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case ' ':
                    continue;
                case '\n':
                    continue;
                case '\t':
                    continue;
                case '\f':
                    continue;
                default:
                    out += s.charAt(i);
            }
        }
        return out;
    }
}