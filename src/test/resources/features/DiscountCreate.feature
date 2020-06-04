Feature: Discount Creation
   Como administrador, puedo crear un nuevo descuento a un producto

  Scenario: Creación satisfactoria de un nuevo descuento (Positive)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    And Accedo a la página de creación de descuentos
    And Introduzco los valores de porcentaje "12.2", cantidad "89", selecciono el proveedor "Pipo2" y el producto "Juguete"
    Then La lista de todas los descuentos contiene el que creé (porcentaje "12.2", cantidad "89", proveedor "Pipo2" y producto "Juguete")

  Scenario: Creación fallida de un nuevo descuento (Negative)
    Given Estoy logueado con el usuario "admin1" y la contraseña "4dm1n"
    When Voy a la lista de proveedores
    And Voy a la lista de descuentos
    And Accedo a la página de creación de descuentos
    And Introduzco los valores de porcentaje "19", cantidad "-2", selecciono el proveedor "Pipo3" y el producto "Jeringuilla"
    Then Se me muestra un error sobre la cantidad del producto
    