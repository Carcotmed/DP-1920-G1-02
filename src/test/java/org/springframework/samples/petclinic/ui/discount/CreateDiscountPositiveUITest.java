package org.springframework.samples.petclinic.ui.discount;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

//HU-019 
  
public class CreateDiscountPositiveUITest {
	  private WebDriver driver;
	  private String baseUrl;
	  private boolean acceptNextAlert = true;
	  private StringBuffer verificationErrors = new StringBuffer();

	  @BeforeEach
	  public void setUp() throws Exception {
		  System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));

	    driver = new FirefoxDriver();
	    baseUrl = "https://www.google.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }

	  @Test
	  public void testCreateDiscountPositiveUI() throws Exception {
	    driver.get("http://localhost:8080/");
	    
	    //login
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
	    
	    //Create
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
	    assertEquals("Juguete", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[1]")).getText());
	    assertEquals("12.1", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[2]")).getText());
	    assertEquals("89", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[3]")).getText());
	    assertEquals("Pipo2", driver.findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[4]")).getText());
	  }

	  @AfterEach
	  public void tearDown() throws Exception {
	    driver.quit();
	    String verificationErrorString = verificationErrors.toString();
	    if (!"".equals(verificationErrorString)) {
	      fail(verificationErrorString);
	    }
	  }

	  private boolean isElementPresent(By by) {
	    try {
	      driver.findElement(by);
	      return true;
	    } catch (NoSuchElementException e) {
	      return false;
	    }
	  }

	  private boolean isAlertPresent() {
	    try {
	      driver.switchTo().alert();
	      return true;
	    } catch (NoAlertPresentException e) {
	      return false;
	    }
	  }

	  private String closeAlertAndGetItsText() {
	    try {
	      Alert alert = driver.switchTo().alert();
	      String alertText = alert.getText();
	      if (acceptNextAlert) {
	        alert.accept();
	      } else {
	        alert.dismiss();
	      }
	      return alertText;
	    } finally {
	      acceptNextAlert = true;
	    }
	  }
	}
