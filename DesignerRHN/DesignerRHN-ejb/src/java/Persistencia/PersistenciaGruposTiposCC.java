/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import InterfacePersistencia.PersistenciaGruposTiposCCInterface;
import Entidades.GruposTiposCC;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'GruposTiposCC'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaGruposTiposCC implements PersistenciaGruposTiposCCInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;*/

    @Override
    public void crear(EntityManager em,GruposTiposCC gruposTiposCC) {
        try {
            em.persist(gruposTiposCC);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos crear ERROR " + e);
        }
    }

    @Override
    public void editar(EntityManager em,GruposTiposCC gruposTiposCC) {
        try {
            em.merge(gruposTiposCC);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos editar ERROR " + e);
        }
    }

    @Override
    public void borrar(EntityManager em,GruposTiposCC gruposTiposCC) {
        try {
            em.remove(em.merge(gruposTiposCC));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos borrar ERROR " + e);
        }
    }

    @Override
    public GruposTiposCC buscarGruposTiposCCPorSecuencia(EntityManager em,BigInteger secuencia) {
        try {
            return em.find(GruposTiposCC.class, secuencia);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaGruposTiposCC buscarGruposTiposCC ERROR " + e);
            return null;
        }
    }

    @Override
    public List<GruposTiposCC> buscarGruposTiposCC(EntityManager em) {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(GruposTiposCC.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposCC buscarGruposTiposCC ERROR" + e);
            return null;
        }
    }

}
