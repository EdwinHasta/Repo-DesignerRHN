package Administrar;

import Entidades.ActualUsuario;
import Entidades.DetallesEmpresas;
import Entidades.Empresas;
import Entidades.Generales;
import InterfaceAdministrar.AdministrarSesionesInterface;
import InterfaceAdministrar.AdministrarTemplateInterface;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import InterfacePersistencia.PersistenciaDetallesEmpresasInterface;
import InterfacePersistencia.PersistenciaEmpresasInterface;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import javax.ejb.EJB;
import javax.ejb.Stateful;
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
    /**
     * Enterprise JavaBean.<br>
     * Representa la interfaz para acceder a las rutas de generales.
     */
    @EJB
    PersistenciaGeneralesInterface persistenciaGenerales;
    @EJB
    PersistenciaEmpresasInterface persistenciaEmpresas;
    @EJB
    PersistenciaDetallesEmpresasInterface persistenciaDetallesEmpresas;

    private EntityManager em;
    private Generales general;

    @Override
    public boolean obtenerConexion(String idSesion) {
        em = administrarSesiones.obtenerConexionSesion(idSesion);
        return em != null;
    }

    @Override
    public ActualUsuario consultarActualUsuario() {
        return persistenciaActualUsuario.actualUsuarioBD(em);
    }

    @Override
    public String logoEmpresa() {
        String rutaLogo;
        general = persistenciaGenerales.obtenerRutas(em);
        if (general != null) {
            Empresas empresa = persistenciaEmpresas.consultarPrimeraEmpresaSinPaquete(em);
            if (empresa != null) {
                rutaLogo = general.getPathfoto() + empresa.getNit() + ".png";
            } else {
                rutaLogo = general.getPathfoto() + "sinlogo.jpg";
            }
        } else {
            return null;
        }
        return rutaLogo;
    }

    @Override
    public String rutaFotoUsuario() {
        String rutaFoto;
        general = persistenciaGenerales.obtenerRutas(em);
        if (general != null) {
            rutaFoto = general.getPathfoto();
        } else {
            return null;
        }
        return rutaFoto;
    }

    
    @Override
    public void cerrarSession(String idSesion) {
        if (em.isOpen()) {
            em.getEntityManagerFactory().close();
            administrarSesiones.borrarSesion(idSesion);
        }
    }
    @Override
    public DetallesEmpresas consultarDetalleEmpresaUsuario() {
        System.out.println("AdministrarTemplate.consultarDetalleEmpresaUsuario");
        try {
            Short codigoEmpresa = persistenciaEmpresas.codigoEmpresa(em);
            DetallesEmpresas detallesEmpresas = persistenciaDetallesEmpresas.buscarDetalleEmpresa(em, codigoEmpresa);
            return detallesEmpresas;
        } catch (Exception e) {
            return null;
        }
    }
}
