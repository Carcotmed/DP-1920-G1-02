Feature: Discount Update
   Como usuario, puedo modificar un descuento de un producto

  Scenario: Modificación satisfactoria de un descuento (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    And Pulso el botón de editar descuento
    And Modifico el porcentaje a 75.0
    Then La entrada de la lista de descuentos que modifiqué muestra el valor cambiado (75.0)

  Scenario: Modificación fallida de un descuento (Negative)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    And Pulso el botón de editar descuento
    And Modifico el porcentaje a 145.0
    Then Se me muestra un mensaje de error sobre el porcentaje