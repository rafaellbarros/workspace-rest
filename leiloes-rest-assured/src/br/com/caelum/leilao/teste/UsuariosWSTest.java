package br.com.caelum.leilao.teste;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.path.xml.XmlPath;

import br.com.caelum.leilao.modelo.Usuario;

public class UsuariosWSTest {
	
	private Usuario mauricio;
	private Usuario guilherme;
	
	@Before
	public void setUp() {
         mauricio = new Usuario(1L,"Mauricio Aniche","mauricio.aniche@caelum.com.br");
         guilherme = new Usuario(2L,"Guilherme Silveira","guilherme.silveira@caelum.com.br");
         
         // RestAssured.baseURI = "http://www.endereco-do-meu-ws.com.br";
         // RestAssured.port = 80;
	}

    @Test
    public void deveRetornarListaDeUsuarios(){

        XmlPath path =
                given().
                header("Accept","application/xml")
                .get("/usuarios").andReturn().xmlPath();

        Usuario usuario1 = path.getObject("list.usuario[0]",Usuario.class);
        Usuario usuario2 = path.getObject("list.usuario[1]",Usuario.class);

        assertEquals(mauricio, usuario1);
        assertEquals(guilherme, usuario2);

    }
    
    @Test
    public void deveRetornarUsuarioPeloId() {
    	JsonPath path = given().header("Accept", "application/json")
			    	.parameter("usuario.id", 1)
			    	.get("/usuarios/show")
			    	.andReturn()
			    	.jsonPath();
    	
    	Usuario usuario = path.getObject("usuario", Usuario.class);
    	assertEquals(mauricio, usuario);
    	
    }
    
    @Test
    public void deveAdicionarUmUsuario() {
    	Usuario rafael = new Usuario("Rafael Barros Silva", "rafaelbarros@gmail.com");
    	
    	XmlPath path = given().header("Accept", "application/xml")
		    	.contentType("application/xml")
		    	.body(rafael)
		    .expect()
		    	.statusCode(200)
		    .when()
		    	.post("/usuarios")
		    .andReturn()
		    	.xmlPath();
    	
    	Usuario resposta = path.getObject("usuario", Usuario.class);
    	
    	assertEquals("Rafael Barros Silva", resposta.getNome());
    	assertEquals("rafaelbarros@gmail.com", resposta.getEmail());
    }
    
    


}
