/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.SucursalesPila;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaSucursalesPilaInterface {

    public void crear(SucursalesPila sucursalesPilas);

    public void editar(SucursalesPila sucursalesPilas);

    public void borrar(SucursalesPila sucursalesPilas);

    public List<SucursalesPila> consultarSucursalesPila();

    public List<SucursalesPila> consultarSucursalesPilaPorEmpresa(BigInteger secEmpresa);

    public BigInteger contarUbicacionesGeograficasSucursal_Pila(BigInteger secuencia);

    public BigInteger contarParametrosInformesSucursal_Pila(BigInteger secuencia);

    public BigInteger contarOdiscorReaccionesCabSucursal_Pila(BigInteger secuencia);

    public BigInteger contarOdisCabecerasSucursal_Pila(BigInteger secuencia);

    public BigInteger contarNovedadesCorreccionesAutolSucursal_Pila(BigInteger secuencia);

    public BigInteger contarNovedadesAutoLiquidacionesSucursal_Pila(BigInteger secuencia);
}
