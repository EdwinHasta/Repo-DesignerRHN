package Administrar;

import Entidades.Ciudades;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import InterfaceAdministrar.AdministrarTerceroInterface;
import InterfacePersistencia.PersistenciaCiudadesInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarTercero implements AdministrarTerceroInterface{

    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaTercerosSucursalesInterface persistenciaTercerosSucursales;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaCiudadesInterface persistenciaCiudades;
    //
    List<Empresas> listEmpresas;
    List<Terceros> listTerceros;
    Terceros tercero;
    List<TercerosSucursales> listTercerosSurcursales;
    TercerosSucursales terceroSucursal;
    List<Ciudades> listCiudades;

    @Override
    public List<Terceros> obtenerListTerceros(BigInteger secuencia) {
        try {
            listTerceros = persistenciaTerceros.lovTerceros(secuencia);
            return listTerceros;
        } catch (Exception e) {
            System.out.println("Error en obtenerListTerceros Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void modificarTercero(Terceros t) {
        try {
            persistenciaTerceros.editar(t);
        } catch (Exception e) {
            System.out.println("Error en modificarTercero Admi : " + e.toString());
        }
    }

    @Override
    public void borrarTercero(Terceros t) {
        try {
            persistenciaTerceros.borrar(t);
        } catch (Exception e) {
            System.out.println("Error en borrarTercero Admi : " + e.toString());

        }
    }

    @Override
    public void crearTercero(Terceros t) {
        try {
            persistenciaTerceros.crear(t);
        } catch (Exception e) {
            System.out.println("Error en crearTercero Admi : " + e.toString());

        }
    }

    @Override
    public List<TercerosSucursales> obtenerListTercerosSucursales(BigInteger secuencia) {
        try {
            listTercerosSurcursales = persistenciaTercerosSucursales.buscarTercerosSucursalesPorTerceroSecuencia(secuencia);
            return listTercerosSurcursales;
        } catch (Exception e) {
            System.out.println("Error obtenerListTercerosSucursales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public void modificarTerceroSucursales(TercerosSucursales t) {
        try {
            persistenciaTercerosSucursales.borrar(t);
        } catch (Exception e) {
            System.out.println("Error en modificarTerceroSucursales Admi : " + e.toString());
        }
    }

    @Override
    public void borrarTerceroSucursales(TercerosSucursales t) {
        try {
            persistenciaTercerosSucursales.borrar(t);
        } catch (Exception e) {
            System.out.println("Error en borrarTerceroSucursales Admi : " + e.toString());
        }
    }

    @Override
    public void crearTerceroSucursales(TercerosSucursales t) {
        try {
            persistenciaTercerosSucursales.borrar(t);
        } catch (Exception e) {
            System.out.println("Error en crearTerceroSucursales Admi : " + e.toString());
        }
    }

    @Override
    public List<Empresas> listEmpresas() {
        try {
            listEmpresas = persistenciaEmpresas.buscarEmpresas();
            return listEmpresas;
        } catch (Exception e) {
            System.out.println("Error en listEmpresas Admi : "+e.toString());
            return null;
        }
    }
    
    @Override
    public List<Ciudades> listCiudades(){
        try{
            listCiudades = persistenciaCiudades.ciudades();
            return listCiudades;
        }catch(Exception e){
            System.out.println("Error en listCiudades Admi : "+e.toString());
            return null;
        }
    }

}
