package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SeeAdoptionsListStepDefinitions extends AbstractStep{
	
	@LocalServerPort
	private int port;
	
	@Given("Estoy logueado con mi usuario {string} y la mi {string}")
	public void loggedAsUser(String username, String password) {
	    LoginStepDefinitions.login(username, password, port, getDriver());
	}
	
	@When ("Voy a la lista de mis adopciones")
	public void goToAdoptionsList () {
		
	}
	
	@Then ("La lista de todas mis adopciones aparece en pantalla")
	public void adoptionsListIsShown () {
		
	}

}
