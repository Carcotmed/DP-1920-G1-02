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

//HU-031
public class CreateProductPositiveUITest {
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
	    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
	    driver.findElement(By.xpath("//input[@id='password']")).click();
	    driver.findElement(By.xpath("//input[@id='password']")).clear();
	    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    //list
	    driver.findElement(By.xpath("//a[contains(@href, '/products')]")).click();
	    
	    //create
	    driver.findElement(By.xpath("//input[@value='Create']")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).clear();
	    driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Prueba");
	    driver.findElement(By.xpath("//input[@id='price']")).click();
	    driver.findElement(By.xpath("//input[@id='price']")).clear();
	    driver.findElement(By.xpath("//input[@id='price']")).sendKeys("12.2");
	    driver.findElement(By.xpath("//input[@id='quantity']")).click();
	    driver.findElement(By.xpath("//input[@id='quantity']")).clear();
	    driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("50");
	    driver.findElement(By.xpath("//input[@id='allAvailable1']")).click();
	    driver.findElement(By.xpath("//select[@id='provider']")).click();
	    new Select(driver.findElement(By.xpath("//select[@id='provider']"))).selectByVisibleText("Pipo3");
	    driver.findElement(By.xpath("//select[@id='provider']/option[3]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    assertEquals("Prueba", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[5]/td")).getText());
	    assertEquals("12.2", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[5]/td[2]")).getText());
	    assertEquals("50", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[5]/td[3]")).getText());
	    assertEquals("true", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[5]/td[4]")).getText());
	    assertEquals("Pipo3", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[5]/td[5]")).getText());
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
