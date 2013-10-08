package Administrar;

import Entidades.Empleados;
import Entidades.EstadosAfiliaciones;
import Entidades.Terceros;
import Entidades.TercerosSucursales;
import Entidades.TiposEntidades;
import Entidades.VigenciasAfiliaciones;
import InterfaceAdministrar.AdministrarVigenciasAfiliaciones3Interface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEstadosAfiliacionesInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaTercerosInterface;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
import InterfacePersistencia.PersistenciaTiposEntidadesInterface;
import InterfacePersistencia.PersistenciaVigenciasAfiliacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasTiposContratosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class AdministrarVigenciasAfiliaciones3 implements AdministrarVigenciasAfiliaciones3Interface {

    @EJB
    PersistenciaVigenciasAfiliacionesInterface persistenciaVigenciasAfilicaciones;
    @EJB
    PersistenciaTiposEntidadesInterface persistenciaTiposEntidades;
    @EJB
    PersistenciaTercerosInterface persistenciaTerceros;
    @EJB
    PersistenciaEstadosAfiliacionesInterface persistenciaEstadosAfiliaciones;
    @EJB
    PersistenciaTercerosSucursalesInterface persistenciaTercerosSucursales;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    @EJB
    PersistenciaVigenciasTiposContratosInterface persistenciaVigenciasTiposContratos;
    //
    List<VigenciasAfiliaciones> listVigenciasAfiliaciones;
    List<Terceros> listTercetos;
    List<EstadosAfiliaciones> listEstadosAfiliaciones;
    List<TiposEntidades> listTiposEntidades;
    List<TercerosSucursales> listTercerosSucursales;
    Empleados empleado;
    Date fechaContratacion;

    @Override
    public void crearVigenciaAfiliacion(VigenciasAfiliaciones vigencia) {
        try {
            persistenciaVigenciasAfilicaciones.crear(vigencia);
        } catch (Exception e) {
            System.out.println("Error crearVigenciaAfiliacion Admi : " + e.toString());
        }
    }

    @Override
    public void editarVigenciaAfiliacion(VigenciasAfiliaciones vigencia) {
        try {
            persistenciaVigenciasAfilicaciones.editar(vigencia);
        } catch (Exception e) {
            System.out.println("Error editarVigenciaAfiliacion Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVigenciaAfiliacion(VigenciasAfiliaciones vigencia) {
        try {
            persistenciaVigenciasAfilicaciones.borrar(vigencia);
        } catch (Exception e) {
            System.out.println("Error borrarVigenciaAfiliacion Admi : " + e.toString());
        }
    }

    @Override
    public List<VigenciasAfiliaciones> listVigenciasAfiliacionesEmpleado(BigInteger secuencia) {
        try {
            listVigenciasAfiliaciones = persistenciaVigenciasAfilicaciones.buscarVigenciasAfiliacionesEmpleado(secuencia);
            return listVigenciasAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error listVigenciasAfiliacionesEmpleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public VigenciasAfiliaciones vigenciaAfiliacionSecuencia(BigDecimal secuencia) {
        try {
            VigenciasAfiliaciones retorno = persistenciaVigenciasAfilicaciones.buscarVigenciasAfiliacionesSecuencia(secuencia);
            return retorno;
        } catch (Exception e) {
            System.out.println("Error vigenciaAfiliacionSecuencia Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<Terceros> listTerceros() {
        try {
            listTercetos = persistenciaTerceros.buscarTerceros();
            return listTercetos;
        } catch (Exception e) {
            System.out.println("Error listTerceros Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<EstadosAfiliaciones> listEstadosAfiliaciones() {
        try {
            listEstadosAfiliaciones = persistenciaEstadosAfiliaciones.buscarEstadosAfiliaciones();
            return listEstadosAfiliaciones;
        } catch (Exception e) {
            System.out.println("Error listEstadosAfiliaciones Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TiposEntidades> listTiposEntidades() {
        try {
            listTiposEntidades = persistenciaTiposEntidades.buscarTiposEntidades();
            return listTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error listTiposEntidades Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<TercerosSucursales> listTercerosSucursales() {
        try {
            listTercerosSucursales = persistenciaTercerosSucursales.buscarTercerosSucursales();
            return listTercerosSucursales;
        } catch (Exception e) {
            System.out.println("Error listTercerosSucursales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados obtenerEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleado(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("Error obtenerEmpleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Long validacionTercerosSurcursalesNuevaVigencia(BigInteger secuencia, Date fechaInicial, BigDecimal secuenciaTE, BigDecimal secuenciaTer) {
        try {
            Long result = persistenciaSolucionesNodos.validacionTercerosVigenciaAfiliacion(secuencia, fechaInicial, secuenciaTE, secuenciaTer);
            return result;
        } catch (Exception e) {
            System.out.println("Error validacionTercerosSurcursales Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Date fechaContratacion(Empleados empleado) {
        try {
            fechaContratacion = persistenciaVigenciasTiposContratos.fechaMaxContratacion(empleado);
            return fechaContratacion;
        } catch (Exception e) {
            System.out.println("Error fechaContratacion Admi : " + e.toString());
            return null;
        }
    }
}
