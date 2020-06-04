package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.java.Log;

@Log
public class SeeAdoptionsListStepDefinitions extends AbstractStep{
	
	@LocalServerPort
	private int port;
	
	@Given("Estoy logueado con el usuario {string} y la contraseña {string}")
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
