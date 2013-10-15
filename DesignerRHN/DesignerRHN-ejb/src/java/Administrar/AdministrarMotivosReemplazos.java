/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosReemplazos;
import InterfaceAdministrar.AdministrarMotivosReemplazosInterface;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;

@Stateful
public class AdministrarMotivosReemplazos implements AdministrarMotivosReemplazosInterface{
    
    @EJB
    PersistenciaMotivosReemplazosInterface persistenciaMotivosReemplazos;
    
    @Override
    public List<MotivosReemplazos> MotivosReemplazos() {
        List<MotivosReemplazos> listaMotivosReemplazos;
        listaMotivosReemplazos = persistenciaMotivosReemplazos.motivosReemplazos();
        return listaMotivosReemplazos;
    }

    @Override
    public List<MotivosReemplazos> lovTiposReemplazos() {
        return persistenciaMotivosReemplazos.motivosReemplazos();
    }



}
