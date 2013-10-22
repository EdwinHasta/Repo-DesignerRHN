/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.TercerosSucursales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaTercerosSucursalesInterface {
    
    public void crear(TercerosSucursales tercerosSucursales);
    public void editar(TercerosSucursales tercerosSucursales);
    public void borrar(TercerosSucursales tercerosSucursales);
    public TercerosSucursales buscarTerceroSucursal(Object id);
    public List<TercerosSucursales> buscarTercerosSucursales();
    public TercerosSucursales buscarTercerosSucursalesSecuencia(BigInteger secuencia);
    public List<TercerosSucursales> buscarTercerosSucursalesPorTerceroSecuencia(BigInteger secuencia);
    
}
