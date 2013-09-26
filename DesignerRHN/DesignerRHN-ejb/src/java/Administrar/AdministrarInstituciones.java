/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Instituciones;
import InterfaceAdministrar.AdministrarInstitucionesInterface;
import InterfacePersistencia.PersistenciaInstitucionesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarInstituciones implements AdministrarInstitucionesInterface{

    @EJB
    PersistenciaInstitucionesInterface persistenciaInstituciones;
    
    @Override
    public List<Instituciones> Instituciones(){
        List<Instituciones> listaInstituciones;
        listaInstituciones = persistenciaInstituciones.instituciones();
        return listaInstituciones;
    }

    @Override
    public List<Instituciones>  lovInstituciones(){
        return persistenciaInstituciones.instituciones();
    }
}