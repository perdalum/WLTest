import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

import java.util.Date;

public class JSONTest {
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
             */
            ml.discardAnswer();

            // Generate som JSON
            System.out.println("Some JSON: " +
                    ml.evaluateToOutputForm(
                            "ExportString[{\"a\" -> {1, 2, 3}, \"b\" -> 12.34, \"c\" -> Pi // N}, \"JSON\"]",
                            0
                    )
            );
        } catch (MathLinkException e) {
            System.out.println("MathLinkException occurred: " + e.getMessage());
        } finally {
            ml.close();
        }
    }
}