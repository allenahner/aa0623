import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

class CheckoutTest {
    @Test
    void test1() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> Checkout.checkoutTool("JAKR", 5, 101, "9/3/15"), "Discount percent must be between 0 and 100.");
    }

    @Test
    void test2() {
        RentalAgreement agreement = Checkout.checkoutTool("LADW", 3, 10, "7/2/20");
        Assertions.assertEquals(3, agreement.rentalDays());
        Assertions.assertEquals("07/02/20", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("07/05/20", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("1.99"), agreement.dailyRentalCharge());
        Assertions.assertEquals(2, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("3.98"), agreement.preDiscountCharge());
        Assertions.assertEquals(10, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("0.40"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("3.58"), agreement.finalCharge());
    }

    @Test
    void test3() {
        RentalAgreement agreement = Checkout.checkoutTool("CHNS", 5, 25, "7/2/15");
        Assertions.assertEquals(5, agreement.rentalDays());
        Assertions.assertEquals("07/02/15", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("07/07/15", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("1.49"), agreement.dailyRentalCharge());
        Assertions.assertEquals(3, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("4.47"), agreement.preDiscountCharge());
        Assertions.assertEquals(25, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("1.12"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("3.35"), agreement.finalCharge());
    }

    @Test
    void test4() {
        RentalAgreement agreement = Checkout.checkoutTool("JAKD", 6, 0, "9/03/15");
        Assertions.assertEquals(6, agreement.rentalDays());
        Assertions.assertEquals("09/03/15", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("09/09/15", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.dailyRentalCharge());
        Assertions.assertEquals(3, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("8.97"), agreement.preDiscountCharge());
        Assertions.assertEquals(0, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("0.00"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("8.97"), agreement.finalCharge());
    }

    @Test
    void test5() {
        RentalAgreement agreement = Checkout.checkoutTool("JAKR", 9, 0, "7/02/15");
        Assertions.assertEquals(9, agreement.rentalDays());
        Assertions.assertEquals("07/02/15", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("07/11/15", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.dailyRentalCharge());
        Assertions.assertEquals(5, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("14.95"), agreement.preDiscountCharge());
        Assertions.assertEquals(0, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("0.00"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("14.95"), agreement.finalCharge());
    }

    @Test
    void test6() {
        RentalAgreement agreement = Checkout.checkoutTool("JAKR", 4, 50, "7/02/20");
        Assertions.assertEquals(4, agreement.rentalDays());
        Assertions.assertEquals("07/02/20", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("07/06/20", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.dailyRentalCharge());
        Assertions.assertEquals(1, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.preDiscountCharge());
        Assertions.assertEquals(50, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("1.50"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("1.49"), agreement.finalCharge());
    }

    @Test
    void test7() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> Checkout.checkoutTool("JAKR", 0, 20, "9/3/15"), "Rental day count must be 1 or greater.");
    }

    @Test
    void test8() {
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> Checkout.checkoutTool("ILGL", 1, 20, "9/3/15"), "Invalid tool code: ILGL");
    }

    @Test
    void test9() {
        RentalAgreement agreement = Checkout.checkoutTool("JAKR", 4, 50, "7/02/21");
        Assertions.assertEquals(4, agreement.rentalDays());
        Assertions.assertEquals("07/02/21", agreement.checkoutDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals("07/06/21", agreement.dueDate().format(DateTimeFormatter.ofPattern("MM/dd/yy")));
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.dailyRentalCharge());
        Assertions.assertEquals(1, agreement.chargeDays());
        Assertions.assertEquals(new BigDecimal("2.99"), agreement.preDiscountCharge());
        Assertions.assertEquals(50, agreement.discountPercent());
        Assertions.assertEquals(new BigDecimal("1.50"), agreement.discountAmount());
        Assertions.assertEquals(new BigDecimal("1.49"), agreement.finalCharge());
    }
}
