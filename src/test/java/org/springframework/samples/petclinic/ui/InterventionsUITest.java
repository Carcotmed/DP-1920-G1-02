
package org.springframework.samples.petclinic.ui;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer.Alphanumeric;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

@TestMethodOrder(value = Alphanumeric.class)
public class InterventionsUITest {

	private WebDriver		driver;
	private String			baseUrl;
	private boolean			acceptNextAlert		= true;
	private StringBuffer	verificationErrors	= new StringBuffer();


	@BeforeEach
	public void setUp() throws Exception {
		System.setProperty("webdriver.gecko.driver", System.getenv("webdriver.gecko.driver"));
		this.driver = new FirefoxDriver();
		this.baseUrl = "https://www.google.com/";
		this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void testListInterventionUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();
		Assertions.assertEquals("Test4", this.driver.findElement(By.xpath("//table[2]/tbody/tr[13]/td[2]")).getText());
	}

	@Test
	public void testCreateUrgentInterventionNegativeUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		//create
		this.driver.findElement(By.xpath("//a[contains(text(),'Urgent\n		Visit')]")).click();

		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		Assert.assertEquals("You must choose a vet", this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[2]/div/span[2]")).getText());
		Assert.assertTrue("no puede estar vacío".equals(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/span[2]")).getText())
			|| "el tamaño tiene que estar entre 3 y 50".equals(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/span[2]")).getText()));
	}

	@Test
	public void testCreateUrgentInterventionPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		//create
		this.driver.findElement(By.xpath("//a[contains(text(),'Urgent\n		Visit')]")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).clear();
		this.driver.findElement(By.xpath("//input[@id='name']")).sendKeys("Pipo");
		new Select(this.driver.findElement(By.xpath("//select[@id='vet']"))).selectByVisibleText("Linda Douglas");
		this.driver.findElement(By.xpath("//select[@id='vet']/option[3]")).click();
		this.driver.findElement(By.xpath("//select[@id='requiredProducts']/option[3]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		Assertions.assertEquals("Pipo", this.driver.findElement(By.xpath("//table[2]/tbody/tr/td[4]")).getText());
	}

	@Test
	public void testListAvailableVetPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		//create x3
		this.driver.findElement(By.xpath("//a[contains(text(),'Urgent\n		Visit')]")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).clear();
		this.driver.findElement(By.xpath("//input[@id='name']")).sendKeys("test1");
		new Select(this.driver.findElement(By.xpath("//select[@id='vet']"))).selectByVisibleText("James Carter");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("(//option[@value='1'])[2]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("test2");
		new Select(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[2]/div/select"))).selectByVisibleText("James Carter");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//select[@id='requiredProducts']/option[2]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).click();
		this.driver.findElement(By.xpath("//input[@id='name']")).clear();
		this.driver.findElement(By.xpath("//input[@id='name']")).sendKeys("test3");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//select[@id='requiredProducts']/option[3]")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//check
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		Assert.assertEquals("Helen Leary", this.driver.findElement(By.xpath("//select[@id='vet']/option")).getText());
	}

	@Test
	public void testEditInterventionPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		Assertions.assertEquals("Peluquería", this.driver.findElement(By.xpath("//tr[10]/td[4]")).getText());

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/8/interventions/5/edit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("Castraciooooon");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();
		Assertions.assertEquals("Castraciooooon", this.driver.findElement(By.xpath("//tr[10]/td[4]")).getText());
	}

	@Test
	public void testEditInterventionNegativeUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("vet1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("v3t");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/6/interventions/7/edit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		Assert.assertTrue("no puede estar vacío".equals(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/span[2]")).getText())
			|| "el tamaño tiene que estar entre 3 y 50".equals(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/span[2]")).getText()));
	}

	@Test
	public void testDeleteInterventionPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();
		Assertions.assertEquals("Castracion", this.driver.findElement(By.xpath("//table[@id='interventionsTable']/tbody/tr[13]/td[4]")).getText());
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/1/interventions/1/delete')]")).click();
		Assertions.assertEquals("No intervention", this.driver.findElement(By.xpath("//table[@id='interventionsTable']/tbody/tr[13]/td[4]")).getText());
	}

	@Test
	public void testCreateInterventionPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/6/interventions/new')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("Name");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		Assertions.assertEquals("Name", this.driver.findElement(By.xpath("//table[2]/tbody/tr[4]/td[4]")).getText());
	}

	@Test
	public void testCreateInterventionWithProductPositiveUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("Name2");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
		new Select(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[2]/div/select"))).selectByVisibleText("James Carter");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//select[@id='requiredProducts']/option")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		Assertions.assertEquals("Name2", this.driver.findElement(By.xpath("//table[2]/tbody/tr/td[4]")).getText());
	}

	@Test
	public void testCreateInterventionWithProductNegativeUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("Name4");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
		new Select(this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[2]/div/select"))).selectByVisibleText("James Carter");
		this.driver.findElement(By.xpath("//select[@id='vet']/option")).click();
		this.driver.findElement(By.xpath("//select[@id='requiredProducts']/option[4]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		Assertions.assertEquals("There aren't enough of [Jeringuilla]", this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div[3]/div/span[2]")).getText());
	}

	@Test
	public void testCreateInterventionWithVetNegativeUI() throws Exception {
		this.driver.get("http://localhost:8080/");

		//login
		this.driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).click();
		this.driver.findElement(By.xpath("//input[@id='username']")).clear();
		this.driver.findElement(By.xpath("//input[@id='username']")).sendKeys("admin1");
		this.driver.findElement(By.xpath("//input[@id='password']")).click();
		this.driver.findElement(By.xpath("//input[@id='password']")).clear();
		this.driver.findElement(By.xpath("//input[@id='password']")).sendKeys("4dm1n");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show owner
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/find')]")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).click();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).clear();
		this.driver.findElement(By.xpath("//input[@id='lastName']")).sendKeys("Franklin");
		this.driver.findElement(By.xpath("//button[@type='submit']")).click();

		//show pet
		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1')]")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("NameTest");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
		this.driver.findElement(By.xpath("//select[@id='vet']/option[6]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("NameTest");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
		this.driver.findElement(By.xpath("//select[@id='vet']/option[6]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();

		this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("NameTest");
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
		this.driver.findElement(By.xpath("//select[@id='vet']/option[6]")).click();
		this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		Boolean result = false;
		try {

			this.driver.findElement(By.xpath("//a[contains(@href, '/owners/1/pets/1/visits/addUrgentVisit')]")).click();
			this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).click();
			this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).clear();
			this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div/div/div/input")).sendKeys("NameTest");
			this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div")).click();
			this.driver.findElement(By.xpath("//select[@id='vet']/option[6]")).click();
			this.driver.findElement(By.xpath("//form[@id='add-intervention-form']/div[2]/div/button")).click();
		} catch (NoSuchElementException e) {
			result = true;
		}
		Assertions.assertTrue(result, "Error en la seleccion de veterinario");
	}

	@After
	public void tearAllDown() throws Exception {
		this.driver.close();
		this.driver.quit();
		String verificationErrorString = this.verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			Assertions.fail(verificationErrorString);
		}
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
