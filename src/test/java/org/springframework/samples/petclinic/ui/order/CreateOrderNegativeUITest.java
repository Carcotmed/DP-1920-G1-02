package org.springframework.samples.petclinic.ui.order;


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

//HU-016
  
public class CreateOrderNegativeUITest {
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
	  public void testCreateOrderNegativeUI() throws Exception {
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
	    
	    driver.findElement(By.xpath("//a[contains(@href, '/orders')]")).click();
	    
	    //create
	    driver.findElement(By.xpath("//input[@value='Create']")).click();
	    driver.findElement(By.xpath("//input[@id='quantity']")).click();
	    driver.findElement(By.xpath("//input[@id='quantity']")).clear();
	    driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("-120");
	    driver.findElement(By.xpath("//input[@id='orderDate']")).click();
	    driver.findElement(By.xpath("//div[@id='ui-datepicker-div']/table/tbody/tr/td[6]/a")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("tiene que ser mayor o igual que 1", driver.findElement(By.xpath("//form[@id='edit-order-form']/div/div[2]/div/span[2]")).getText());
	    
	  
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
