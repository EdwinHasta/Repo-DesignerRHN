/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Cursos;
import InterfaceAdministrar.AdministrarCursosInterface;
import InterfacePersistencia.PersistenciaCursosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarCursos implements AdministrarCursosInterface {

    @EJB
    PersistenciaCursosInterface persistenciaCursos;
    
    @Override
    public List<Cursos> Cursos(){
        List<Cursos> listaCursos;
        listaCursos = persistenciaCursos.cursos();
        return listaCursos;
    }

    @Override
    public List<Cursos>  lovCursos(){
        return persistenciaCursos.cursos();
    }
}


