/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Inforeportes;
import InterfacePersistencia.PersistenciaInforeportesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'Inforeportes'
 * de la base de datos.
 * (las busquedas se realizan sobre la tabla 'UsuariosInforeportes')
 * @author AndresPineda
 */
@Stateless
public class PersistenciaInforeportes implements PersistenciaInforeportesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("El Inforeportes no exite o esta reservada por lo cual no puede ser modificada (Inforeportes)");
        }
    }

    @Override
    public void editar(Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Inforeportes");
        }
    }

    @Override
    public void borrar(Inforeportes inforeportes) {
        try {
            em.remove(em.merge(inforeportes));
        } catch (Exception e) {
            System.out.println("El Inforeportes no se ha podido eliminar");
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportes() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Inforeportes.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("Error buscarInforeportes");
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioNomina() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'NOM' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioLaboral() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'LBL' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioBanco() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'BAN' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioContabilidad() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'CON' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioPersonal() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'PER' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }
    
    @Override
     public List<Inforeportes> buscarInforeportesUsuarioBienestar() {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'BIN' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }
}
