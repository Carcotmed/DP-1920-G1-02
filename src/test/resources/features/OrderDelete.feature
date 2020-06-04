Feature: Order Deletion
   Como administrador, puedo borrar un order

  Scenario: Eliminación satisfactoria de un order (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de orders
    And Veo que la lista de orders tiene 4 elementos
    And Pulso el botón de borrar el order en posición 2
    Then La lista de todos los orders contiene un order menos (3)

  Scenario: Eliminación no satisfactoria de un order (Negative)
    Given Estoy logueado con el usuario "vet1" y la contraseña "v3t"
    When Voy a la lista de orders
    Then No se me permite acceder y se me muestra un error de tipo 403 "Forbidden"
    