package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;
import pages.ProductsPage;

public class SauceDemoTests extends BaseTest {

    @Test(priority = 1)
    public void testSuccessfulLogin() {
        LoginPage loginPage = new LoginPage(driver);

        // Шаги теста
        loginPage.login("standard_user", "secret_sauce");

        // Проверки
        ProductsPage productsPage = new ProductsPage(driver);
        String actualTitle = productsPage.getPageTitle();

        Assert.assertTrue(driver.getCurrentUrl().contains("/inventory.html"),
                "URL должен содержать /inventory.html после успешного логина");
        Assert.assertEquals(actualTitle, "Products",
                "Заголовок страницы должен быть 'Products'");
        Assert.assertTrue(productsPage.getProductsCount() > 0,
                "На странице должны отображаться товары");
    }

    @Test(priority = 2)
    public void testLockedOutUserLogin() {
        LoginPage loginPage = new LoginPage(driver);

        // Шаги теста
        loginPage.login("locked_out_user", "secret_sauce");

        // Проверки
        String errorMessage = loginPage.getErrorMessage();
        Assert.assertTrue(loginPage.isErrorMessageDisplayed(),
                "Сообщение об ошибке должно отображаться");
        Assert.assertTrue(errorMessage.contains("Epic sadface"),
                "Сообщение об ошибке должно содержать текст 'Epic sadface'");
        Assert.assertTrue(driver.getCurrentUrl().contains("saucedemo.com"),
                "После неудачного логина пользователь должен остаться на странице логина");
    }

    @Test(priority = 3)
    public void testAddProductToCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);

        // Шаги теста
        productsPage.addBackpackToCart();

        // Проверки
        int cartItemsCount = productsPage.getCartItemsCount();
        Assert.assertEquals(cartItemsCount, 1,
                "Счетчик корзины должен показывать 1 товар");
    }

    @Test(priority = 4)
    public void testRemoveProductFromCart() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver);

        // Добавляем товар
        productsPage.addBackpackToCart();

        // Удаляем товар
        productsPage.removeBackpackFromCart();

        // Проверки
        int cartItemsCount = productsPage.getCartItemsCount();
        Assert.assertEquals(cartItemsCount, 0,
                "Счетчик корзины должен показывать 0 товаров после удаления");
    }
}