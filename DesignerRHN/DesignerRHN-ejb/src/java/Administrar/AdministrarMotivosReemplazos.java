/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.MotivosReemplazos;
import InterfaceAdministrar.AdministrarMotivosReemplazosInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaMotivosReemplazosInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarMotivosReemplazos implements AdministrarMotivosReemplazosInterface{
    
    @EJB
    PersistenciaMotivosReemplazosInterface persistenciaMotivosReemplazos;
    
        /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public void obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
    }
    
    @Override
    public List<MotivosReemplazos> MotivosReemplazos() {
        List<MotivosReemplazos> listaMotivosReemplazos;
        listaMotivosReemplazos = persistenciaMotivosReemplazos.motivosReemplazos(em);
        return listaMotivosReemplazos;
    }

    @Override
    public List<MotivosReemplazos> lovTiposReemplazos() {
        return persistenciaMotivosReemplazos.motivosReemplazos(em);
    }



}
