package Administrar;

import Entidades.ActualUsuario;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfaceAdministrar.AdministrarTemplateInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import java.io.IOException;
import javax.ejb.EJB;
import javax.ejb.Stateful;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;

/**
 *
 * @author -Felipphe-
 */
@Stateful
public class AdministrarTemplate implements AdministrarTemplateInterface {

    /**
     * Enterprise JavaBeans.<br>
     * Atributo que representa la comunicación con la persistencia
     * 'persistenciaActualUsuario'.
     */
    @EJB
    PersistenciaActualUsuarioInterface persistenciaActualUsuario;
    /**
     * Enterprise JavaBean.<br>
     * Atributo que representa todo lo referente a la conexión del usuario que
     * está usando el aplicativo.
     */
    @EJB
    AdministrarSesionesInterface administrarSesiones;

    private EntityManager em;

    @Override
    public boolean obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
        return em != null;
    }

    @Override
    public ActualUsuario consultarActualUsuario() {
        return persistenciaActualUsuario.actualUsuarioBD(em);
    }
    
    public void cerrarSession(String idSesion) {
        if (em.isOpen()) {
            em.getEntityManagerFactory().close();
            administrarSesiones.borrarSesion(idSesion);
        }
    }
}
