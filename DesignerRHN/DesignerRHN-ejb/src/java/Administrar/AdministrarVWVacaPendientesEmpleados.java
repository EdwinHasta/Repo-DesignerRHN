package Administrar;

import Entidades.Empleados;
import Entidades.VWVacaPendientesEmpleados;
import InterfaceAdministrar.AdministrarVWVacaPendientesEmpleadosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaSolucionesNodosInterface;
import InterfacePersistencia.PersistenciaVWVacaPendientesEmpleadosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class AdministrarVWVacaPendientesEmpleados implements AdministrarVWVacaPendientesEmpleadosInterface {

    @EJB
    PersistenciaVWVacaPendientesEmpleadosInterface persistenciaVWVacaPendientesEmpleados;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaSolucionesNodosInterface persistenciaSolucionesNodos;
    Empleados empleado;
    List<VWVacaPendientesEmpleados> vacaciones;
    BigDecimal unidades;
    //

    @Override
    public void crearVacaPendiente(VWVacaPendientesEmpleados vaca) {
        try {
            persistenciaVWVacaPendientesEmpleados.crear(vaca);
        } catch (Exception e) {
            System.out.println("Error en crearVacaPeniente Admi : " + e.toString());
        }
    }

    @Override
    public void editarVacaPendiente(VWVacaPendientesEmpleados vaca) {
        try {
            persistenciaVWVacaPendientesEmpleados.editar(vaca);
        } catch (Exception e) {
            System.out.println("Error en editarVacaPendiente Admi : " + e.toString());
        }
    }

    @Override
    public void borrarVacaPendiente(VWVacaPendientesEmpleados vaca) {
        try {
            persistenciaVWVacaPendientesEmpleados.borrar(vaca);
        } catch (Exception e) {
            System.out.println("Error en borrarVacaPendiente Admi : " + e.toString());
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaPendientesPendientes(Empleados empl) {
        try {
            vacaciones = persistenciaVWVacaPendientesEmpleados.vacaEmpleadoPendientes(empl.getSecuencia());
            return vacaciones;
        } catch (Exception e) {
            System.out.println("Error en vacaPendientesMayorCero Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public List<VWVacaPendientesEmpleados> vacaPendientesDisfrutadas(Empleados empl) {
        try {
            vacaciones = persistenciaVWVacaPendientesEmpleados.vacaEmpleadoDisfrutadas(empl.getSecuencia());
            return vacaciones;
        } catch (Exception e) {
            System.out.println("Error en vacaPendientesIgualCero Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public Empleados obtenerEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleado(secuencia);
            return empleado;
        } catch (Exception e) {
            System.out.println("Error en obtener empleado Admi : " + e.toString());
            return null;
        }
    }

    @Override
    public BigDecimal diasProvisionadosEmpleado(Empleados empl) {
        try {
            unidades = persistenciaSolucionesNodos.diasProvisionados(empl.getSecuencia());
            return unidades;
        } catch (Exception e) {
            System.out.println("Error en diasProvisionadosEmpleado Admi : " + e.toString());
            return null;
        }
    }
}
