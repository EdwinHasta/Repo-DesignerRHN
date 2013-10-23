/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import InterfacePersistencia.PersistenciaGruposTiposEntidadesInterface;
import Entidades.Grupostiposentidades;
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
public class PersistenciaGruposTiposEntidades implements PersistenciaGruposTiposEntidadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    EntityManager em;

    @Override
    public void crear(Grupostiposentidades gruposTiposEntidades) {
        try {
            em.persist(gruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades crear ERROR : " + e);
        }
    }

    @Override
    public void editar(Grupostiposentidades gruposTiposEntidades) {
        try {
            em.merge(gruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades editar error " + e);
        }
    }

    @Override
    public void borrar(Grupostiposentidades gruposTiposEntidades) {
        try {
            em.remove(gruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades borrar ERROR +" + e);
        }
    }

    @Override
    public Grupostiposentidades buscarGrupoTipoEntidad(BigInteger secuenciaGruposTiposEntidades) {
        try {

            return em.find(Grupostiposentidades.class, secuenciaGruposTiposEntidades);
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades buscarGrupoTipoEntidad ERROR " + e);

            return null;
        }
    }

    @Override
    public List<Grupostiposentidades> buscarGruposTiposEntidades() {
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Grupostiposentidades.class));
            return em.createQuery(cq).getResultList();
        } catch (Exception e) {
            System.out.println("\n ERROR EN PersistenciaGruposTiposEntidades buscarGruposTiposEntidades" + e);
            return null;
        }
    }
}
