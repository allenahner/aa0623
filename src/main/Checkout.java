import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;

public class Checkout {
    /**
     * Formatter for parsing and formatting dates.
     */
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yy");

    /**
     * Checks out a tool and generates a rental agreement.
     *
     * @param toolCode      The code of the tool to be rented.
     * @param rentalDays    The number of days for which the tool is rented.
     * @param discountPercent  The discount percentage to be applied.
     * @param checkoutDate  The date the tool is checked out.
     * @return The rental agreement for the checked-out tool.
     * @throws IllegalArgumentException if rentalDays is less than 1 or discountPercent is not between 0 and 100.
     */
    public static RentalAgreement checkoutTool(String toolCode, int rentalDays, int discountPercent, String checkoutDate) {
        if (rentalDays < 1) {
            throw new IllegalArgumentException("Rental day count must be 1 or greater.");
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException("Discount percent must be between 0 and 100.");
        }

        Tool tool = getToolByCode(toolCode);
        LocalDate parsedCheckoutDate = LocalDate.parse(checkoutDate, formatter);
        LocalDate dueDate = parsedCheckoutDate.plusDays(rentalDays);
        int chargeDays = calculateChargeableDays(parsedCheckoutDate, dueDate, tool);
        BigDecimal preDiscountCharge = calculatePreDiscountCharge(chargeDays, tool);
        BigDecimal discountAmount = calculateDiscountAmount(preDiscountCharge, discountPercent);
        BigDecimal finalCharge = calculateFinalCharge(preDiscountCharge, discountAmount);

        return new RentalAgreement(tool, rentalDays, parsedCheckoutDate, dueDate, tool.toolType().getDailyCharge(),
                chargeDays, preDiscountCharge, discountPercent, discountAmount, finalCharge);
    }

    /**
     * Retrieves the tool based on its code.
     *
     * @param toolCode The code of the tool.
     * @return The tool with the specified code.
     * @throws IllegalArgumentException if the tool code is invalid.
     */
    private static Tool getToolByCode(String toolCode) {
        return switch (toolCode) {
            case "LADW" -> new Tool("LADW", ToolType.LADDER, "Werner");
            case "CHNS" -> new Tool("CHNS", ToolType.CHAINSAW, "Stihl");
            case "JAKD" -> new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt");
            case "JAKR" -> new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid");
            default -> throw new IllegalArgumentException("Invalid tool code: " + toolCode);
        };
    }

    /**
     * Calculates the number of chargeable days for a tool rental.
     *
     * @param startDate The start date of the rental.
     * @param endDate   The end date of the rental.
     * @param tool      The tool being rented.
     * @return The number of chargeable days.
     */
    private static int calculateChargeableDays(LocalDate startDate, LocalDate endDate, Tool tool) {
        int chargeableDays = 0;
        LocalDate date = startDate.plusDays(1); // Start from the day after the checkout date

        while (date.equals(endDate) || date.isBefore(endDate)) {
            if (isChargeable(date, tool.toolType())) {
                chargeableDays++;
            }
            date = date.plusDays(1);
        }

        return chargeableDays;
    }

    /**
     * Checks if a specific date is chargeable for the given tool type.
     *
     * @param date      The date to check.
     * @param toolType  The type of tool.
     * @return true if the date is chargeable, false otherwise.
     */
    private static boolean isChargeable(LocalDate date, ToolType toolType) {
        if (isWeekend(date) && toolType.isWeekendCharge()) {
            return true;
        }

        if (isHoliday(date) && toolType.isHolidayCharge()) {
            return true;
        }

        return !isWeekend(date) && !isHoliday(date);
    }

    /**
     * Checks if a date falls on a weekend (Saturday or Sunday).
     *
     * @param date The date to check.
     * @return true if the date is a weekend, false otherwise.
     */
    private static boolean isWeekend(LocalDate date) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY;
    }

    /**
     * Checks if a date is a holiday (Labor Day or observed Independence Day).
     *
     * @param date The date to check.
     * @return true if the date is a holiday, false otherwise.
     */
    private static boolean isHoliday(LocalDate date) {
        return isLaborDay(date) || isObservedIndependenceDay(date);
    }

    /**
     * Checks if a date is Labor Day.
     *
     * @param date The date to check.
     * @return true if the date is Labor Day, false otherwise.
     */
    private static boolean isLaborDay(LocalDate date) {
        LocalDate laborDay = LocalDate.of(date.getYear(), Month.SEPTEMBER, 1);

        while (laborDay.getDayOfWeek() != DayOfWeek.MONDAY) {
            laborDay = laborDay.plusDays(1);
        }

        return laborDay.equals(date);
    }

    /**
     * Checks if a date is the observed Independence Day.
     *
     * @param date The date to check.
     * @return true if the date is the observed Independence Day, false otherwise.
     */
    private static boolean isObservedIndependenceDay(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), Month.JULY, 4);
        DayOfWeek dayOfWeek = independenceDay.getDayOfWeek();

        LocalDate observedDate = independenceDay;
        if (dayOfWeek == DayOfWeek.SATURDAY) {
            observedDate = independenceDay.with(TemporalAdjusters.previous(DayOfWeek.FRIDAY));
        } else if (dayOfWeek == DayOfWeek.SUNDAY) {
            observedDate = independenceDay.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        }

        return observedDate.equals(date);
    }

    /**
     * Calculates the pre-discount charge for the rental.
     *
     * @param chargeDays The number of chargeable days.
     * @param tool       The rented tool.
     * @return The pre-discount charge amount.
     */
    private static BigDecimal calculatePreDiscountCharge(int chargeDays, Tool tool) {
        BigDecimal dailyCharge = tool.toolType().getDailyCharge();

        return dailyCharge.multiply(BigDecimal.valueOf(chargeDays)).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the discount amount based on the pre-discount charge and discount percentage.
     *
     * @param preDiscountCharge The pre-discount charge amount.
     * @param discountPercent   The discount percentage.
     * @return The discount amount.
     */
    private static BigDecimal calculateDiscountAmount(BigDecimal preDiscountCharge, int discountPercent) {
        BigDecimal discount = BigDecimal.valueOf(discountPercent).divide(BigDecimal.valueOf(100));

        return preDiscountCharge.multiply(discount).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Calculates the final charge after applying the discount.
     *
     * @param preDiscountCharge The pre-discount charge amount.
     * @param discountAmount    The discount amount.
     * @return The final charge amount.
     */
    private static BigDecimal calculateFinalCharge(BigDecimal preDiscountCharge, BigDecimal discountAmount) {
        return preDiscountCharge.subtract(discountAmount).setScale(2, RoundingMode.HALF_UP);
    }
}
