package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
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
public class SeeAdoptionsListStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;

	@When("Voy a la lista de mis adopciones")
	public void goToAdoptionsList() {
		getDriver().findElement(By.xpath("//a[contains(@href, '/adoptions')]")).click();
		getDriver().findElement(By.xpath("//a[contains(@href, '/adoptions/myAdoptions')]")).click();
	}

	@Then("La lista de todas mis adopciones aparece en pantalla")
	public void adoptionsListIsShown() {
		Assertions.assertEquals("Leo",
				getDriver().findElement(By.xpath("//table[@id='adoptionsTable']/tbody/tr/td")).getText());
		stopDriver();
	}

}
