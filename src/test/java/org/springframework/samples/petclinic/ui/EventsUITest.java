
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
class EventsUITest {

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
	void successfulEventCreationUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/new')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div/div/input")).click();
		this.driver.findElement(By.id("date")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/div/span[2]")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a[2]/span")).click();
		this.driver.findElement(By.linkText("4")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).sendKeys("Description");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).sendKeys("20");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).sendKeys("Placeee");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div[2]/div/button")).click();
		Assertions.assertEquals("Description",
				this.driver.findElement(By.xpath("//table[@id='eventsTable']/tbody/tr[6]/td[4]")).getText());
	}

	@Test
	void successfulEventPublishUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/4')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/publish/4')]")).click();
		Assertions.assertEquals("true", this.driver.findElement(By.xpath("//tr[4]/td")).getText());
	}

	@Test
	void emptyEventPublishUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/5')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/publish/5')]")).click();
		Assertions.assertTrue(
				this.driver.findElement(By.xpath("//body/div/div")).getText()
						.contains("Every field must be completed to publish the event"),
				"Error unexpected at publishing an empty event");
	}

	@Test
	void pastDateEventCreationUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//div[@id='main-navbar']/ul[2]/li/a")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/new')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/div/a/span")).click();
		this.driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr[2]/td[3]/a")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[2]/div/input")).sendKeys("Description");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).sendKeys("10");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[4]/div/input")).sendKeys("Place");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div[2]/div/button")).click();
		Assertions.assertEquals("tiene que ser una fecha en el futuro",
				this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div/div/span[2]")).getText());
	}

	@Test
	void successfulEventEditionUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/4')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/edit/4')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div/div[3]/div/input")).sendKeys("50");
		this.driver.findElement(By.xpath("//form[@id='add-event-form']/div[2]/div/button")).click();
		Assertions.assertEquals("0 / 50", this.driver.findElement(By.xpath("//tr[6]/td")).getText());
	}

	@Test
	void successfulEventDeleteUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();

		WebElement table = this.driver.findElement(By.xpath("//table[@id='eventsTable']/tbody"));
		List<WebElement> rows = table.findElements(By.tagName("tr"));
		int rowsCountBefore = rows.size();

		this.driver.findElement(By.xpath("//a[contains(@href, '/events/5')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/delete/5')]")).click();

		table = this.driver.findElement(By.xpath("//table[@id='eventsTable']/tbody"));
		rows = table.findElements(By.tagName("tr"));
		int rowsCountAfter = rows.size();

		Assertions.assertEquals(rowsCountBefore, rowsCountAfter + 1);
	}

	@Test
	void publishedEventDeleteUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.get("http://localhost:80/events/delete/1");
		Assertions.assertTrue(
				this.driver.findElement(By.xpath("//body/div/div")).getText()
						.contains("You can't delete an event already published"),
				"Error unexpected at deleting a published event");
	}

	@Test
	void successfulParticipationCreationUITest() throws Exception {
		this.driver.get("http://localhost:80/");
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events')]")).click();
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/3')]")).click();
		Assertions.assertEquals("0 / 10000", this.driver.findElement(By.xpath("//tr[6]/td")).getText());
		this.driver.findElement(By.xpath("//a[contains(@href, '/events/newParticipation/3')]")).click();
		this.driver.findElement(By.xpath("//option[@value='1']")).click();
		this.driver.findElement(By.xpath("//option[@value='16']")).click();
		this.driver.findElement(By.xpath("//form[@id='add-participation-form']/div[2]/div/button")).click();
		Assertions.assertEquals("1 / 10000", this.driver.findElement(By.xpath("//tr[6]/td")).getText());
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
