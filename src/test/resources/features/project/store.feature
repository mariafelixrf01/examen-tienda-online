
Feature: Product - Store

  Scenario: Validación del precio de un producto
    Given estoy en la página de la tienda
    And me logueo con mi usuario "mariafelixrf01@gmail.com" y clave "AZULROJO10@M"
    When navego a la categoria "Clothes" y subcategoria "Men"
    And agrego 2 unidades del primer producto al carrito
    Then valido en el popup la confirmación del producto agregado
    And valido en el popup que el monto total sea calculado correctamente
    When finalizo la compra
    Then valido el titulo de la pagina del carrito
    And vuelvo a validar el calculo de precios en el carrito


  Scenario: Validación de inicio de sesión con credenciales inválidas
    Given estoy en la página de la tienda
    When intento iniciar sesión con credenciales inválidas
    Then valido que se muestre un mensaje de error de autenticación
    And valido que permanezca en la página de inicio de sesión

  Scenario: Validación de una categoría inexistente
    Given estoy en la página de la tienda
    When navego a una categoria inexistente "Autos"
    Then valido que la categoria no se encuentre disponible