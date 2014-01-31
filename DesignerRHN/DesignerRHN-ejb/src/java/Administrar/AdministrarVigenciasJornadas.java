package Administrar;

import Entidades.Empleados;
import Entidades.JornadasLaborales;
import Entidades.TiposDescansos;
import Entidades.VigenciasCompensaciones;
import Entidades.VigenciasJornadas;
import InterfaceAdministrar.AdministrarVigenciasJornadasInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaJornadasLaboralesInterface;
import InterfacePersistencia.PersistenciaTiposDescansosInterface;
import InterfacePersistencia.PersistenciaVigenciasCompensacionesInterface;
import InterfacePersistencia.PersistenciaVigenciasJornadasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Remove;
import javax.ejb.Stateful;

/**
 *
 * @author AndresPineda
 */
@Stateful
public class AdministrarVigenciasJornadas implements AdministrarVigenciasJornadasInterface {

    @EJB
    PersistenciaVigenciasJornadasInterface persistenciaVigenciasJornadas;
    @EJB
    PersistenciaJornadasLaboralesInterface persistenciaJornadasLaborales;
    @EJB
    PersistenciaTiposDescansosInterface persistenciaTiposDescansos;
    @EJB
    PersistenciaVigenciasCompensacionesInterface persistenciaVigenciasCompensaciones;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    // VigenciasJornadas
    List<VigenciasJornadas> listVigenciasJornadas;
    VigenciasJornadas vigenciaJornada;
    // VigenciasCompensaciones
    List<VigenciasCompensaciones> listVigenciasCompensaciones;
    VigenciasCompensaciones vigenciaCompensacion;
    //JornadasLaborales
    List<JornadasLaborales> listJornadasLaborales;
    //TiposDescansos
    List<TiposDescansos> listTiposDescansos;
    //Empleados
    Empleados empleado;

    @Override
    public List<VigenciasJornadas> VigenciasJornadasEmpleado(BigInteger secEmpleado) {
        try {
            listVigenciasJornadas = persistenciaVigenciasJornadas.buscarVigenciasJornadasEmpleado(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Jornadas (VigenciasJornadasEmpleado)");
            listVigenciasJornadas = null;
        }
        return listVigenciasJornadas;
    }

    @Override
    public void modificarVJ(List<VigenciasJornadas> listVJModificadas) {
        try {
            for (int i = 0; i < listVJModificadas.size(); i++) {
                System.out.println("Modificando ...");
                if (listVJModificadas.get(i).getJornadatrabajo().getSecuencia() == null) {
                    listVJModificadas.get(i).setJornadatrabajo(null);
                }
                if (listVJModificadas.get(i).getTipodescanso().getSecuencia() == null) {
                    listVJModificadas.get(i).setTipodescanso(null);
                }
                vigenciaJornada = listVJModificadas.get(i);
                persistenciaVigenciasJornadas.editar(vigenciaJornada);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVJ AdmiVigJor");
        }
    }

    @Override
    public void borrarVJ(VigenciasJornadas vigenciasJornadas) {
        try {
            persistenciaVigenciasJornadas.borrar(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("Error borrarVJ AdmiVigJor");
        }
    }

    @Override
    public void crearVJ(VigenciasJornadas vigenciasJornadas) {
        try {
            persistenciaVigenciasJornadas.crear(vigenciasJornadas);
        } catch (Exception e) {
            System.out.println("Error crearVJ AdmiVigJor");
        }
    }

    @Override
    public List<VigenciasCompensaciones> VigenciasCompensacionesSecVigenciaTipoComp(String tipoC, BigInteger secVigencia) {
        try {
            listVigenciasCompensaciones = persistenciaVigenciasCompensaciones.buscarVigenciasCompensacionesVigenciayCompensacion(tipoC, secVigencia);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Jornadas (VigenciasCompensacionesSecVigenciaTipoComp)");
            listVigenciasCompensaciones = null;
        }
        return listVigenciasCompensaciones;
    }

    @Override
    public List<VigenciasCompensaciones> VigenciasCompensacionesSecVigencia(BigInteger secVigencia) {
        try {
            listVigenciasCompensaciones = persistenciaVigenciasCompensaciones.buscarVigenciasCompensacionesVigenciaSecuencia(secVigencia);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Jornadas (VigenciasCompensacionesSecVigenciaTipoComp)");
            listVigenciasCompensaciones = null;
        }
        return listVigenciasCompensaciones;
    }

    @Override
    public void modificarVC(List<VigenciasCompensaciones> listVCModificadas) {
        try {
            for (int i = 0; i < listVCModificadas.size(); i++) {
                System.out.println("Modificando Vigencias Prorrateo...");
                vigenciaCompensacion = listVCModificadas.get(i);
                persistenciaVigenciasCompensaciones.editar(vigenciaCompensacion);
            }
        } catch (Exception e) {
            System.out.println("Error modificarVC AdmiVigJor");
        }
    }

    @Override
    public void borrarVC(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            persistenciaVigenciasCompensaciones.borrar(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("Error borrarVC AdmiVigJor");
        }
    }

    @Override
    public void crearVC(VigenciasCompensaciones vigenciasCompensaciones) {
        try {
            persistenciaVigenciasCompensaciones.crear(vigenciasCompensaciones);
        } catch (Exception e) {
            System.out.println("Error crearVC AdmiVigJor");
        }
    }

    @Override
    public List<TiposDescansos> tiposDescansos() {
        try {
            listTiposDescansos = persistenciaTiposDescansos.consultarTiposDescansos();
            return listTiposDescansos;
        } catch (Exception e) {
            System.out.println("Error tiposDescansos Admi");
            return null;
        }
    }

    @Override
    public List<JornadasLaborales> jornadasLaborales() {
        try {
            listJornadasLaborales = persistenciaJornadasLaborales.buscarJornadasLaborales();
            return listJornadasLaborales;
        } catch (Exception e) {
            System.out.println("Error jornadasLaborales Admi");
            return null;
        }
    }

    @Remove
    @Override
    public void salir() {
        listVigenciasJornadas = null;
        listVigenciasCompensaciones = null;
    }

    @Override
    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            System.out.println("Empleado : " + empleado.getPersona().getNombre());
            return empleado;
        } catch (Exception e) {
            System.out.println("Error buscarEmpleado Adm");
            empleado = null;
            return empleado;
        }
    }
}
