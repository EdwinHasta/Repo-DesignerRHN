/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.TSGruposTiposEntidades;
import InterfacePersistencia.PersistenciaTSGruposTiposEntidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'TSGruposTiposEntidades' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTSGruposTiposEntidades implements PersistenciaTSGruposTiposEntidadesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.persist(tSGruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public void editar(EntityManager em, TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.merge(tSGruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public void borrar(EntityManager em, TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.remove(em.merge(tSGruposTiposEntidades));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public List<TSGruposTiposEntidades> buscarTSGruposTiposEntidades(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t ORDER BY t.secuencia ASC");
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TSGruposTiposEntidades> tSGruposTiposEntidades = (List<TSGruposTiposEntidades>) query.getResultList();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGruposTiposEntidades PersistenciaTSGruposTiposEntidades : " + e.toString());
            return null;
        }
    }

    @Override
    public TSGruposTiposEntidades buscarTSGrupoTipoEntidadSecuencia(EntityManager em, BigInteger secTSGrupo) {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t WHERE t.secuencia = :secTSGrupo");
            query.setParameter("secTSGrupo", secTSGrupo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TSGruposTiposEntidades tSGruposTiposEntidades = (TSGruposTiposEntidades) query.getSingleResult();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGrupoTipoEntidadSecuencia PersistenciaTSGruposTiposEntidades : " + e.toString());
            TSGruposTiposEntidades tSGruposTiposEntidades = null;
            return tSGruposTiposEntidades;
        }
    }

    @Override
    public List<TSGruposTiposEntidades> buscarTSGruposTiposEntidadesPorSecuenciaTipoSueldo(EntityManager em, BigInteger secTipoSueldo) {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t WHERE t.tiposueldo.secuencia =:secTipoSueldo");
            query.setParameter("secTipoSueldo", secTipoSueldo);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<TSGruposTiposEntidades> tSGruposTiposEntidades = (List<TSGruposTiposEntidades>) query.getResultList();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGruposTiposEntidadesPorSecuenciaTipoSueldo PersistenciaTSGruposTiposEntidades : " + e.toString());
            return null;
        }
    }

}
