README
Proyecto: Gestión de Exámenes con Java y MySQL

Este proyecto implementa un sistema de gestión de unidades didácticas, enunciados y convocatorias de examen, utilizando Java en consola con persistencia en:

Base de datos MySQL

Fichero serializado (convocatorias.dat)

📂 Contenido del repositorio

Código fuente completo en Java (/src).

Archivo ejecutable .jar en la carpeta dist/.

Carpeta lib/ con las librerías necesarias (incluido el conector MySQL).

Diagrama de clases (diagrama.png).

Javadoc generado en la carpeta dist/javadoc/.

Script SQL retoconecta.sql para crear la base de datos y poblarla con datos de prueba.

Fichero de persistencia convocatorias.dat.

🚀 Instrucciones de ejecución

Requisitos previos:

Tener instalado Java 8 o superior.

Tener instalado MySQL Server.

Importar el script retoconecta.sql en MySQL:

mysql -u root -p < retoconecta.sql


Configurar conexión MySQL en DaoImplementacionMySql:

private static final String URL = "jdbc:mysql://localhost:3306/retoconecta";
private static final String USER = "tu_usuario";
private static final String PASSWORD = "tu_password";


Ejecutar la aplicación:

Asegúrese de que la carpeta lib/ con el conector MySQL está junto al .jar.

Desde la terminal:

java -jar dist/RetoConecta.jar


Opciones principales del menú:

Crear unidad didáctica

Crear convocatoria

Crear enunciado de examen

Consultar enunciados por unidad

Consultar convocatorias de un enunciado

Visualizar documento asociado a un enunciado

Asignar enunciado a convocatoria

🌍 Repositorio online

El código fuente se encuentra disponible en el siguiente repositorio:

👉 https://github.com/Debetiesto/RetoConecta.git

📌 Notas

El fichero convocatorias.dat se utiliza para almacenar las convocatorias serializadas.

Si se elimina convocatorias.dat, el programa puede regenerarlo al crear nuevas convocatorias.

Revise la configuración de usuario/contraseña MySQL en DaoImplementacionMySql para evitar
