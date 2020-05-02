package org.springframework.samples.petclinic.ui.provider;


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

//HU-013
  
public class CreateProviderPositiveUITest {
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
	  public void testCreateProviderPositiveUI() throws Exception {
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
	    
	    //Create
	    driver.findElement(By.xpath("//a[contains(@href, '/providers/new')]")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).clear();
	    driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Pipo");
	    driver.findElement(By.xpath("//input[@id='address']")).clear();
	    driver.findElement(By.xpath("//input[@id='address']")).sendKeys("calle pipo");
	    driver.findElement(By.xpath("//input[@id='phone']")).clear();
	    driver.findElement(By.xpath("//input[@id='phone']")).sendKeys("123456789");
	    driver.findElement(By.xpath("//input[@id='email']")).click();
	    driver.findElement(By.xpath("//input[@id='email']")).clear();
	    driver.findElement(By.xpath("//input[@id='email']")).sendKeys("pipo@gmail.com");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    assertEquals("Pipo", driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[5]/td")).getText());
	    assertEquals("calle pipo", driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[5]/td[2]")).getText());
	    assertEquals("123456789", driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[5]/td[3]")).getText());
	    assertEquals("pipo@gmail.com", driver.findElement(By.xpath("//table[@id='providersTable']/tbody/tr[5]/td[4]")).getText());
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
