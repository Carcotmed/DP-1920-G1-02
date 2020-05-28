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
class ProductUITest {

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
	 @Test //HU-029 y HU-032
	  public void testListProductPositiveUI() throws Exception {
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
	    driver.findElement(By.xpath("//div[@id='main-navbar']/ul/li[5]/a/span[2]")).click();
	    assertEquals("Products", driver.findElement(By.xpath("//h2")).getText());
	    assertEquals("Jeringuilla", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[4]/td")).getText());
	    assertEquals("20.5", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[4]/td[2]")).getText());
	    assertEquals("0", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[4]/td[3]")).getText());
	    assertEquals("true", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[4]/td[4]")).getText());
	    assertEquals("Pipo1", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr[4]/td[5]")).getText());
	  }
	
	@Order(2)
	@Test //HU-031
	  public void testCreateProductNegativeUI() throws Exception {
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
	    driver.findElement(By.xpath("//input[@id='price']")).sendKeys("-12.2");
	    driver.findElement(By.xpath("//input[@id='allAvailable1']")).click();
	    driver.findElement(By.xpath("//select[@id='provider']")).click();
	    new Select(driver.findElement(By.xpath("//select[@id='provider']"))).selectByVisibleText("Pipo3");
	    driver.findElement(By.xpath("//select[@id='provider']/option[3]")).click();
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    assertEquals("tiene que ser mayor o igual que 0", driver.findElement(By.xpath("//form[@id='product-edit-form']/div/div[2]/div/span[2]")).getText());
	    assertEquals("no puede ser null", driver.findElement(By.xpath("//form[@id='product-edit-form']/div/div[3]/div/span[2]")).getText());
	  }
	
	@Order(3)
	 @Test //HU-031
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
	
	@Order(4)
	 @Test //HU-033
	  public void testUpdateProductNegativeUI() throws Exception {
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
	    driver.findElement(By.xpath("//input[@id='quantity']")).sendKeys("-29");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();

	    assertEquals("tiene que ser mayor o igual que 0", driver.findElement(By.xpath("//form[@id='product-edit-form']/div/div[3]/div/span[2]")).getText());

	  }
	@Order(5)
	 @Test //HU-033
	  public void testUpdateProductPositiveUI() throws Exception {
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
	
	@Order(6)
	@Test  //HU-030
	  public void testDeleteProductNegativeUI() throws Exception {
	    driver.get("http://localhost:8080/");
	    //login
	    driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
	    driver.findElement(By.xpath("//input[@id='username']")).click();
	    driver.findElement(By.xpath("//input[@id='username']")).clear();
	    driver.findElement(By.xpath("//input[@id='username']")).sendKeys("owner1");
	    driver.findElement(By.xpath("//input[@id='password']")).click();
	    driver.findElement(By.xpath("//input[@id='password']")).clear();
	    driver.findElement(By.xpath("//input[@id='password']")).sendKeys("0wn3r");
	    driver.findElement(By.xpath("//button[@type='submit']")).click();
	    
	    //list
	    driver.findElement(By.xpath("//a[contains(@href, '/products')]")).click();
	    
	    //delete
	    driver.findElement(By.xpath("//a[contains(@href, '/products/delete/1')]")).click();
	    
	    assertEquals("Whitelabel Error Page", driver.findElement(By.xpath("//h1")).getText());
	    assertEquals("There was an unexpected error (type=Forbidden, status=403).", driver.findElement(By.xpath("//div[2]")).getText());
	  }
	
	@Order(7)
	@Test //HU-030
	  public void testDeleteProductPositiveUI() throws Exception {
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
	    
		WebElement table = driver.findElement(By.xpath("//table[@id='productsTable']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countBefore = rows_table.size();

		// delete
		driver.findElement(By.xpath("//a[contains(@href, '/products/delete/1')]")).click();
		assertEquals("Collar", driver.findElement(By.xpath("//table[@id='productsTable']/tbody/tr/td")).getText());

		table = driver.findElement(By.xpath("//table[@id='productsTable']/tbody"));
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
