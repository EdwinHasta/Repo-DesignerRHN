/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Direcciones;
import Entidades.Personas;
import java.math.BigInteger;
import java.util.List;


public interface AdministrarDireccionesInterface {
    
     public List<Direcciones> direccionesPersona(BigInteger secPersona);
     public Personas encontrarPersona(BigInteger secPersona);
     public List<Ciudades>  lovCiudades();
     public void modificarDireccion(List<Direcciones> listaDireccionesModificar);
     public void borrarTelefono(Direcciones direcciones);
     public void crearTelefono(Direcciones direcciones);
     
}
