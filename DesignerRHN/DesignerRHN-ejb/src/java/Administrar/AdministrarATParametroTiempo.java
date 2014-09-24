package Administrar;

import Entidades.ActualUsuario;
import Entidades.Cuadrillas;
import Entidades.Empleados;
import Entidades.ParametrosTiempos;
import InterfaceAdministrar.AdministrarATParametroTiempoInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaCuadrillasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaParametrosTiemposInterface;
import InterfacePersistencia.PersistenciaTurnosEmpleadosInterface;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
@Stateful
public class AdministrarATParametroTiempo implements AdministrarATParametroTiempoInterface {
    
    @EJB
    PersistenciaCuadrillasInterface persistenciaCuadrillas;
    @EJB
    PersistenciaParametrosTiemposInterface persistenciaParametrosTiempos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleadosInterface;
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    @EJB
    PersistenciaTurnosEmpleadosInterface persistenciaTurnosEmpleados;
    @EJB
    AdministrarSesionesInterface administrarSesiones;
    
    private EntityManager em;

    //--------------------------------------------------------------------------
    //MÉTODOS
    //--------------------------------------------------------------------------
    //@Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<Empleados> lovEmpleados() {
        try {
            List<Empleados> lista = persistenciaEmpleadosInterface.buscarEmpleados(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovEmpleados Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public ParametrosTiempos consultarParametrosTiemposPorUsarioBD(String usuarioBD) {
        try {
            ParametrosTiempos lista = persistenciaParametrosTiempos.buscarParametrosTiemposPorUsuarioBD(em, usuarioBD);
            return lista;
        } catch (Exception e) {
            System.out.println("Error consultarParametrosTiemposPorUsarioBD Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public ActualUsuario obtenerActualUsuario() {
        try {
            ActualUsuario user = persistenciaActualUsuario.actualUsuarioBD(em);
            return user;
        } catch (Exception e) {
            System.out.println("Error obtenerActualUsuario Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public List<Cuadrillas> lovCuadrillas() {
        try {
            List<Cuadrillas> lista = persistenciaCuadrillas.buscarCuadrillas(em);
            return lista;
        } catch (Exception e) {
            System.out.println("Error lovCuadrillas Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public Date obtenerFechaInicialMinTurnosEmpleados() {
        try {
            Date fecha = persistenciaTurnosEmpleados.obtenerFechaInicialMinimaTurnosEmpleados(em);
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaInicialMinTurnosEmpleados Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public Date obtenerFechaFinalMaxTurnosEmpleados() {
        try {
            Date fecha = persistenciaTurnosEmpleados.obtenerFechaFinalMaximaTurnosEmpleados(em);
            return fecha;
        } catch (Exception e) {
            System.out.println("Error obtenerFechaFinalMaxTurnosEmpleados Admi : " + e.toString());
            return null;
        }
    }
    
    @Override
    public void ejecutarPKG_INSERTARCUADRILLA(BigInteger cuadrilla, Date fechaDesde, Date fechaHasta) {
        try {
            persistenciaParametrosTiempos.ejecutarPKG_INSERTARCUADRILLA(em, cuadrilla, fechaDesde, fechaHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_INSERTARCUADRILLA Admi : " + e.toString());
        }
    }
    
    @Override
    public void ejecutarPKG_SIMULARTURNOSEMPLEADOS(Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaParametrosTiempos.ejecutarPKG_SIMULARTURNOSEMPLEADOS(em, fechaDesde, fechaHasta, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_SIMULARTURNOSEMPLEADOS Admi : " + e.toString());
        }
    }
    
    @Override
    public void ejecutarPKG_LIQUIDAR(Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta, String formulaLiquidacion) {
        try {
            persistenciaParametrosTiempos.ejecutarPKG_LIQUIDAR(em, fechaDesde, fechaHasta, emplDesde, emplHasta, formulaLiquidacion);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_LIQUIDAR Admi : " + e.toString());
        }
    }
    
    @Override
    public void ejecutarPKG_EliminarProgramacion(Date fechaDesde, Date fechaHasta) {
        try {
            persistenciaParametrosTiempos.ejecutarPKG_EliminarProgramacion(em, fechaDesde, fechaHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_EliminarProgramacion Admi : " + e.toString());
        }
    }
    
    @Override
    public void ejecutarPKG_ELIMINARSIMULACION(BigInteger cuadrilla, Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaParametrosTiempos.ejecutarPKG_ELIMINARSIMULACION(em, cuadrilla, fechaDesde, fechaHasta, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_ELIMINARSIMULACION Admi : " + e.toString());
        }
    }
    
    @Override
    public int ejecutarPKG_CONTARNOVEDADESLIQ(Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            int conteo = persistenciaTurnosEmpleados.ejecutarPKG_CONTARNOVEDADESLIQ(em, fechaDesde, fechaHasta, emplDesde, emplHasta);
            return conteo;
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_CONTARNOVEDADESLIQ Admi : " + e.toString());
            return -1;
        }
    }
    
    @Override
    public void ejecutarPKG_ELIMINARLIQUIDACION(Date fechaDesde, Date fechaHasta, BigInteger emplDesde, BigInteger emplHasta) {
        try {
            persistenciaTurnosEmpleados.ejecutarPKG_ELIMINARLIQUIDACION(em, fechaDesde, fechaHasta, emplDesde, emplHasta);
        } catch (Exception e) {
            System.out.println("Error ejecutarPKG_ELIMINARLIQUIDACION Admi : " + e.toString());
        }
    }
    
    @Override
    public void modificarParametroTiempo(ParametrosTiempos parametro) {
        try {
            persistenciaParametrosTiempos.editar(em, parametro);
        } catch (Exception e) {
            System.out.println("Error modificarParametroTiempo Admi : " + e.toString());
        }  
    }
    
}
