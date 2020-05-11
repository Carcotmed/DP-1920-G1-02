package org.springframework.samples.petclinic.ui;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
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
public class ProviderUITest {

	private WebDriver driver;
	private String baseUrl;
	private boolean acceptNextAlert = true;
	private StringBuffer verificationErrors = new StringBuffer();

	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Order(1)
	@Test // HU-013
	public void testCreateProviderNegativeUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		// Create
		driver.findElement(By.xpath("//a[contains(@href, '/providers/new')]")).click();
		driver.findElement(By.xpath("//input[@id='name']")).click();
		driver.findElement(By.xpath("//input[@id='name']")).clear();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Pipo");
		driver.findElement(By.xpath("//input[@id='address']")).clear();
		driver.findElement(By.xpath("//input[@id='address']")).sendKeys("calle pipo");
		driver.findElement(By.xpath("//input[@id='phone']")).clear();
		driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("123456789");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("no puede estar vacío",
				driver.findElement(By.xpath("//form[@id='add-provider-form']/div/div[4]/div/span[2]")).getText());
	}

	@Order(2)
	@Test // HU-013
	public void testCreateProviderPositiveUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		// Create
		driver.findElement(By.xpath("//a[contains(@href, '/providers/new')]")).click();
		driver.findElement(By.xpath("//input[@id='name']")).click();
		driver.findElement(By.xpath("//input[@id='name']")).clear();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Satan");
		driver.findElement(By.xpath("//input[@id='address']")).clear();
		driver.findElement(By.xpath("//input[@id='address']")).sendKeys("calle pipo");
		driver.findElement(By.xpath("//input[@id='phone']")).clear();
		driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("123456789");
		driver.findElement(By.xpath("//input[@id='email']")).click();
		driver.findElement(By.xpath("//input[@id='email']")).clear();
		driver.findElement(By.xpath("//input[@id='email']")).sendKeys("pipo@gmail.com");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("Satan", driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[6]/td")).getText());
		assertEquals("calle pipo",
				driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[6]/td[2]")).getText());
		assertEquals("123456789",
				driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[6]/td[3]")).getText());
		assertEquals("pipo@gmail.com",
				driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[6]/td[4]")).getText());
	}

	@Order(3)
	@Test // HU-014
	public void testUpdateProviderNegativeUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		// Update
		driver.findElement(By.xpath("//a[contains(@href, '/providers/1/edit')]")).click();

		assertEquals("Whitelabel Error Page", driver.findElement(By.xpath("//h1")).getText());
		assertEquals("There was an unexpected error (type=Forbidden, status=403).",
				driver.findElement(By.xpath("//div[2]")).getText());
	}

	@Order(4)
	@Test // HU-014
	public void testUpdateProviderPositiveUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		// Update
		driver.findElement(By.xpath("//a[contains(@href, '/providers/1/edit')]")).click();
		driver.findElement(By.xpath("//input[@id='name']")).click();
		driver.findElement(By.xpath("//input[@id='name']")).clear();
		driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Pipo1Updated");
		driver.findElement(By.xpath("//input[@id='address']")).click();
		driver.findElement(By.xpath("//input[@id='address']")).clear();
		driver.findElement(By.xpath("//input[@id='address']")).sendKeys("Calle Pipo nº2");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		assertEquals("Pipo1Updated",
				driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr/td")).getText());
		assertTrue(driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr/td[2]")).getText()
				.matches("^Calle Pipo n[\\s\\S]2$"));
	}

	@Order(5)
	@Test // HU-015
	public void testDeleteProviderNegativeUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		// Delete
		driver.findElement(By.xpath("//a[contains(@href, '/providers/1/delete')]")).click();

		assertEquals("Whitelabel Error Page", driver.findElement(By.xpath("//h1")).getText());
		assertEquals("There was an unexpected error (type=Forbidden, status=403).",
				driver.findElement(By.xpath("//div[2]")).getText());
	}

	@Order(6)
	@Test // HU-015
	public void testDeleteProviderPositiveUI() throws Exception {
		driver.get("http://localhost:8080/");

		// login
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();

		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();

		WebElement table = driver.findElement(By.xpath("//table[@id='providersTable']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countBefore = rows_table.size();

		// Delete
		driver.findElement(By.xpath("//a[contains(@href, '/providers/5/delete')]")).click();

		table = driver.findElement(By.xpath("//table[@id='providersTable']/tbody"));
		rows_table = table.findElements(By.tagName("tr"));
		int rows_countAfter = rows_table.size();
		assertThat(rows_countBefore).isEqualTo(rows_countAfter + 1);
	}

	@AfterEach
	public void tearDown() throws Exception {
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
