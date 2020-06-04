package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LoginStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;

	@Given("Estoy logueado con el usuario {string} y la contraseña {string}")
	public void i_am_logged_in_the_system_as_with_password(String username, String password) {
		getDriver().manage().window().maximize();
		LoginStepDefinitions.login(username, password, port, getDriver());
	}

	public static void login(String username, String password, int port, WebDriver driver) {
		driver.get("http://localhost:"+port+"/");
		driver.findElement(By.xpath("//a[contains(@href, '/login')]")).click();
		driver.findElement(By.xpath("//input[@id='username']")).clear();
		driver.findElement(By.xpath("//input[@id='username']")).sendKeys(username);
		driver.findElement(By.xpath("//input[@id='password']")).clear();
		driver.findElement(By.xpath("//input[@id='password']")).sendKeys(password);
		driver.findElement(By.xpath("//button[@type='submit']")).click();
	}

}