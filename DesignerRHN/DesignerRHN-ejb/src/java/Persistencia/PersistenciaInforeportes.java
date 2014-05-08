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
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;

    @Override
    public void crear(EntityManager em, Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("El Inforeportes no exite o esta reservada por lo cual no puede ser modificada (Inforeportes)");
        }
    }

    @Override
    public void editar(EntityManager em, Inforeportes inforeportes) {
        try {
            em.merge(inforeportes);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el Inforeportes");
        }
    }

    @Override
    public void borrar(EntityManager em, Inforeportes inforeportes) {
        try {
            em.remove(em.merge(inforeportes));
        } catch (Exception e) {
            System.out.println("El Inforeportes no se ha podido eliminar");
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportes(EntityManager em) {
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
    public List<Inforeportes> buscarInforeportesUsuarioNomina(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'NOM' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioLaboral(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'LBL' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioBanco(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'BAN' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioContabilidad(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'CON' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }

    @Override
    public List<Inforeportes> buscarInforeportesUsuarioPersonal(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'PER' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }
    
    @Override
     public List<Inforeportes> buscarInforeportesUsuarioBienestar(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT ui.inforeporte FROM UsuariosInforeportes ui WHERE ui.inforeporte.modulo.nombrecorto = 'BIN' AND ui.usuario.alias = (SELECT a.alias FROM ActualUsuario a) ORDER BY ui.inforeporte.codigo DESC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Inforeportes> inforeportes = (List<Inforeportes>) query.getResultList();
            System.out.println("Size : " + inforeportes.size());
            return inforeportes;
        } catch (Exception e) {
            System.out.println("Error en buscarInforeportesUsuario " + e);
            return null;
        }
    }
}
