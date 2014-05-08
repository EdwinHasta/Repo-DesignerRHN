/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.MotivosReemplazos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarMotivosReemplazosInterface {
    	/**
     * Método encargado de obtener el Entity Manager el cual tiene
     * asociado la sesion del usuario que utiliza el aplicativo.
     * @param idSesion Identificador se la sesion.
     */
    public void obtenerConexion(String idSesion);
    public List<MotivosReemplazos> MotivosReemplazos();
    public List<MotivosReemplazos> lovTiposReemplazos();
}
