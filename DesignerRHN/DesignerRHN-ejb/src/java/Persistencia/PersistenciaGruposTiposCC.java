/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
 *
 * @author user
 */
@Stateless
public class PersistenciaGruposTiposCC implements PersistenciaGruposTiposCCInterface {
   @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;

    @Override
    public void crear(GruposTiposCC gruposTiposCC) {
        try {
            em.persist(gruposTiposCC);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos crear ERROR " + e);
        }
    }

    @Override
    public void editar(GruposTiposCC gruposTiposCC) {
        try {
            em.merge(gruposTiposCC);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos editar ERROR " + e);
        }
    }

    @Override
    public void borrar(GruposTiposCC gruposTiposCC) {
        try {
            em.remove(em.merge(gruposTiposCC));
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaTiposCentrosCostos borrar ERROR " + e);
        }
    }

    @Override
    public GruposTiposCC buscarGruposTiposCCPorSecuencia(BigInteger secuenciaTiposCentrosCostos) {
        try {
            return em.find(GruposTiposCC.class, secuenciaTiposCentrosCostos);
        } catch (Exception e) {
            System.err.println("ERROR PersistenciaGruposTiposCC buscarGruposTiposCC ERROR " + e);
            return null;
        }
    }

    @Override
    public List<GruposTiposCC> buscarGruposTiposCC() {
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
