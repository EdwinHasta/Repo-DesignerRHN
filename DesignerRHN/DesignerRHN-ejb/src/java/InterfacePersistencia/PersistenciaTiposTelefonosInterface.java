/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TiposTelefonos;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface PersistenciaTiposTelefonosInterface {
    public void crear(TiposTelefonos tiposTelefonos);
    public void editar(TiposTelefonos tiposTelefonos);
    public void borrar(TiposTelefonos tiposTelefonos);
    public TiposTelefonos buscarTipoTelefonos(BigInteger id);
    public List<TiposTelefonos> tiposTelefonos();
}
