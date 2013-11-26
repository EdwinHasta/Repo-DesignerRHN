/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.VigenciasGruposSalariales;
import InterfacePersistencia.PersistenciaVigenciasGruposSalarialesInterface;
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
public class PersistenciaVigenciasGruposSalariales implements PersistenciaVigenciasGruposSalarialesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Proyectos
     */
    @Override
    public void crear(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.persist(vigenciasGruposSalariales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

    /*
     *Editar Proyectos
     */
    @Override
    public void editar(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.merge(vigenciasGruposSalariales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

    /*
     *Borrar Proyectos
     */
    @Override
    public void borrar(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.remove(em.merge(vigenciasGruposSalariales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

    /*
     *Encontrar un Proyecto
     */
    @Override
    public VigenciasGruposSalariales buscarVigenciaGrupoSalarial(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(VigenciasGruposSalariales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaGrupoSalarial PersistenciaVigenciasGruposSalariales : " + e.toString());
            return null;
        }

    }

    /*
     *Encontrar todas los proyectos
     */
    @Override
    public List<VigenciasGruposSalariales> buscarVigenciasGruposSalariales() {
        try {
            List<VigenciasGruposSalariales> vigenciasGruposSalariales = (List<VigenciasGruposSalariales>) em.createNamedQuery("VigenciasGruposSalariales.findAll").getResultList();
            return vigenciasGruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasGruposSalariales PersistenciaVigenciasGruposSalariales : " + e.toString());
            return null;
        }
    }

    @Override
    public VigenciasGruposSalariales buscarVigenciaGrupoSalarialSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vgs FROM VigenciasGruposSalariales vgs WHERE vgs.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            VigenciasGruposSalariales vigenciasGruposSalariales = (VigenciasGruposSalariales) query.getSingleResult();
            return vigenciasGruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaGrupoSalarialSecuencia PersistenciaVigenciasGruposSalariales : " + e.toString());
            VigenciasGruposSalariales vigenciasGruposSalariales = null;
            return vigenciasGruposSalariales;
        }
    }
    
    @Override
    public List<VigenciasGruposSalariales> buscarVigenciaGrupoSalarialSecuenciaGrupoSal(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT vgs FROM VigenciasGruposSalariales vgs WHERE vgs.gruposalarial.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            List<VigenciasGruposSalariales> vigenciasGruposSalariales = (List<VigenciasGruposSalariales>) query.getResultList();
            return vigenciasGruposSalariales;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaGrupoSalarialSecuencia PersistenciaVigenciasGruposSalariales : " + e.toString());
            List<VigenciasGruposSalariales> vigenciasGruposSalariales = null;
            return vigenciasGruposSalariales;
        }
    }
}
