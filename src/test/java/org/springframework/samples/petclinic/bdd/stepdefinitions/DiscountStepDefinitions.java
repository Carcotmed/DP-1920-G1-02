package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.boot.web.server.LocalServerPort;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DiscountStepDefinitions extends AbstractStep {

	@LocalServerPort
	private int port;

	@When("Voy a la lista de proveedores")
	public void voy_a_la_lista_de_proveedores() {
		getDriver().findElement(By.xpath("//a[contains(@href, '/providers')]")).click();
	}

	@When("Voy a la lista de descuentos")
	public void voy_a_la_lista_de_descuentos() {
		getDriver().findElement(By.xpath("//a[contains(@href, '/discounts')]")).click();
	}

	@When("Accedo a la página de creación de descuentos")
	public void accedo_a_la_página_de_creación_de_descuentos() {
		getDriver().findElement(By.xpath("//input[@value='Create']")).click();
	}

	@When("Introduzco los valores de porcentaje {string}, cantidad {string}, selecciono el proveedor {string} y el producto {string}")
	public void introduzco_los_valores_de_porcentaje_cantidad_selecciono_el_proveedor_y_el_producto(String string,
			String string2, String string3, String string4) {
		getDriver().findElement(By.xpath("//input[@id='percentage']")).click();
		getDriver().findElement(By.xpath("//input[@id='percentage']")).clear();
		getDriver().findElement(By.xpath("//input[@id='percentage']")).sendKeys(string);
		getDriver().findElement(By.xpath("//input[@id='quantity']")).click();
		getDriver().findElement(By.xpath("//input[@id='quantity']")).clear();
		getDriver().findElement(By.xpath("//input[@id='quantity']")).sendKeys(string2);
		getDriver().findElement(By.xpath("//select[@id='provider']")).click();
		new Select(getDriver().findElement(By.xpath("//select[@id='provider']"))).selectByVisibleText(string3);
		getDriver().findElement(By.xpath("//select[@id='provider']/option[2]")).click();
		getDriver().findElement(By.xpath("//select[@id='product']")).click();
		new Select(getDriver().findElement(By.xpath("//select[@id='product']"))).selectByVisibleText(string4);
		getDriver().findElement(By.xpath("//select[@id='product']/option[3]")).click();
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("La lista de todas los descuentos contiene el que creé \\(porcentaje {string}, cantidad {string}, proveedor {string} y producto {string})")
	public void la_lista_de_todas_los_descuentos_contiene_el_que_creé(String string, String string2, String string3,
			String string4) {
		assertEquals(string4,
				getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[1]")).getText());
		assertEquals(string,
				getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[2]")).getText());
		assertEquals(string2,
				getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[3]")).getText());
		assertEquals(string3,
				getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[3]/td[4]")).getText());
		stopDriver();
	}

	@Then("Se me muestra un error sobre la cantidad del producto")
	public void se_me_muestra_un_error_sobre_la_cantidad_del_producto() {
		assertEquals("tiene que ser mayor o igual que 1",
				getDriver().findElement(By.xpath("//form[@id='discount-edit-form']/div/div[2]/div/span[2]")).getText());
		stopDriver();
	}

	@When("Veo que la lista tiene {int} elementos")
	public void veo_que_la_lista_tiene_varios_elementos(Integer int1) {
		WebElement table = getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countBefore = rows_table.size();
		assertThat(rows_countBefore).isEqualTo(int1);
	}

	@When("Pulso el botón de borrar descuento")
	public void pulso_el_botón_de_borrar_descuento() {
		getDriver().findElement(By.xpath("//a[contains(@href, '/discounts/delete/1')]")).click();
	}

	@Then("La lista de todos los descuentos contiene un descuento menos \\({int})")
	public void la_lista_de_todos_los_descuentos_contiene_un_descuento_menos(Integer int1) {
		WebElement table = getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody"));
		List<WebElement> rows_table = table.findElements(By.tagName("tr"));
		int rows_countAfter = rows_table.size();
		assertThat(rows_countAfter).isEqualTo(int1);
		stopDriver();
	}

	@Then("No se me permite acceder y se me muestra un error de tipo {int} {string}")
	public void se_me_muestra_un_error_de_tipo(Integer int1, String string) {
		assertEquals("Whitelabel Error Page", getDriver().findElement(By.xpath("//h1")).getText());
		assertEquals("There was an unexpected error (type=" + string + ", status=" + int1 + ").",
				getDriver().findElement(By.xpath("//div[2]")).getText());
		stopDriver();
	}

	@When("Pulso el botón de editar descuento")
	public void pulso_el_botón_de_editar_descuento() {
		getDriver().findElement(By.xpath("//a[contains(@href, '/discounts/edit/2')]")).click();
	}

	@When("Modifico el porcentaje a {double}")
	public void modifico_el_porcentaje_a(Double double1) {
		getDriver().findElement(By.xpath("//input[@id='percentage']")).click();
		getDriver().findElement(By.xpath("//input[@id='percentage']")).clear();
		getDriver().findElement(By.xpath("//input[@id='percentage']")).sendKeys(""+double1);
		getDriver().findElement(By.xpath("//button[@type='submit']")).click();
	}

	@Then("La entrada de la lista de descuentos que modifiqué muestra el valor cambiado \\({double})")
	public void la_entrada_de_la_lista_de_descuentos_que_modifiqué_muestra_el_valor_cambiado(Double double1) {
		assertEquals(""+double1, getDriver().findElement(By.xpath("//table[@id='discountsTable']/tbody/tr[1]/td[2]")).getText());
		stopDriver();
	}
	
	@Then("Se me muestra un mensaje de error sobre el porcentaje")
	public void se_me_muestra_un_mensaje_de_error_sobre_el_porcentaje() {
		assertEquals("valor numérico fuera de los límites (se esperaba <2 dígitos>.<2 dígitos)",
				getDriver().findElement(By.xpath("//form[@id='discount-edit-form']/div/div/div/span[2]")).getText());
		stopDriver();	
	}

}
