import com.wolfram.jlink.*;

import java.util.Date;

public class RandomWords {
    /*
     * This program is heavily inspired by the example at
     * https://reference.wolfram.com/language/JLink/tutorial/WritingJavaProgramsThatUseTheWolframLanguage.html#30556
     */
    public static void main(String[] argv) {

        if (argv.length == 0) {
            argv = new String[] {
                    "-linkmode",
                    "launch",
                    "-linkname",
                    "/Applications/Mathematica.app/Contents/MacOS/MathKernel -mathlink"
            };
        }
        KernelLink ml = null;

        System.out.println("Create kernel link @ " + new Date());
        try {
            ml = MathLinkFactory.createKernelLink(argv);
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            return;
        }

        try {
            /*
             * Get rid of the initial InputNamePacket the kernel will send
             * when it is launched.
             * pmd: As I understand it, this forces the kernel to load before our
             * first evaluation.
             */
            System.out.println("Load kernel @ " + new Date());
            ml.discardAnswer();
            System.out.println("Kernel loaded @ " + new Date());

            //ml.evaluate("<<MyPackage.m");
            //ml.discardAnswer();

            /*
             * Prøv at sætte en variabel og så læse den i et senere kald.
             */

            int minutes = 10*60;   // Minutes to run
            int tickPerMinute = 6; // words per minute
            System.out.println(
                    "Write a random word for "+minutes+" minutes with "+tickPerMinute+" word pr minute."
            );
            for (int i = 0; i < tickPerMinute*minutes; i++) {
                String word = ml.evaluateToOutputForm("RandomWord[]",0);
                System.out.println(word);
                Thread.sleep(60000/tickPerMinute); // pause for a bit
            }

            /*
            System.out.println("Calculate 2+2 @ " + new Date());
            ml.evaluate("2+2");
            ml.waitForAnswer();
            int result = ml.getInteger();
            System.out.println("2 + 2 = " + result + " @ " + new Date());

            String s = ml.evaluateToOutputForm("ExportString[{1,2,3},\"JSON\"]", 0);
            System.out.println("JSON representation of list {1,2,3} = " + s + " @ " + new Date());

            String listLength = ml.evaluateToOutputForm("Length[{1,2,3}]", 0);
            System.out.println("4 + 4 = " + listLength);

            // Here's how to send the same input, but not as a string:
            ml.putFunction("EvaluatePacket", 1);
            ml.putFunction("Plus", 2);
            ml.put(3);
            ml.put(3);
            ml.endPacket();
            ml.waitForAnswer();
            result = ml.getInteger();
            System.out.println("3 + 3 = " + result);

            // If you want the result back as a string, use evaluateToInputForm
            // or evaluateToOutputForm. The second arg for either is the
            // requested page width for formatting the string. Pass 0 for
            // PageWidth->Infinity. These methods get the result in one
            // step--no need to call waitForAnswer.
            String strResult = ml.evaluateToOutputForm("4+4", 0);
            System.out.println("4 + 4 = " + strResult);
            */
        } catch (MathLinkException | InterruptedException e) {
            System.out.println("MathLinkException or InterruptedException occurred: " + e.getMessage());
        } finally {
            ml.close();
        }
    }
}