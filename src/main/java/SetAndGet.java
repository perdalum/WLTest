import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;
import com.wolfram.jlink.MyComplex;

import java.util.Date;

public class SetAndGet {
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
            ml.discardAnswer();

            // Where are we?
            System.out.println("CWD: " + ml.evaluateToOutputForm("Directory[]",0));

            // Okay, try to load some definitions from a Wolfram Language package
            ml.evaluate("<<src/main/resources/aPackage.wl");
            ml.discardAnswer();

            /* c is a classifier defined thus
             * trainingset={1->"A",2->"A",3.5->"B",4->"B",10->"C",9->"C"};
             * c=Classify[trainingset]
             */
            System.out.println("4.3 corresponds to " + ml.evaluateToOutputForm("c[4.3]",0));

            /*
             * Try to set a symbol to a value and create a function
             */
            ml.evaluateToOutputForm("a=12;",0);
            ml.evaluateToOutputForm("f[x_]:=Exp[-I x]",0); // this evaluates to a complex number

            // calculate 2 + Exp[-I 2 Pi] using previously defined symbols
            String result = ml.evaluateToInputForm("N[a+f[2.1 Pi]]",0);
            System.out.println("a + f[2.1 π] = " + result);

        } catch (MathLinkException e) {
            System.out.println("MathLinkException or InterruptedException occurred: " + e.getMessage());
        } finally {
            ml.close();
        }
    }
}