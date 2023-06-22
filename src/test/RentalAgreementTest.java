import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

public class RentalAgreementTest {
    private static final Tool TOOL = new Tool("LADW", ToolType.LADDER, "Werner");
    private static final LocalDate CHECKOUT_DATE = LocalDate.of(2023, 6, 15);
    private static final LocalDate DUE_DATE = LocalDate.of(2023, 6, 20);
    private static final BigDecimal DAILY_RENTAL_CHARGE = BigDecimal.valueOf(1.99);
    private static final BigDecimal PRE_DISCOUNT_CHARGE = BigDecimal.valueOf(9.95);
    private static final BigDecimal DISCOUNT_AMOUNT = BigDecimal.valueOf(1.00);
    private static final BigDecimal FINAL_CHARGE = BigDecimal.valueOf(8.95);

    @Test
    void testToString() {
        RentalAgreement agreement = new RentalAgreement(TOOL, 5, CHECKOUT_DATE, DUE_DATE,
                DAILY_RENTAL_CHARGE, 5, PRE_DISCOUNT_CHARGE, 10, DISCOUNT_AMOUNT, FINAL_CHARGE);

        String expectedOutput = """
                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Rental days: 5
                Check out date: 06/15/23
                Due date: 06/20/23
                Daily rental charge: $1.99
                Charge days: 5
                Pre-discount charge: $9.95
                Discount percent: 10%
                Discount amount: $1.00
                Final charge: $8.95""";

        Assertions.assertEquals(expectedOutput, agreement.toString());
    }

    @Test
    void testPrintRentalAgreement() {
        RentalAgreement agreement = new RentalAgreement(TOOL, 5, CHECKOUT_DATE, DUE_DATE,
                DAILY_RENTAL_CHARGE, 5, PRE_DISCOUNT_CHARGE, 10, DISCOUNT_AMOUNT, FINAL_CHARGE);

        // Capture console output
        ConsoleOutputCapture capture = new ConsoleOutputCapture();
        capture.start();

        agreement.printRentalAgreement();

        capture.stop();
        String consoleOutput = capture.getOutput();

        String expectedOutput = """
                Tool code: LADW
                Tool type: Ladder
                Tool brand: Werner
                Rental days: 5
                Check out date: 06/15/23
                Due date: 06/20/23
                Daily rental charge: $1.99
                Charge days: 5
                Pre-discount charge: $9.95
                Discount percent: 10%
                Discount amount: $1.00
                Final charge: $8.95
                """;

        Assertions.assertEquals(expectedOutput, consoleOutput);
    }
}

