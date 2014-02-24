/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Empresas;
import Entidades.SucursalesPila;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarSucursalesPilaInterface {

    public List<Empresas> buscarEmpresas();

    public void modificarSucursalesPila(List<SucursalesPila> listaSucursalesPila);

    public void borrarSucursalesPila(List<SucursalesPila> listaSucursalesPila);

    public void crearSucursalesPila(List<SucursalesPila> listaSucursalesPila);

    public List<SucursalesPila> consultarSucursalesPila();

    public List<SucursalesPila> consultarSucursalPila(BigInteger secSucursalesPila);

    public BigInteger contarNovedadesAutoLiquidacionesSucursal_Pila(BigInteger secSucursalesPila);

    public BigInteger contarNovedadesCorreccionesAutolSucursal_Pila(BigInteger secSucursalesPila);

    public BigInteger contarOdisCabecerasSucursal_Pila(BigInteger secSucursalesPila);

    public BigInteger contarOdiscorReaccionesCabSucursal_Pila(BigInteger secSucursalesPila);

    public BigInteger contarParametrosInformesSucursal_Pila(BigInteger secSucursalesPila);

    public BigInteger contarUbicacionesGeograficasSucursal_Pila(BigInteger secSucursalesPila);
}
