package taqc;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;



public class DesktopMacTest {
	
	private String defaultURL = "https://demo.opencart.com/";
	private static WebDriver driver;
	
	@BeforeAll
	public static void setup() {
		System.setProperty("webdriver.chrome.driver", "D:\\WebDrivers\\chromedriver-win64\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(300));
	}
	
	@BeforeEach
	public void preparation() {	
		driver.get(defaultURL);		
	}
	
	@Test
	public void macTest() {
		//changing currency
		WebElement currency = driver.findElement(By.cssSelector(".dropdown a[href=\"#\"]"));
		currency.click();
		WebElement Euro = driver.findElement(By.cssSelector("a[href=\"EUR\""));
		Euro.click();
		//follow the link to "Desktops"
		WebElement dropdown = driver.findElement(By.partialLinkText("Desktops"));
		dropdown.click();
		WebElement link = driver.findElement(By.partialLinkText("Mac"));
		link.click();
		//On the Mac page
		List<WebElement> products = driver.findElements(By.cssSelector(".product-thumb"));
		WebElement res = null;
		//searching for "iMac" container
		for(WebElement in : products) {
			if(in.findElement(By.cssSelector(".product-thumb .description a")).getText().equals("iMac")){
				res = in;
			}
		}
		if(res!= null) {//container with iMac was found
			String actual = res.findElement(By.cssSelector(".price span.price-new")).getText();
			String expected = "111.55€";
			Assertions.assertTrue(actual.equals(expected), "iMac price isn't equal 111.55€");
		}
		else {
			throw new NoSuchElementException("iMac is not presented on the page");
		}
		
	}
	
	@AfterAll
	public static void finish() {
		if (driver != null) {
           		driver.quit();
        	}
	}
}
