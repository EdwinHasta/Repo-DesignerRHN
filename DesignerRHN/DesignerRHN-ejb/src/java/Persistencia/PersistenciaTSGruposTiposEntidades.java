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
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.persist(tSGruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public void editar(TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.merge(tSGruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public void borrar(TSGruposTiposEntidades tSGruposTiposEntidades) {
        try {
            em.remove(em.merge(tSGruposTiposEntidades));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTSGruposTiposEntidades : " + e.toString());
        }
    }

    @Override
    public List<TSGruposTiposEntidades> buscarTSGruposTiposEntidades() {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t ORDER BY t.secuencia ASC");
            List<TSGruposTiposEntidades> tSGruposTiposEntidades = (List<TSGruposTiposEntidades>) query.getResultList();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGruposTiposEntidades PersistenciaTSGruposTiposEntidades : " + e.toString());
            return null;
        }
    }

    @Override
    public TSGruposTiposEntidades buscarTSGrupoTipoEntidadSecuencia(BigInteger secTSGrupo) {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t WHERE t.secuencia = :secTSGrupo");
            query.setParameter("secTSGrupo", secTSGrupo);
            TSGruposTiposEntidades tSGruposTiposEntidades = (TSGruposTiposEntidades) query.getSingleResult();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGrupoTipoEntidadSecuencia PersistenciaTSGruposTiposEntidades : " + e.toString());
            TSGruposTiposEntidades tSGruposTiposEntidades = null;
            return tSGruposTiposEntidades;
        }
    }

    @Override
    public List<TSGruposTiposEntidades> buscarTSGruposTiposEntidadesPorSecuenciaTipoSueldo(BigInteger secTipoSueldo) {
        try {
            Query query = em.createQuery("SELECT t FROM TSGruposTiposEntidades t WHERE t.tiposueldo.secuencia =:secTipoSueldo");
            query.setParameter("secTipoSueldo", secTipoSueldo);
            List<TSGruposTiposEntidades> tSGruposTiposEntidades = (List<TSGruposTiposEntidades>) query.getResultList();
            return tSGruposTiposEntidades;
        } catch (Exception e) {
            System.out.println("Error buscarTSGruposTiposEntidadesPorSecuenciaTipoSueldo PersistenciaTSGruposTiposEntidades : " + e.toString());
            return null;
        }
    }

}
