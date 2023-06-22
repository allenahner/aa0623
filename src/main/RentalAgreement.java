import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.StringJoiner;

/**
 * Represents a rental agreement for a tool.
 */
public record RentalAgreement(Tool tool, int rentalDays, LocalDate checkoutDate, LocalDate dueDate,
                              BigDecimal dailyRentalCharge, int chargeDays, BigDecimal preDiscountCharge,
                              int discountPercent, BigDecimal discountAmount, BigDecimal finalCharge) {

    /**
     * Returns a string representation of the rental agreement.
     *
     * @return The rental agreement as a formatted string.
     */
    public String toString(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yy");
        DecimalFormat currencyFormatter = new DecimalFormat("$0.00");
        NumberFormat percentFormatter = NumberFormat.getPercentInstance();

        StringJoiner agreement = new StringJoiner("\n");
        agreement.add("Tool code: " + tool.toolCode());
        agreement.add("Tool type: " + tool.toolType().getName());
        agreement.add("Tool brand: " + tool.brand());
        agreement.add("Rental days: " + rentalDays);
        agreement.add("Check out date: " + checkoutDate.format(dateFormatter));
        agreement.add("Due date: " + dueDate.format(dateFormatter));
        agreement.add("Daily rental charge: " + currencyFormatter.format(dailyRentalCharge));
        agreement.add("Charge days: " + chargeDays);
        agreement.add("Pre-discount charge: " + currencyFormatter.format(preDiscountCharge));
        agreement.add("Discount percent: " + percentFormatter.format(discountPercent/100.0));
        agreement.add("Discount amount: " + currencyFormatter.format(discountAmount));
        agreement.add("Final charge: " + currencyFormatter.format(finalCharge));

        return agreement.toString();
    }

    /**
     * Prints the rental agreement to the console.
     */
    public void printRentalAgreement() {
        System.out.println(this);
    }
}
