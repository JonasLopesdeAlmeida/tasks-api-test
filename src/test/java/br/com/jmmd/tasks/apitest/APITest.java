package br.com.jmmd.tasks.apitest;

import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class APITest {
	
	@BeforeClass
	public static void setup() {
		RestAssured.baseURI = "http://localhost:8080/tasks-backend";
	}
	
	@Test
	public void deveRetornarTarefas() {
		RestAssured.given()
		.when()
		   .get("/todo")
		.then()
		   .statusCode(200)
		;
	}
	
	@Test
	public void deveAdicionarUmaTarefaComSucesso() {
		RestAssured.given()
		   //PASSANDO OBJ JSON VIA BODY
		   .body("	{\r\n" + 
		   		"		\"task\":\"Test via api\",\r\n" + 
		   		"		\"dueDate\":\"2021-12-17\"\r\n" + 
		   		"	}")
		   //contentType USADO PARA DAR SUPORTE JSON PARA O TEST DA API
		   .contentType(ContentType.JSON)
		.when()
		   .post("/todo")
		.then()
		   //.log().all() USADO PARA MOSTRAR AS RESPOSTAS NO CONSOLE.
		   .statusCode(201)
		;
	}
	
	@Test
	public void naoDeveAdicionarUmaTarefaInvalida() {
		RestAssured.given()
		   //PASSANDO OBJ JSON VIA BODY
		   .body("	{\r\n" + 
		   		"		\"task\":\"Test via api\",\r\n" + 
		   		"		\"dueDate\":\"2021-06-11\"\r\n" + 
		   		"	}")
		   //contentType USADO PARA DAR SUPORTE JSON PARA O TEST DA API
		   .contentType(ContentType.JSON)
		.when()
		   .post("/todo")
		.then()
		   .log().all() 
		   .statusCode(400)
		   //validando a mensagem. CoreMatchers usado para lidar com duas strings.
		   .body("message", CoreMatchers.is("Due date must not be in past"))
		;
	}
	
	

}
