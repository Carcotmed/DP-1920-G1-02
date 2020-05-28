
package org.springframework.samples.petclinic.ui;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

@TestMethodOrder(value = Alphanumeric.class)
class AdoptionsUITest {

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

	@Test
	void successfulMyAdoptionsListUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/myAdoptions')]")).click();
		Assertions.assertEquals("Adoptable1",
				this.driver.findElement(By.xpath("//table[@id='adoptionsTable']/tbody/tr/td")).getText());
	}

	@Test
	void successfulAdoptionCreationUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/11/pets/14')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/new/14')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-adoption-form']/div[2]/div/button")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/myAdoptions')]")).click();
		Assertions.assertEquals("Adoptable1",
				this.driver.findElement(By.xpath("//table[@id='adoptionsTable']/tbody/tr[2]/td")).getText());
	}

	@Test
	void incorrectAdoptionCreationUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("http://localhost:8080/adoptions/new/10");
		this.driver.findElement(By.xpath("//form[@id='add-adoption-form']/div[2]/div/button")).click();
		Assertions.assertTrue(
				this.driver.findElement(By.xpath("//body/div/div")).getText()
						.contains("You can't adopt a pet which another person owns"),
				"Error unexpected at adopting an already owned pet");
	}

	@Test
	void successfulAllAdoptionListUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/allAdoptions')]")).click();
		Assertions.assertEquals("Adoptable1",
				this.driver.findElement(By.xpath("//table[@id='adoptionsTable']/tbody/tr/td")).getText());
	}

	@Test
	void incorrectAllAdoptionListUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("http://localhost:8080/adoptions/allAdoptions");
		Assertions.assertTrue(
				this.driver.findElement(By.xpath("//body/div/div")).getText()
						.contains("Only vets and admins can access to this feature"),
				"Error unexpected at attempting to see all adoptions as owner");
	}

	@Test
	void successfulAdoptionDeleteUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/allAdoptions')]")).click();

		WebElement table = this.driver.findElement(By.xpath("//table[@id='adoptionsTable']/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int rowsCountBefore = rows.size();

		this.driver.findElement(By.xpath("//a[contains(@href, '/adoptions/delete/1')]")).click();

		table = this.driver.findElement(By.xpath("//table[@id='adoptionsTable']/tbody"));
		rows = table.findElements(By.tagName("tr"));
		int rowsCountAfter = rows.size();

		Assertions.assertEquals(rowsCountBefore, rowsCountAfter + 1);
	}

	@Test
	void incorrectAdoptionDeleteUITest() throws Exception {
		this.driver.get("http://localhost:8080/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("http://localhost:8080/adoptions/delete/1");
		Assertions.assertTrue(
				this.driver.findElement(By.xpath("//body/div/div")).getText()
						.contains("Only administrators can delete adoptions"),
				"Error unexpected at attempting to delete an adoptions as owner");
	}

	@After
	void tearAllDown() throws Exception {
		this.driver.close();
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assertions.fail(verificationErrorString);
		}
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
