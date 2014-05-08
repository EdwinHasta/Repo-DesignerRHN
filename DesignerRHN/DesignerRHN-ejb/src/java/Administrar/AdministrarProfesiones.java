/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Administrar;

import Entidades.Profesiones;
import InterfaceAdministrar.AdministrarProfesionesInterface;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfacePersistencia.PersistenciaProfesionesInterface;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;

@Stateful
public class AdministrarProfesiones implements AdministrarProfesionesInterface {

    @EJB
    PersistenciaProfesionesInterface persistenciaProfesiones;
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
    public List<Profesiones> Profesiones() {
        List<Profesiones> listaProfesiones;
        listaProfesiones = persistenciaProfesiones.profesiones(em);
        return listaProfesiones;
    }

    @Override
    public List<Profesiones> lovProfesiones() {
        return persistenciaProfesiones.profesiones(em);
    }
}
