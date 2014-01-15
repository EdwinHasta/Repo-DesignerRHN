/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.FormasPagos;
import Entidades.MetodosPagos;
import Entidades.Periodicidades;
import Entidades.Sucursales;
import Entidades.VigenciasFormasPagos;
import InterfaceAdministrar.AdministrarEmplVigenciasFormasPagosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaFormasPagosInterface;
import InterfacePersistencia.PersistenciaMetodosPagosInterface;
import InterfacePersistencia.PersistenciaSucursalesInterface;
import InterfacePersistencia.PersistenciaVigenciasFormasPagosInterface;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarEmplVigenciasFormasPagos implements AdministrarEmplVigenciasFormasPagosInterface {

    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaSucursalesInterface persistenciaSucursales;
    @EJB
    PersistenciaVigenciasFormasPagosInterface PersistenciaVigenciasFormasPagos;
    @EJB
    PersistenciaFormasPagosInterface PersistenciaFormasPagos;
    @EJB
    PersistenciaMetodosPagosInterface PersistenciaMetodosPagos;
    @EJB
    PersistenciaPeriodicidadesInterface persistenciaPeriodicidades;
    Empleados empleado;
    VigenciasFormasPagos vigenciaFormaPagoPorEmpleado;
    List<VigenciasFormasPagos> listVigenciasFormasPagosPorEmpleado;
    List<Sucursales> listSucursales;
    List<FormasPagos> listFormasPagos;
    List<MetodosPagos> listMetodosPagos;
    List<Periodicidades> listPeriodicidades;

    @Override
    public List<VigenciasFormasPagos> consultarVigenciasFormasPagosPorEmpleado(BigInteger secEmpleado) {
        try {
            listVigenciasFormasPagosPorEmpleado = PersistenciaVigenciasFormasPagos.buscarVigenciasFormasPagosPorEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Formas Pagos");
            listVigenciasFormasPagosPorEmpleado = null;
        }
        return listVigenciasFormasPagosPorEmpleado;
    }

    @Override
    public void modificarVigenciasFormasPagos(List<VigenciasFormasPagos> listaVigenciasFormasPagos) {
        for (int i = 0; i < listaVigenciasFormasPagos.size(); i++) {
            System.out.println("Administrar Modificando...");
            listaVigenciasFormasPagos.get(i);
            if (vigenciaFormaPagoPorEmpleado.getSucursal().getSecuencia() == null) {
                vigenciaFormaPagoPorEmpleado.setSucursal(null);
            }
            PersistenciaVigenciasFormasPagos.editar(listaVigenciasFormasPagos.get(i));
        }
    }

    @Override
    public void borrarVigenciasFormasPagos(List<VigenciasFormasPagos> listaVigenciasFormasPagos) {
        for (int i = 0; i < listaVigenciasFormasPagos.size(); i++) {
            System.out.println("Administrar Modificando...");
            if (vigenciaFormaPagoPorEmpleado.getSucursal().getSecuencia() == null) {
                vigenciaFormaPagoPorEmpleado.setSucursal(null);
            }
            PersistenciaVigenciasFormasPagos.borrar(listaVigenciasFormasPagos.get(i));
        }
    }

    @Override
    public void crearVigencasFormasPagos(VigenciasFormasPagos vigenciasFormasPagos) {
        PersistenciaVigenciasFormasPagos.crear(vigenciasFormasPagos);
    }

    @Override
    public Empleados consultarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleados.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("AdministrarVigenciasFormasPagos buscarEmpleado error" + e);
            empleado = null;
            return empleado;
        }
    }

    @Override
    public List<Sucursales> consultarLOVSucursales() {
        try {
            listSucursales = persistenciaSucursales.buscarSucursales();
            return listSucursales;
        } catch (Exception e) {
            System.err.println("AdministrarVigencasFormasPagos Error en la busqueda de sucursales " + e);
            return null;
        }
    }

    @Override
    public List<FormasPagos> consultarLOVFormasPagos() {
        try {
            listFormasPagos = PersistenciaFormasPagos.buscarFormasPagos();
            return listFormasPagos;
        } catch (Exception e) {
            System.err.println("AdministrarVigencasFormasPagos Error en la busqueda de Formas pagos " + e);
            return null;
        }
    }

    @Override
    public List<MetodosPagos> consultarLOVMetodosPagos() {
        try {
            listMetodosPagos = PersistenciaMetodosPagos.buscarMetodosPagos();
            return listMetodosPagos;
        } catch (Exception e) {
            System.err.println("AdministrarVigencasFormasPagos Error en la busqueda de Metodos Pagos " + e);
            return null;
        }
    }

    @Override
    public List<Periodicidades> consultarLOVPerdiocidades() {
        try {
            listPeriodicidades = persistenciaPeriodicidades.buscarPeriodicidades();
            return listPeriodicidades;
        } catch (Exception e) {
            System.err.println("AdministrarVigencasFormasPagos Error en la busqueda de Periodicidades " + e);
            return null;
        }
    }
}
