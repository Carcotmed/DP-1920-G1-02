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
import org.openqa.selenium.support.ui.Select;

@TestMethodOrder(value = OrderAnnotation.class)
public class DiscountUITest {

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
	@Test // HU-019
	public void testCreateDiscountNegativeUI() throws Exception {
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

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();

		// create
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).clear();
		driver.findElement(By.xpath("//input[@id='percentage']")).sendKeys("19");
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("-2");
		driver.findElement(By.xpath("//select[@id='provider']")).click();
		new Select(driver.findElement(By.xpath("//select[@id='provider']"))).selectByVisibleText("Pipo3");
		driver.findElement(By.xpath("//select[@id='provider']/option[3]")).click();
		driver.findElement(By.xpath("//select[@id='product']")).click();
		new Select(driver.findElement(By.xpath("//select[@id='product']"))).selectByVisibleText("Jeringuilla");
		driver.findElement(By.xpath("//select[@id='product']/option[4]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("tiene que ser mayor o igual que 1",
				driver.findElement(By.xpath("//form[@id='discount-edit-form']/div/div[2]/div/span[2]")).getText());
	}

	@Order(2)
	@Test // HU-019
	public void testCreateDiscountPositiveUI() throws Exception {
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
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();

		// Create
		driver.findElement(By.xpath("//input[@value='Create']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).clear();
		driver.findElement(By.xpath("//input[@id='percentage']")).sendKeys("12.1");
		driver.findElement(By.xpath("//input[@id='quantity']")).click();
		driver.findElement(By.xpath("//input[@id='quantity']")).clear();
		driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("89");
		driver.findElement(By.xpath("//select[@id='provider']")).click();
		new Select(driver.findElement(By.xpath("//select[@id='provider']"))).selectByVisibleText("Pipo2");
		driver.findElement(By.xpath("//select[@id='provider']/option[2]")).click();
		driver.findElement(By.xpath("//select[@id='product']")).click();
		new Select(driver.findElement(By.xpath("//select[@id='product']"))).selectByVisibleText("Juguete");
		driver.findElement(By.xpath("//select[@id='product']/option[3]")).click();
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Juguete",
				driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[1]")).getText());
		assertEquals("12.1", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[2]")).getText());
		assertEquals("89", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[3]")).getText());
		assertEquals("Pipo2",
				driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[4]")).getText());
	}

	@Order(3)
	@Test // HU-020
	public void testUpdateDiscountNegativeUI() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts/edit/1')]")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).clear();
		driver.findElement(By.xpath("//input[@id='percentage']")).sendKeys("145.0");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("valor numérico fuera de los límites (se esperaba <2 dígitos>.<2 dígitos)",
				driver.findElement(By.xpath("//form[@id='discount-edit-form']/div/div/div/span[2]")).getText());
	}

	@Order(4)
	@Test // HU-020
	public void testUpdateDiscountPositiveUI() throws Exception {
		driver.get("http://localhost:8080/");
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		driver.findElement(By.xpath("//input[@id='password']")).click();
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/providers')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts/edit/2')]")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).click();
		driver.findElement(By.xpath("//input[@id='percentage']")).clear();
		driver.findElement(By.xpath("//input[@id='percentage']")).sendKeys("75.0");
		driver.findElement(By.xpath("//button[@type='submit']")).click();
		assertEquals("Collar", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[2]/td")).getText());
		assertEquals("75.0", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[2]/td[2]")).getText());
	}

	@Order(5)
	@Test // HU-021
	public void testDeleteDiscountNegativeUI() throws Exception {
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

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();

		// acceso a discounts list
		assertEquals("Whitelabel Error Page", driver.findElement(By.xpath("//h1")).getText());
		assertEquals("There was an unexpected error (type=Forbidden, status=403).",
				driver.findElement(By.xpath("//div[2]")).getText());
	}

	@Order(6)
	@Test // HU-021
	public void testDeleteDiscountPositiveUI() throws Exception {
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

		driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[7]/a/span[2]")).click();
		driver.findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();

		WebElement table = driver.findElement(By.xpath("//table[@id='discountsTable']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countBefore = rows_table.size();

		// delete
		driver.findElement(By.xpath("//a[contains(@href, '/discounts/delete/1')]")).click();
		assertEquals("Collar", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr/td")).getText());

		table = driver.findElement(By.xpath("//table[@id='discountsTable']/tbody"));
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
