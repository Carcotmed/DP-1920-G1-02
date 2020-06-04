Feature: Order Creation
   Como administrador, puedo crear un nuevo order

  Scenario: Creación satisfactoria de un nuevo order (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de orders
    And Creo un Order nuevo
    And Introduzco como cantidad 120, eligo el dia a traves del selector y dejo el producto por defecto
    Then La lista de todas los orders contiene el order (producto "Pomadita", cantidad 120, fecha "2020-06-07" y proveedor "Pipo1") en la posición 4

  Scenario: Creación no satisfactoria de un nuevo order (Negative)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de orders
    And Creo un Order nuevo
    And Introduzco como cantidad -120, eligo el dia a traves del selector y dejo el producto por defecto
    Then Se me muestra un mensaje de error sobre la cantidad
    