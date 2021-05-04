import com.wolfram.jlink.KernelLink;
import com.wolfram.jlink.MathLinkException;
import com.wolfram.jlink.MathLinkFactory;

import java.util.Date;

public class DANERTest {
    /*
     * This program is heavily inspired by the example at
     * https://reference.wolfram.com/language/JLink/tutorial/WritingJavaProgramsThatUseTheWolframLanguage.html#30556
     */
    public static void main(String[] argv) {

        // CONFIGURATION
        String mathKernelPath = "/Applications/Mathematica.app/Contents/MacOS/MathKernel";
        // @ miaplacidus
        //mathKernelPath = "/opt/wolframengine/12.2/SystemFiles/Kernel/Binaries/Linux-x86-64/WolframKernel";
        if (argv.length == 0) {
            argv = new String[]{
                    "-linkmode",
                    "launch",
                    "-linkname",
                    mathKernelPath + " -mathlink"
            };
        }
        KernelLink ml = null;

        // CREATE A LINK TO A LOCAL WOLFRAM KERNEL
        System.out.println("Create kernel link @ " + new Date());
        try {
            ml = MathLinkFactory.createKernelLink(argv);
        } catch (MathLinkException e) {
            System.out.println("Fatal error opening link: " + e.getMessage());
            return;
        }

        try {
            // ********************************************************************
            // INITIALIZE THE KERNEL
            /*
             * Get rid of the initial InputNamePacket the kernel will send
             * when it is launched.
             * pmd: As I understand it, this forces the kernel to load before our
             * first evaluation.
             */
            ml.discardAnswer();

            // Okay, try to load some definitions from a Wolfram Language package
            ml.evaluate("<<src/main/resources/DANER.m");
            ml.discardAnswer();
            // DONE INITIALUZATION
            // ********************************************************************


        } catch (MathLinkException e) {
            System.out.println("MathLinkException occurred: " + e.getMessage());
        }

        // warm up (~9s on a laptop)
        System.out.println("Warm up with a test image - takes a few seconds:");
        System.out.println(ml.evaluateToOutputForm("findSimilarFaces[\"http://17053.dk/pmd.png\",2]", 0));

        // Now for the real test (< 1s on a laptop)
        System.out.println("Now for the real test - takes less that a second:");
        System.out.println(ml.evaluateToOutputForm("findSimilarFaces[\"http://17053.dk/pmd.png\",2]", 0));

        for (int i = 0; i <20; i++) {
            System.out.println(ml.evaluateToOutputForm("findSimilarFaces[\"http://17053.dk/pmd.png\",2]", 0));
        }

        // Let's leave the kernel
        System.out.println("## Say Goodbye 👋");
        ml.close();
    }
}