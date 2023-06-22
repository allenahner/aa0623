import java.math.BigDecimal;

/**
 * Represents the type of tool with its associated properties.
 */
public enum ToolType {
    LADDER("Ladder", BigDecimal.valueOf(1.99), true, true, false),
    CHAINSAW("Chainsaw", BigDecimal.valueOf(1.49), true, false, true),
    JACKHAMMER("Jackhammer", BigDecimal.valueOf(2.99), true, false, false);

    private final String name;
    private final BigDecimal dailyCharge;
    private final boolean weekdayCharge;
    private final boolean weekendCharge;
    private final boolean holidayCharge;

    /**
     * Constructs a ToolType with the specified properties.
     *
     * @param name           the name of the tool type
     * @param dailyCharge    the daily charge for renting the tool
     * @param weekdayCharge  indicates if there is a charge on weekdays
     * @param weekendCharge  indicates if there is a charge on weekends
     * @param holidayCharge  indicates if there is a charge on holidays
     */
    ToolType(String name, BigDecimal dailyCharge, boolean weekdayCharge,
             boolean weekendCharge, boolean holidayCharge) {
        this.name = name;
        this.dailyCharge = dailyCharge;
        this.weekdayCharge = weekdayCharge;
        this.weekendCharge = weekendCharge;
        this.holidayCharge = holidayCharge;
    }

    /**
     * Returns the name of the tool type.
     *
     * @return the name of the tool type
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the daily charge for renting the tool.
     *
     * @return the daily charge for renting the tool
     */
    public BigDecimal getDailyCharge() {
        return dailyCharge;
    }

    /**
     * Checks if there is a charge on weekdays.
     *
     * @return {@code true} if there is a charge on weekdays, {@code false} otherwise
     */
    public boolean isWeekdayCharge() {
        return weekdayCharge;
    }

    /**
     * Checks if there is a charge on weekends.
     *
     * @return {@code true} if there is a charge on weekends, {@code false} otherwise
     */
    public boolean isWeekendCharge() {
        return weekendCharge;
    }

    /**
     * Checks if there is a charge on holidays.
     *
     * @return {@code true} if there is a charge on holidays, {@code false} otherwise
     */
    public boolean isHolidayCharge() {
        return holidayCharge;
    }
}
