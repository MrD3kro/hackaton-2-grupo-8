# Hackaton - 2
## Grupo 8
## integrantes: 
* Camilo Forero | rol :  Arquitecto/a del modelo
* Camilo Bohorquez | rol:  QA (pruebas)
* Cristian David Valencia | rol: Responsable de reglas
* Leoneider Gonzalez | rol: Responsable de la terminal, Integrador/a Git
**Cómo ejecutar el proyecto en IntelliJ?**
Se dirige al paquete src/app y en la clase ejecutable Main se corre todo el programa.
## Preguntas 6.3
**¿Por qué un mapa y no una lista?**
Hashmap y no lista porque queremos que no se repita cliente por cedula
**¿Qué ventaja tiene que no se repitan?**
Así no tenemos que preocuparnos por duplicados.
**¿Por qué el operador nunca escribe los km?**
El operador nunca escribe los km porque podria equivocarse
**¿Por qué aquí sí sirve una lista?**
Porque el codigo es aleotorio y no lo tiene que ingresar el operador se asigna automaticamente a cada repartidor, entonces no es necesario tener un orden.
**¿Cómo encuentran un pedido por su número?**
Tenemos un valor incremental que nos da un numero de pedido siempre.
## Reflexión 
**¿Qué pasó con los pedidos del día?**
Los pedidos se borran porque quedan en tiempo de ejecucion y no se alojan en una colleccion.
**¿Cómo compartirían los clientes y los pedidos entre las dos?**
Extendiendo de las clases Clientes y Pedidos.
