package org.springframework.samples.petclinic.bdd.stepdefinitions;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class OrderStepDefinitions extends AbstractStep{
	
		@When("Voy a la lista de orders")
		public void voy_a_la_lista_de_orders() {
			getDriver().findElement(By.xpath("//a[contains(@href, '/orders')]")).click();
		}

		@When("Creo un Order nuevo")
		public void creo_un_Order_nuevo() {
			getDriver().findElement(By.xpath("//input[@value='Create']")).click();
		}
		
		@When("Introduzco como cantidad {int}, eligo el dia a traves del selector y dejo el producto por defecto")
		public void introduzco_como_cantidad_eligo_el_dia_a_traves_del_selector_y_dejo_el_producto_por_defecto(Integer int1) {
			getDriver().findElement(By.xpath("//input[@id='quantity']")).click();
			getDriver().findElement(By.xpath("//input[@id='quantity']")).clear();
			getDriver().findElement(By.xpath("//input[@id='quantity']")).sendKeys(""+int1);
			getDriver().findElement(By.xpath("//input[@id='orderDate']")).click();
			getDriver().findElement(By.xpath("//input[@id='orderDate']")).sendKeys("2020/06/07");
			getDriver().findElement(By.xpath("//input[@id='sent1']")).click();
			getDriver().findElement(By.xpath("//button[@type='submit']")).click();
		}
		
		@Then("La lista de todas los orders contiene el order \\(producto {string}, cantidad {int}, fecha {string} y proveedor {string}) en la posición {int}")
		public void la_lista_de_todas_los_orders_contiene_el_order_producto_cantidad_fecha_y_proveedor_en_la_posición(String string, Integer int1, String string2, String string3, Integer int2) {
			assertEquals(string, getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr["+int2+"]/td")).getText());
			assertEquals(""+int1, getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr["+int2+"]/td[2]")).getText());
			assertEquals(string2, getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr["+int2+"]/td[3]")).getText());
			assertEquals(string3, getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr["+int2+"]/td[6]")).getText());
			stopDriver();
		}

		@Then("Se me muestra un mensaje de error sobre la cantidad")
		public void se_me_muestra_un_mensaje_de_error_sobre_la_cantidad() {
			assertEquals("tiene que ser mayor o igual que 1",
					getDriver().findElement(By.xpath("//form[@id='edit-order-form']/div/div[2]/div/span[2]")).getText());
			stopDriver();
		}

			@When("Modifico el primero")
			public void modifico_el_primero() {
				getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr/td[8]/a")).click();
			}

			@When("Introduzco como cantidad {int} y guardo el cambio")
			public void introduzco_como_cantidad_y_guardo_el_cambio(Integer int1) {
				getDriver().findElement(By.xpath("//input[@id='quantity']")).click();
				getDriver().findElement(By.xpath("//input[@id='quantity']")).clear();
				getDriver().findElement(By.xpath("//input[@id='quantity']")).sendKeys(""+int1);
				getDriver().findElement(By.xpath("//button[@type='submit']")).click();
			}

			@When("Pulso el botón de borrar el order en posición {int}")
			public void pulso_el_botón_de_borrar_order(Integer int1) {
				getDriver().findElement(By.xpath("//table[@id='orders']/tbody/tr["+int1+"]/td[8]/a[2]")).click();
			}

			@Then("La lista de todos los orders contiene un order menos \\({int})")
			public void la_lista_de_todos_los_orders_contiene_un_order_menos(Integer int1) {
				WebElement table = getDriver().findElement(By.xpath("//table[@id='orders']/tbody"));
				List<WebElement> rows_table = table.findElements(By.tagName("tr"));
				int rows_countAfter = rows_table.size();
				assertThat(rows_countAfter).isEqualTo(int1);
			    stopDriver();
			}
			
			@When("Veo que la lista de orders tiene {int} elementos")
			public void veo_que_la_lista_de_orders_tiene_elementos(Integer int1) {
				WebElement table = getDriver().findElement(By.xpath("//table[@id='orders']/tbody"));
				List<WebElement> rows_table = table.findElements(By.tagName("tr"));
				int rows_countBefore = rows_table.size();
				assertThat(rows_countBefore).isEqualTo(int1);
			}

		
}
