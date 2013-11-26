/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.GruposSalariales;
import InterfacePersistencia.PersistenciaGruposSalarialesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaGruposSalariales implements PersistenciaGruposSalarialesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Proyectos
     */
    @Override
    public void crear(GruposSalariales gruposSalariales) {
        try {
            em.persist(gruposSalariales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaGruposSalariales : " + e.toString());
        }
    }

    /*
     *Editar Proyectos
     */
    @Override
    public void editar(GruposSalariales gruposSalariales) {
        try {
            em.merge(gruposSalariales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaGruposSalariales : " + e.toString());
        }
    }

    /*
     *Borrar Proyectos
     */
    @Override
    public void borrar(GruposSalariales gruposSalariales) {
        try {
            em.remove(em.merge(gruposSalariales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaGruposSalariales : " + e.toString());
        }
    }

    /*
     *Encontrar un Proyecto
     */
    @Override
    public GruposSalariales buscarGrupoSalarial(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(GruposSalariales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarGrupoSalarial PersistenciaGruposSalariales : " + e.toString());
            return null;
        }

    }

    /*
     *Encontrar todas los proyectos
     */
    @Override
    public List<GruposSalariales> buscarGruposSalariales() {
        try {
            Query query = em.createQuery("SELECT gs FROM GruposSalariales gs ORDER BY gs.secuencia");
            List<GruposSalariales> gruposSalariales = query.getResultList();
            return gruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarGruposSalariales PersistenciaGruposSalariales : " + e.toString());
            return null;
        }
    }

    @Override
    public GruposSalariales buscarGrupoSalarialSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT gs FROM GruposSalariales gs WHERE gs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            GruposSalariales gruposSalariales = (GruposSalariales) query.getSingleResult();
            return gruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarGrupoSalarialSecuencia PersistenciaGruposSalariales : " + e.toString());
            GruposSalariales gruposSalariales = null;
            return gruposSalariales;
        }

    }
}
