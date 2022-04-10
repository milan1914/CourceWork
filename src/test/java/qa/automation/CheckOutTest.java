package qa.automation;

import base.TestUtil;
import com.opencsv.exceptions.CsvException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.*;
import utils.CsvHelper;

import java.io.IOException;

public class CheckOutTest extends TestUtil {
    @DataProvider(name = "csvUserList")
    public static Object[][] readUsersFromCsvFile() throws IOException, CsvException {
        return CsvHelper.readCsvFile("src/test/resources/users.csv");
    }

    @Test(dataProvider = "csvUserList")
    public void successfulLoginTest(String userName, String password){

        LoginPage loginPage = new LoginPage(driver);
        ProductsPage productsPage = loginPage.login(userName, password);

        productsPage.addItemToTheCart("fleece-jacket");
        productsPage.enterIntoCart();

        CartPage cartPage = new CartPage(driver);
        cartPage.checkout();

        CheckoutInformationPage checkoutInformationPage = new CheckoutInformationPage(driver);
        checkoutInformationPage.fillCheckoutInfo("Milan", "Zhekov", "1914");

        CompletedOrderPage completedOrderPage = new CompletedOrderPage(driver);
        Assert.assertTrue(completedOrderPage.getCheckoutHeader());


    }
}
