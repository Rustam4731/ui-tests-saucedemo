package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class ProductsPage {
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(className = "title")
    private WebElement pageTitle;

    @FindBy(className = "inventory_item")
    private List<WebElement> productItems;

    @FindBy(css = ".shopping_cart_link")
    private WebElement cartIcon;

    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;

    @FindBy(css = "[data-test='add-to-cart-sauce-labs-backpack']")
    private WebElement addToCartBackpackButton;

    @FindBy(css = "[data-test='remove-sauce-labs-backpack']")
    private WebElement removeBackpackButton;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public String getPageTitle() {
        wait.until(ExpectedConditions.visibilityOf(pageTitle));
        return pageTitle.getText();
    }

    public void addBackpackToCart() {
        wait.until(ExpectedConditions.elementToBeClickable(addToCartBackpackButton));
        addToCartBackpackButton.click();
    }

    public void removeBackpackFromCart() {
        wait.until(ExpectedConditions.elementToBeClickable(removeBackpackButton));
        removeBackpackButton.click();
    }

    public int getCartItemsCount() {
        try {
            wait.until(ExpectedConditions.visibilityOf(cartBadge));
            return Integer.parseInt(cartBadge.getText());
        } catch (Exception e) {
            return 0;
        }
    }

    public void openCart() {
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon));
        cartIcon.click();
    }

    public int getProductsCount() {
        wait.until(ExpectedConditions.visibilityOfAllElements(productItems));
        return productItems.size();
    }
}