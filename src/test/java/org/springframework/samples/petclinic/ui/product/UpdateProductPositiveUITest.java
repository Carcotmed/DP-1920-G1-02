package org.springframework.samples.petclinic.ui.product;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

//HU-033
public class UpdateProductPositiveUITest {
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
	  public void testCreateProductPositiveUI() throws Exception {
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
	    
	    //list
	    driver.findElement(By.xpath("//a[contains(@href, '/products')]")).click();
	    
	    //update
	    driver.findElement(By.xpath("//a[contains(@href, '/products/edit/1')]")).click();
	    driver.findElement(By.xpath("//input[@id='quantity']")).click();
	    driver.findElement(By.xpath("//input[@id='quantity']")).clear();
	    driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("29");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    assertEquals("29", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr/td[3]")).getText());
	
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
