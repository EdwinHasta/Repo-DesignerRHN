/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Encargaturas;
import Entidades.Estructuras;
import Entidades.MotivosReemplazos;
import Entidades.TiposReemplazos;
import InterfaceAdministrar.AdministrarNovedadesReemplazosInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaEncargaturasInterface;
import InterfacePersistencia.PersistenciaEstructurasInterface;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import InterfacePersistencia.PersistenciaTiposReemplazosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author user
 */
@Stateful
public class AdministrarNovedadesReemplazos implements AdministrarNovedadesReemplazosInterface {

    @EJB
    PersistenciaEncargaturasInterface persistenciaEncargaturas;
    @EJB
    PersistenciaMotivosReemplazosInterface persistenciaMotivosReemplazos;
    @EJB
    PersistenciaTiposReemplazosInterface persistenciaTiposReemplazos;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    @EJB
    PersistenciaEstructurasInterface persistenciaEstructuras;

    //Trae las encargaturas del empleado cuya secuencia se envía como parametro//
    @Override
    public List<Encargaturas> encargaturasEmpleado(BigInteger secEmpleado) {
        try {
            return persistenciaEncargaturas.encargaturasEmpleado(secEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarNovedadesReemplazos.encargaturasEmpleado" + e);
            return null;
        }
    }

    @Override
    public Empleados encontrarEmpleado(BigInteger secEmpleado) {
        return persistenciaEmpleados.buscarEmpleado(secEmpleado);
    }

    //Listas de Tipos Reemplazos, Motivos Reemplazos, Estructuras, Cargos
    @Override
    public List<Empleados> lovEmpleados() {
        return persistenciaEmpleados.buscarEmpleados();
    }

    @Override
    public List<TiposReemplazos> lovTiposReemplazos() {
        return persistenciaTiposReemplazos.tiposReemplazos();
    }

    @Override
    public List<MotivosReemplazos> lovMotivosReemplazos() {
        return persistenciaMotivosReemplazos.motivosReemplazos();
    }

    @Override
    public List<Estructuras> lovEstructuras() {
        return persistenciaEstructuras.estructuras();
    }

    public List<Cargos> lovCargos() {
        return persistenciaCargos.cargosSalario();
    }

    /*Toca Arreglarlo con el Native Query
     @Override
     public List<Cargos> lovCargos() {
     return persistenciaCargos.cargos();
     }*/
    @Override
    public void modificarEncargatura(List<Encargaturas> listaEncargaturasModificar) {
        for (int i = 0; i < listaEncargaturasModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaEncargaturasModificar.get(i).getCargo().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setCargo(null);
            }
            if (listaEncargaturasModificar.get(i).getMotivoreemplazo().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setMotivoreemplazo(null);
            }
            if (listaEncargaturasModificar.get(i).getReemplazado().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setReemplazado(null);
            }
            if (listaEncargaturasModificar.get(i).getEstructura().getSecuencia() == null) {
                listaEncargaturasModificar.get(i).setEstructura(null);
            }
            persistenciaEncargaturas.editar(listaEncargaturasModificar.get(i));
        }
    }

    @Override
    public void borrarEncargaturas(Encargaturas encargaturas) {
        persistenciaEncargaturas.borrar(encargaturas);
    }

    @Override
    public void crearEncargaturas(Encargaturas encargaturas) {
        persistenciaEncargaturas.crear(encargaturas);
    }
}
