/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Bancos;
import Entidades.Ciudades;
import Entidades.Sucursales;
import InterfaceAdministrar.AdministrarSucursalesInterface;
import InterfacePersistencia.PersistenciaBancosInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaSucursalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarSucursales implements AdministrarSucursalesInterface {

    //--------------------------------------------------------------------------
    //ATRIBUTOS
    //--------------------------------------------------------------------------    
    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaSucursales'.
     */
    @EJB
    PersistenciaSucursalesInterface persistenciaSucursales;
    @EJB
    PersistenciaBancosInterface persistenciaBancos;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------

    public void modificarSucursales(List<Sucursales> listaSucursales) {
        for (int i = 0; i < listaSucursales.size(); i++) {
            System.out.println("Administrar Modificando...");
            persistenciaSucursales.editar(listaSucursales.get(i));
        }
    }

    public void borrarSucursales(List<Sucursales> listaSucursales) {
        for (int i = 0; i < listaSucursales.size(); i++) {
            System.out.println("Administrar Borrando...");
            persistenciaSucursales.borrar(listaSucursales.get(i));
        }
    }

    public void crearSucursales(List<Sucursales> listaSucursales) {
        for (int i = 0; i < listaSucursales.size(); i++) {
            persistenciaSucursales.crear(listaSucursales.get(i));
        }
    }

    @Override
    public List<Sucursales> consultarSucursales() {
        List<Sucursales> listaSucursales;
        listaSucursales = persistenciaSucursales.consultarSucursales();
        return listaSucursales;
    }

    public List<Bancos> consultarLOVBancos() {
        List<Bancos> listLOVBancos;
        listLOVBancos = persistenciaBancos.buscarBancos();
        return listLOVBancos;
    }

    public List<Ciudades> consultarLOVCiudades() {
        List<Ciudades> listLOVCiudades;
        listLOVCiudades = persistenciaCiudades.ciudades();
        return listLOVCiudades;
    }

    public BigInteger contarVigenciasFormasPagosSucursal(BigInteger secuenciaSucursal) {
        BigInteger contarVigenciasFormasPagosSucursal;
        contarVigenciasFormasPagosSucursal = persistenciaSucursales.contarVigenciasFormasPagosSucursal(secuenciaSucursal);
        return contarVigenciasFormasPagosSucursal;
    }
}
