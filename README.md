# APP_GEST_PAQU_BACKEND
## Descripcion
Este proyecto tiene como finalidad gestionar la entrega de paquetes para la empresa de Transporte S.A.S, podra getionar las empresas que contrata, gestionar la entrada de los vehiculos y la entrega de los paquetes; para finalmente calcular el valor que se debe pagar a cada empresa que haya contratado segun su configuracion de comisiones

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/JulianSar18/APPGESTPAQUEBACKEND)


## ARQUITECTURA
Este backend se encuentra distribuido por 5 paquetes

- com.tc_28.api.controller
-- En este paquete se encuentran los 5 controlares que dispone el software 
- com.tc_28.api.dto
-- Aqui se encuentra los objetos transformados 
- com.tc_28.api.model
-- Los modelos de la apliacion
- com.tc_28.api.repository
-- Las distintas consultas para la base de datos
- com.tc_28.api.service
-- Las interfaces y clases que contienen la logica 


## TECNOLOGIA

el proyecto se encuntra basado en las siguientes herramientas:


- [SpringBoot](https://spring.io/projects/spring-boot) - creación de aplicaciones independientes basadas en Spring de nivel de producción
- [Gradle](https://gradle.org/) - Construccion de codigo


## INSTALACION
Se requiere tambien la conexion a la base de datos oracle para poder realizar las diferentes operaciones
 [Modelo Base de datos](https://github.com/JulianSar18/APPGESTPAQUEDB)
APP_GEST_PAQU_BACKEND requiere [JAVA SDK](https://www.oracle.com/co/java/technologies/javase/jdk11-archive-downloads.html) V11.0.16 y [Gradle](https://gradle.org/install/) V7.5.1 para ejecutarse

Dependencias
```
    implementation 'org.springframework.boot:spring-boot-starter-data-jdbc'
	implementation 'org.springframework.boot:spring-boot-starter-data-jpa'
	implementation 'org.springframework.boot:spring-boot-starter'
	implementation 'org.springframework.boot:spring-boot-starter-web'
	compileOnly 'org.projectlombok:lombok'
	runtimeOnly 'com.oracle.database.jdbc:ojdbc8'
	annotationProcessor 'org.projectlombok:lombok'
	testImplementation 'org.springframework.boot:spring-boot-starter-test'
	implementation 'com.github.ozlerhakan:poiji:4.1.1'
	implementation 'com.itextpdf:itextpdf:5.5.13.3'
	implementation 'javax.servlet:javax.servlet-api:4.0.1'
```

#### CORRER PROYECTO

Usar Spring tools Suite 4 Como IDE para el desarrollo y prueba de la aplicacion
Verificar credenciales de la base de datos



## License

MIT

**Free Software**

