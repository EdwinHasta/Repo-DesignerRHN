/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Proyectos;
import InterfaceAdministrar.AdministrarProyectosInterface;
import InterfacePersistencia.PersistenciaProyectosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

/**
 *
 * @author Viktor
 */
@Stateful
public class AdministrarProyectos implements AdministrarProyectosInterface{
    
      @EJB
    PersistenciaProyectosInterface persistenciaProyectos;
    
    @Override
    public List<Proyectos> Proyectos(){
        List<Proyectos> listaProyectos;
        listaProyectos = persistenciaProyectos.proyectos();
        return listaProyectos;
    }

    @Override
    public List<Proyectos>  lovProyectos(){
        return persistenciaProyectos.proyectos();
    }


}

