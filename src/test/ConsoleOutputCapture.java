import java.io.ByteArrayOutputStream;
import java.io.PrintStream;


// Helper class to capture console output
public class ConsoleOutputCapture {
    private final PrintStream originalOut;
    private final ByteArrayOutputStream outputStream;

    public ConsoleOutputCapture() {
        originalOut = System.out;
        outputStream = new ByteArrayOutputStream();
    }

    public void start() {
        System.setOut(new PrintStream(outputStream));
    }

    public void stop() {
        System.setOut(originalOut);
    }

    public String getOutput() {
        return outputStream.toString();
    }
}