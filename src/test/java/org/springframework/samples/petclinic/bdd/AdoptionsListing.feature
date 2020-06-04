Feature: Adoption Listing
  Como usuario, puedo ver la lista de las adopciones que he realizado

  Scenario: Visión satisfactoria de mi lista de adopciones (Positive)
    Given Estoy logueado con el usuario "userA1" y la contraseña "patata"
    When Voy a la lista de mis adopciones
    Then La lista de todas mis adopciones aparece en pantalla
    