package AutoSel1;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class AutomationExercise {

	static WebDriver driver;

	public static void main(String[] args) {
		
		// Set up the Chrome driver and go to the test page.
		System.setProperty("webdriver.chrome.driver", "C:\\Program Files\\Java\\chromedriver\\chromedriver.exe");
		ChromeAce ace = new ChromeAce();
		driver = new ChromeDriver();
		driver.get("https://www.automationexercise.com");
		
		// This method is used now since the user account has already been set up.
		SignIn();
		
		GetFirstItem();
		GetSecondItem();
		
		// If the user must re-register, then use these two methods instead of the "SignIn" method.
		// No need to move these, just uncomment these and comment the "SignIn" method.
	//	Signup();
	//	SignupInfo();
		
		Checkout();
		Payment();
				
		driver.quit();
	}
	
	// Selects an item on the testing site and puts it in the cart.
	private static void GetFirstItem() {

		driver.findElement(By.xpath("/html/body/section[2]/div[1]/div/div[1]/div/div[2]/div[1]/ul/li[1]/a")).click();
		driver.findElement(By.xpath("/html/body/section/div/div[2]/div[2]/div/div[3]/div/div[2]/ul/li/a")).click();
		WebElement amount =  driver.findElement(By.id("quantity"));
		amount.clear();
		amount.sendKeys("3");
		driver.findElement(By.cssSelector("button[class='btn btn-default cart']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[class='btn btn-success close-modal btn-block']")));
		element.click();
	}
	
	// Selects a different item on the testing site and puts it in the cart.
	private static void GetSecondItem() {
		
		driver.findElement(By.partialLinkText("Home")).click();
		driver.findElement(By.xpath("/html/body/section[2]/div[1]/div/div[2]/div[1]/div[12]/div/div[2]/ul/li/a")).click();
		WebElement amount =  driver.findElement(By.id("quantity"));
		amount.clear();
		amount.sendKeys("2");
		driver.findElement(By.cssSelector("button[class='btn btn-default cart']")).click();
		
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cartModal\"]/div/div/div[2]/p[2]/a")));
		element.click();
	}
	
	// Sign into a pre-existing account.
	private static void SignIn() {

		driver.findElement(By.partialLinkText("Signup")).click();
		driver.findElement(By.cssSelector("input[data-qa='login-email']")).sendKeys("me@gmail.com");
		driver.findElement(By.cssSelector("input[data-qa='login-password']")).sendKeys("me1234ME");
		driver.findElement(By.cssSelector("button[data-qa='login-button")).click();
	}

	// Register for an account.
	private static void Signup() {

		driver.findElement(By.partialLinkText("Signup")).click();
		driver.findElement(By.cssSelector("input[name='name']")).sendKeys("Me");
		driver.findElement(By.cssSelector("input[data-qa='signup-email']")).sendKeys("me@gmail.com");
		driver.findElement(By.cssSelector("button[data-qa='signup-button")).click();
	}
	
	// Input user info in order to register for an account.
	private static void SignupInfo() {

		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.id("id_gender1")));
		if (!element.isSelected()) {
			element.click();
		}
		
		driver.findElement(By.id("password")).sendKeys("me1234ME");

		WebElement day = driver.findElement(By.id("days"));
		Select dropdownDay = new Select(day);
		dropdownDay.selectByVisibleText("3");
		
		WebElement month = driver.findElement(By.id("months"));
		Select dropdownMonth = new Select(month);
		dropdownMonth.selectByVisibleText("April");
		
		WebElement year = driver.findElement(By.id("years"));
		Select dropdownYear = new Select(year);
		dropdownYear.selectByVisibleText("1901");
		
		WebElement news = driver.findElement(By.id("newsletter"));

		if (!news.isSelected()) {
			news.click();
		}
		
		driver.findElement(By.id("first_name")).sendKeys("Me");
		driver.findElement(By.id("last_name")).sendKeys("Fool");
		driver.findElement(By.id("address1")).sendKeys("Fake St. #555");
		
		WebElement country = driver.findElement(By.id("country"));
		Select dropdownCountry = new Select(country);
		dropdownCountry.selectByVisibleText("Singapore");
		
		driver.findElement(By.id("state")).sendKeys("Of Mind");
		driver.findElement(By.id("city")).sendKeys("Gotham");
		driver.findElement(By.id("zipcode")).sendKeys("123456");
		driver.findElement(By.id("mobile_number")).sendKeys("555-5555");
		driver.findElement(By.cssSelector("button[data-qa='create-account")).click();
	}
	
	// Proceed to checkout with selected items in the cart.
	private static void Checkout() {
		driver.findElement(By.partialLinkText("Cart")).click();
		
		WebElement tableBody = driver.findElement(By.tagName("tbody"));
		List<WebElement> rows = tableBody.findElements(By.tagName("tr"));
		
		CheckItemCount(rows);
		
		CheckItemName(rows, 0, "Fancy Green Top");
		CheckItemQuantity(rows, 0, "3");
		
		CheckItemName(rows, 1, "Frozen Tops For Kids");
		CheckItemQuantity(rows, 1, "2");
		
		
		driver.findElement(By.linkText("Proceed To Checkout")).click();
		driver.findElement(By.linkText("Place Order")).click();
	}	

	// Input payment method to complete purchase.
	private static void Payment() {
		driver.findElement(By.cssSelector("input[name='name_on_card']")).sendKeys("Me Fool");
		driver.findElement(By.cssSelector("input[name='card_number']")).sendKeys("1234567887564321");
		driver.findElement(By.cssSelector("input[name='cvc']")).sendKeys("123");
		driver.findElement(By.cssSelector("input[name='expiry_month']")).sendKeys("05");
		driver.findElement(By.cssSelector("input[name='expiry_year']")).sendKeys("2025");
		driver.findElement(By.cssSelector("button[data-qa='pay-button")).click();
		driver.findElement(By.linkText("Continue")).click();
	}

	// Checks that the number of items to purchase is correct.
	private static void CheckItemCount(List<WebElement> rows) {
		int items = 0;
		for (WebElement row : rows){
			items++;
		}
		Assertions.assertEquals(items,2, "Correct number of items to purchase");
	}
	
	// Checks that the item name is correct.
	private static void CheckItemName(List<WebElement> rows, int itemNum, String ItemName) {
		
		String rName = rows.get(itemNum).findElement(By.linkText(ItemName)).getText();
		System.out.println(rName);
		Assertions.assertEquals(ItemName, rName);
	}

	// Checks that the item quantity is correct.
	private static void CheckItemQuantity(List<WebElement> rows, int itemNum, String quantity) {
		
		String rquantity = rows.get(itemNum).findElement(By.className("cart_quantity")).getText();
		System.out.println(rquantity);
		Assertions.assertEquals(rquantity, quantity);
	}
	
}
