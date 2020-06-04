Feature: Order Update
   Como administrador, puedo modificar un nuevo order

  Scenario: Modificación satisfactoria de un order (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de orders
    And Modifico el primero
    And Introduzco como cantidad 348 y guardo el cambio
    Then La lista de todas los orders contiene el order (producto "Pomadita", cantidad 348, fecha "2013-01-01" y proveedor "Pipo1") en la posición 1

  Scenario: Modificación no satisfactoria de un order (Negative)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de orders
    And Creo un Order nuevo
    And Introduzco como cantidad -120, eligo el dia a traves del selector y dejo el producto por defecto
    Then Se me muestra un mensaje de error sobre la cantidad
    