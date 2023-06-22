import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

public class ToolTypeTest {
    @Test
    void testGetName() {
        Assertions.assertEquals("Ladder", ToolType.LADDER.getName());
        Assertions.assertEquals("Chainsaw", ToolType.CHAINSAW.getName());
        Assertions.assertEquals("Jackhammer", ToolType.JACKHAMMER.getName());
    }

    @Test
    void testGetDailyCharge() {
        Assertions.assertEquals(BigDecimal.valueOf(1.99), ToolType.LADDER.getDailyCharge());
        Assertions.assertEquals(BigDecimal.valueOf(1.49), ToolType.CHAINSAW.getDailyCharge());
        Assertions.assertEquals(BigDecimal.valueOf(2.99), ToolType.JACKHAMMER.getDailyCharge());
    }

    @Test
    void testIsWeekdayCharge() {
        Assertions.assertTrue(ToolType.LADDER.isWeekdayCharge());
        Assertions.assertTrue(ToolType.CHAINSAW.isWeekdayCharge());
        Assertions.assertTrue(ToolType.JACKHAMMER.isWeekdayCharge());
    }

    @Test
    void testIsWeekendCharge() {
        Assertions.assertTrue(ToolType.LADDER.isWeekendCharge());
        Assertions.assertFalse(ToolType.CHAINSAW.isWeekendCharge());
        Assertions.assertFalse(ToolType.JACKHAMMER.isWeekendCharge());
    }

    @Test
    void testIsHolidayCharge() {
        Assertions.assertFalse(ToolType.LADDER.isHolidayCharge());
        Assertions.assertTrue(ToolType.CHAINSAW.isHolidayCharge());
        Assertions.assertFalse(ToolType.JACKHAMMER.isHolidayCharge());
    }
}

