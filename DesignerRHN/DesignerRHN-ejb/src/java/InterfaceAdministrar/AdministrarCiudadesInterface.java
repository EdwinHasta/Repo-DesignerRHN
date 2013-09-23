/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import java.util.List;


public interface AdministrarCiudadesInterface {
    public List<Ciudades> Ciudades();
    public void modificarCiudad(List<Ciudades> listaCiudadesModificar);
    public void borrarCiudad(Ciudades ciudades);
    public void crearCiudad(Ciudades ciudades);
    public List<Ciudades>  lovCiudades();
}
