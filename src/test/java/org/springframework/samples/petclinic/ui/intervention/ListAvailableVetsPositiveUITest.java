package org.springframework.samples.petclinic.ui.intervention;


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

//HU-008
  
public class ListAvailableVetsPositiveUITest {
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
	  public void testListAvailableVetPositiveUI() throws Exception {
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
	    
	    //show owner
	    driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
	    driver.findElement(By.xpath("//input[@id='lastName']")).click();
	    driver.findElement(By.xpath("//input[@id='lastName']")).clear();
	    driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    //show pet
	    driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();
	    
	    //create x3
	    driver.findElement(By.xpath("//a[contains(text(),'Urgent\n		Visit')]")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).clear();
	    driver.findElement(By.xpath("//input[@id='name']")).sendKeys("test1");
	    new Select(driver.findElement(By.xpath("//select[@id='vet']"))).selectByVisibleText("James Carter");
	    driver.findElement(By.xpath("//select[@id='vet']/option")).click();
	    driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
	    driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
	    driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
	    driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("test2");
	    new Select(driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[2]/div/select"))).selectByVisibleText("James Carter");
	    driver.findElement(By.xpath("//select[@id='vet']/option")).click();
	    driver.findElement(By.xpath("//select[@id='requiredProducts']/option[2]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).click();
	    driver.findElement(By.xpath("//input[@id='name']")).clear();
	    driver.findElement(By.xpath("//input[@id='name']")).sendKeys("test3");
	    new Select(driver.findElement(By.xpath("//select[@id='vet']"))).selectByVisibleText("James Carter");
	    driver.findElement(By.xpath("//select[@id='vet']/option")).click();
	    driver.findElement(By.xpath("//select[@id='requiredProducts']/option[3]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    //check
	    driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
	    assertEquals("Helen Leary", driver.findElement(By.xpath("//select[@id='vet']/option")).getText());
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
