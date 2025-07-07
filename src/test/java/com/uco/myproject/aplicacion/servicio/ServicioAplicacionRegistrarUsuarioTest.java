package com.uco.myproject.aplicacion.servicio;

import com.uco.myproject.aplicacion.dto.DtoUsuario;
import com.uco.myproject.dominio.modelo.Usuario;
import com.uco.myproject.dominio.servicio.ServicioCifrarTexto;
import com.uco.myproject.dominio.servicio.ServicioGuardarUsuario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class ServicioAplicacionRegistrarUsuarioTest {

    @Test
    void deberiaEncriptarClaveAntesDeCrearUsuario() {
        // arrange
        String claveTextoPlano = "123456";
        String claveEncriptada = "encrypted_password_hash";
        DtoUsuario dto = new DtoUsuario("test.user", claveTextoPlano);
        
        ServicioGuardarUsuario servicioGuardarUsuario = Mockito.mock(ServicioGuardarUsuario.class);
        ServicioCifrarTexto servicioCifrarTexto = Mockito.mock(ServicioCifrarTexto.class);
        
        Mockito.when(servicioCifrarTexto.ejecutar(claveTextoPlano)).thenReturn(claveEncriptada);
        Mockito.when(servicioGuardarUsuario.ejecutar(Mockito.any(Usuario.class))).thenReturn(1L);
        
        ServicioAplicacionRegistrarUsuario servicio = new ServicioAplicacionRegistrarUsuario(
            servicioGuardarUsuario, servicioCifrarTexto);
        
        // act
        servicio.ejecutar(dto);
        
        // assert - capture the Usuario object passed to guardar
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        Mockito.verify(servicioGuardarUsuario).ejecutar(usuarioCaptor.capture());
        
        Usuario usuarioGuardado = usuarioCaptor.getValue();
        
        // Verify the password stored in Usuario is encrypted, not plain text
        Assertions.assertEquals(claveEncriptada, usuarioGuardado.getClave(), 
            "La clave debe estar cifrada cuando se crea el objeto Usuario");
        Assertions.assertNotEquals(claveTextoPlano, usuarioGuardado.getClave(), 
            "La clave no debe estar en texto plano en el objeto Usuario");
        
        // Verify encryption service was called with plain text password
        Mockito.verify(servicioCifrarTexto).ejecutar(claveTextoPlano);
    }
}