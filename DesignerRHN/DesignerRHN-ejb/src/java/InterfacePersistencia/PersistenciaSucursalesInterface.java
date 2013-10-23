/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Sucursales;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSucursalesInterface {

    public void crear(Sucursales sucursales);

    public void editar(Sucursales sucursales);

    public void borrar(Sucursales sucursales);

    public Sucursales buscarSucursal(BigInteger secuenciaS);

    public List<Sucursales> buscarSucursales();
}
