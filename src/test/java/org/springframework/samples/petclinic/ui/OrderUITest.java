package org.springframework.samples.petclinic.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestMethodOrder(value = OrderAnnotation.class)
class OrderUITest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Order(1)
	@Test // HU-016
	void testCreateOrderNegativeUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		// create
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("-120");
		driver.findElement(By.xpath("//input[@id='orderDate']")).click();
		driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td[6]/a")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("tiene que ser mayor o igual que 1",
				driver.findElement(By.xpath("//form[@id='edit-order-form']/div/div[2]/div/span[2]")).getText());

	}

	@Order(2)
	@Test // HU-016
	void testCreateOrderPositiveUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		// create
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("120");
		driver.findElement(By.xpath("//input[@id='orderDate']")).click();
		driver.findElement(By.xpath("(//a[contains(@href, '#')])[9]")).click();
		driver.findElement(By.xpath("//input[@id='sent1']")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("Pomadita", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td")).getText());
		assertEquals("120", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[2]")).getText());
		assertEquals("2020-06-07", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[3]")).getText());
		assertEquals("true", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[5]")).getText());
		assertEquals("Pipo1", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[6]")).getText());
		assertEquals("45.0", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[7]")).getText());
	}

	@Order(3)
	@Test // HU-017
	void testUpdateOrderNegativeUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		// update
		driver.findElement(By.xpath("//table[@id='orders']/tbody/tr/td[8]/a")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("-398");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("tiene que ser mayor o igual que 1",
				driver.findElement(By.xpath("//form[@id='edit-order-form']/div/div[2]/div/span[2]")).getText());

	}

	@Order(4)
	@Test // HU-017
	void testUpdateOrderPositiveUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		// update
		driver.findElement(By.xpath("//table[@id='orders']/tbody/tr/td[8]/a")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("398");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("398", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr/td[2]")).getText());
	}

	@Order(5)
	@Test // HU-018
	void testDeleteOrderNegativeUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		assertEquals("Whitelabel Error Page", driver.findElement(By.xpath("//h1")).getText());
		assertEquals("There was an unexpected error (type=Forbidden, status=403).",
				driver.findElement(By.xpath("//div[2]")).getText());
	}

	@Order(6)
	@Test // HU-018
	void testDeleteOrderPositiveUI() throws Exception {
		driver.get("http://localhost:80/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();

		WebElement table = driver.findElement(By.xpath("//table[@id='orders']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countBefore = rows_table.size();

		// delete
		driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[2]/td[8]/a[2]")).click();

		assertEquals("Juguete", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[2]/td")).getText());

		table = driver.findElement(By.xpath("//table[@id='orders']/tbody"));
		rows_table = table.findElements(By.tagName("tr"));
		int rows_countAfter = rows_table.size();
		assertThat(rows_countBefore).isEqualTo(rows_countAfter + 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		this.driver.close();
	}

	private boolean isElementPresent(final By by) {
		try {
			this.driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	private boolean isAlertPresent() {
		try {
			this.driver.switchTo().alert();
			return true;
		} catch (NoAlertPresentException e) {
			return false;
		}
	}

	private String closeAlertAndGetItsText() {
		try {
			Alert alert = this.driver.switchTo().alert();
			String alertText = alert.getText();
			if (this.acceptNextAlert) {
				alert.accept();
			} else {
				alert.dismiss();
			}
			return alertText;
		} finally {
			this.acceptNextAlert = true;
		}
	}

}
