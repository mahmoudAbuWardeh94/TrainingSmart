import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

public class testCases {
	public WebDriver driver;
	int numberOfTry = 4;
	SoftAssert softassertProcess = new SoftAssert();

	@BeforeTest()
	public void this_is_before_test() throws AWTException {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.get("https://smartbuy-me.com/smartbuystore/en");
		driver.manage().window().maximize();
		
		pagedown();
	}

	@Test()
	public void adding_item_Samsung_50_inch() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		for (int i = 0; i < numberOfTry; i++) {
			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();

		}

	}

	@Test()
	public void check_the_correct_price_laptop() {
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.xpath("/html/body/main/div[3]/div[1]/div[4]/div[2]/div/div[2]/div/div/ul/li[2]/a"))
				.click();

		driver.findElement(By.xpath("//*[@id=\"change_view\"]/div/div/div[2]/div[3]/div/div/a/img")).click();

		driver.findElement(By.xpath("//*[@id=\"addToCartButton\"]")).click();

		String the_single_price = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[2]/div[2]/div"))
				.getText();
		String the_updated_single_price = the_single_price.replace(".000 JOD", "");
		String the_updated_single_price_Trim = the_updated_single_price.trim();

		String the_updated_final_price = the_updated_single_price_Trim.replace(",", ".");

		double final_price = Double.parseDouble(the_updated_final_price);

		softassertProcess.assertEquals(final_price * numberOfTry, 4.797);
		softassertProcess.assertAll();

	}

	private static void pagedown() throws AWTException{
		Robot upThree = new Robot();
		upThree.keyPress(KeyEvent.VK_PAGE_DOWN);
		upThree.keyRelease(KeyEvent.VK_PAGE_DOWN);
	}
}
