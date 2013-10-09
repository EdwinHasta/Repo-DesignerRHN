/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cargos;
import Entidades.Empleados;
import Entidades.Personas;
import Entidades.Proyectos;
import Entidades.PryRoles;
import Entidades.VigenciasProyectos;
import InterfaceAdministrar.AdministrarVigenciasProyectosInterface;
import InterfacePersistencia.PersistenciaCargosInterface;
import InterfacePersistencia.PersistenciaEmpleadoInterface;
import InterfacePersistencia.PersistenciaPersonasInterface;
import InterfacePersistencia.PersistenciaProyectosInterface;
import InterfacePersistencia.PersistenciaPryRolesInterface;
import InterfacePersistencia.PersistenciaVigenciasProyectosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarVigenciasProyectos implements AdministrarVigenciasProyectosInterface{

    @EJB
    PersistenciaVigenciasProyectosInterface persistenciaVigenciasProyectos;
    @EJB
    PersistenciaPersonasInterface persistenciaPersonas;
    @EJB
    PersistenciaProyectosInterface persistenciaProyectos;
    @EJB
    PersistenciaPryRolesInterface persistenciaPryRoles;
    @EJB
    PersistenciaCargosInterface persistenciaCargos;
    @EJB
    PersistenciaEmpleadoInterface persistenciaEmpleados;
    
    private VigenciasProyectos vP;
    
    @Override
    public List<VigenciasProyectos> vigenciasProyectosEmpleado(BigInteger secEmpleado) {
        try {
            return persistenciaVigenciasProyectos.vigenciasProyectosEmpleado(secEmpleado);
        } catch (Exception e) {
            System.err.println("Error AdministrarVigenciasProyectos.vigenciasProyectosPersona " + e);
            return null;
        }
    }

    @Override
    public Empleados encontrarEmpleado(BigInteger secEmpleado) {
        return persistenciaEmpleados.buscarEmpleado(secEmpleado);
    }

    //Listas de Valores Educacion, Profesion, Instituciones, Adiestramiento
    @Override
    public List<Proyectos> lovProyectos() {
        return persistenciaProyectos.proyectos();
    }

    @Override
    public List<PryRoles> lovPryRoles() {
        return persistenciaPryRoles.pryroles();
    }

    @Override
    public List<Cargos> lovCargos() {
        return persistenciaCargos.cargos();
    }
       
    @Override
    public void modificarVigenciaProyecto(List<VigenciasProyectos> listaVigenciasProyectosModificar) {
        for (int i = 0; i < listaVigenciasProyectosModificar.size(); i++) {
            System.out.println("Modificando...");
            if (listaVigenciasProyectosModificar.get(i).getProyecto().getSecuencia() == null) {
                listaVigenciasProyectosModificar.get(i).setProyecto(null);
                vP = listaVigenciasProyectosModificar.get(i);
            } else {
                vP = listaVigenciasProyectosModificar.get(i);
            }
            if (listaVigenciasProyectosModificar.get(i).getPryRol().getSecuencia() == null) {
                listaVigenciasProyectosModificar.get(i).setPryRol(null);
                vP = listaVigenciasProyectosModificar.get(i);
            } else {
                vP = listaVigenciasProyectosModificar.get(i);
            }
            if (listaVigenciasProyectosModificar.get(i).getPryCargoproyecto().getSecuencia() == null) {
                listaVigenciasProyectosModificar.get(i).setPryCargoproyecto(null);
                vP = listaVigenciasProyectosModificar.get(i);
            } else {
                vP = listaVigenciasProyectosModificar.get(i);
            }
                       
            
            persistenciaVigenciasProyectos.editar(vP);
        }
    }

    @Override
    public void borrarVigenciaProyecto(VigenciasProyectos vigenciasProyectos) {
        persistenciaVigenciasProyectos.borrar(vigenciasProyectos);
    }


    @Override
    public void crearVigenciaProyecto(VigenciasProyectos vigenciasProyectos) {
        persistenciaVigenciasProyectos.crear(vigenciasProyectos);
    }
    
    @Override
    public Proyectos buscarProyectoPorNombreVigencia(String nombre){
        try{
            Proyectos proyecto = persistenciaProyectos.buscarProyectoNombre(nombre);
            return proyecto;
        } catch(Exception e){
            return null;
        }
    }
}
