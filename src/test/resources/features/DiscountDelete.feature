@ignore
Feature: Discount Deletion
   Como usuario, puedo borrar un descuento de un producto

  Scenario: Eliminación satisfactoria de un descuento (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    And Veo que la lista tiene 3 elementos
    And Pulso el botón de borrar descuento
    Then La lista de todos los descuentos contiene un descuento menos (2)

  Scenario: Eliminación fallida de un descuento (Negative)
    Given Estoy logueado con el usuario "vet1" y la contraseña "v3t"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    Then No se me permite acceder y se me muestra un error de tipo 403 "Forbidden"
