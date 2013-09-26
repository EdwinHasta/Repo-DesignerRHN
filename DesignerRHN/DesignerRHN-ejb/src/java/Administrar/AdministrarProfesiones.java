/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Profesiones;
import InterfaceAdministrar.AdministrarProfesionesInterface;
import InterfacePersistencia.PersistenciaProfesionesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarProfesiones implements AdministrarProfesionesInterface {

  @EJB
    PersistenciaProfesionesInterface persistenciaProfesiones;
    
    @Override
    public List<Profesiones> Profesiones(){
        List<Profesiones> listaProfesiones;
        listaProfesiones = persistenciaProfesiones.profesiones();
        return listaProfesiones;
    }

    @Override
    public List<Profesiones>  lovProfesiones(){
        return persistenciaProfesiones.profesiones();
    }
}