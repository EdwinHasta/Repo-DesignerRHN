/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Ciudades;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarTerceroInterface {
    
    public List<Terceros> obtenerListTerceros(BigInteger secuencia);
    public void modificarTercero(Terceros t);
    public void borrarTercero(Terceros t);
    public void crearTercero(Terceros t);
    //
    public List<TercerosSucursales> obtenerListTercerosSucursales(BigInteger secuencia);
    public void modificarTerceroSucursales(TercerosSucursales t);
    public void borrarTerceroSucursales(TercerosSucursales t);
    public void crearTerceroSucursales(TercerosSucursales t);
    //
    public List<Empresas> listEmpresas();
    public List<Ciudades> listCiudades();
    
}
