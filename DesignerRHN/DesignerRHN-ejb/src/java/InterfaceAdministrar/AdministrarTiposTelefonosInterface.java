/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.TiposTelefonos;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTiposTelefonosInterface {
    
    public List<TiposTelefonos> tiposTelefonos();
    public void modificarTipoTelefono(List<TiposTelefonos> listaTiposTelefonosModificar);
    public void borrarTipoTelefono(TiposTelefonos tipoTelefono);
    public void crearTipoTelefono(TiposTelefonos tipoTelefono);
    
}
