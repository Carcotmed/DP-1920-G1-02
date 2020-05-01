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
  
public class CreateOrderPositiveUITest {
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
	  public void testCreateOrderPositiveUI() throws Exception {
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
	    driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("120");
	    driver.findElement(By.xpath("//input[@id='orderDate']")).click();
	    driver.findElement(By.xpath("(//a[contains(@href, '#')])[9]")).click();
	    driver.findElement(By.xpath("//input[@id='sent1']")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    assertEquals("Pomadita", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td")).getText());
	    assertEquals("120", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[2]")).getText());
	    assertEquals("2020-05-08", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[3]")).getText());
	    assertEquals("true", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[5]")).getText());
	    assertEquals("Pipo1", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[6]")).getText());
	    assertEquals("45.0", driver.findElement(By.xpath("//table[@id='orders']/tbody/tr[4]/td[7]")).getText());
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
