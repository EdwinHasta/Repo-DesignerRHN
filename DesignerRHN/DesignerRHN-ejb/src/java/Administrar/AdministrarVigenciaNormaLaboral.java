/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Empleados;
import Entidades.NormasLaborales;
import Entidades.VigenciasNormasEmpleados;
import InterfaceAdministrar.AdministrarVigenciaNormaLaboralInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaNormasLaboralesInterface;
import InterfacePersistencia.PersistenciaVigenciasNormasEmpleadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.ejb.LocalBean;

/**
 *
 * @author John Pineda
 */
@Stateful
@LocalBean
public class AdministrarVigenciaNormaLaboral implements AdministrarVigenciaNormaLaboralInterface {

    /**
     * CREACION DE LOS EJB
     */
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleado;
    @EJB
    PersistenciaNormasLaboralesInterface persistenciaNormasLaborales;
    @EJB
    PersistenciaVigenciasNormasEmpleadosInterface persistenciaVigenciasNormasEmpleados;
    /**
     * Creacion de atributos
     */
    List<VigenciasNormasEmpleados> vigenciasNormasEmpleados; //esta lista es la que se mostrara en la tabla de vigencias
    VigenciasNormasEmpleados vne;
    Empleados empleado;
    List<NormasLaborales> normasLaborales;

    /**
     * Creacion de metodos
     */
    public List<VigenciasNormasEmpleados> vigenciasNormasEmpleadosEmpl(BigInteger secEmpleado) {
        try {
            vigenciasNormasEmpleados = persistenciaVigenciasNormasEmpleados.buscarVigenciasNormasEmpleadosEmpl(secEmpleado);
        } catch (Exception e) {
            System.out.println("Error en Administrar Vigencias Ubiaciones (vigenciasUbicacionesEmpleado)");
            vigenciasNormasEmpleados = null;
        }
        return vigenciasNormasEmpleados;
    }

    public void modificarVNE(List<VigenciasNormasEmpleados> listVNEModificadas) {
        for (int i = 0; i < listVNEModificadas.size(); i++) {
            System.out.println("Modificando...");
            vne = listVNEModificadas.get(i);
            persistenciaVigenciasNormasEmpleados.editar(vne);
        }
    }

    public void borrarVNE(VigenciasNormasEmpleados vigenciasNormasEMpleados) {
        persistenciaVigenciasNormasEmpleados.borrar(vigenciasNormasEMpleados);
    }

    public void crearVNE(VigenciasNormasEmpleados vigenciasNormasEmpleados) {
        persistenciaVigenciasNormasEmpleados.crear(vigenciasNormasEmpleados);
    }

    public Empleados buscarEmpleado(BigInteger secuencia) {
        try {
            empleado = persistenciaEmpleado.buscarEmpleadoSecuencia(secuencia);
            return empleado;
        } catch (Exception e) {
            empleado = null;
            return empleado;
        }
    }

    public List<NormasLaborales> normasLaborales() {
        try {
            normasLaborales = persistenciaNormasLaborales.buscarNormasLaborales();
            return normasLaborales;
        } catch (Exception e) {
            System.err.println("ERROR EN AdministrarVigencianormaLaboral en NormasLabolares ");
            return null;
        }
    }
}
