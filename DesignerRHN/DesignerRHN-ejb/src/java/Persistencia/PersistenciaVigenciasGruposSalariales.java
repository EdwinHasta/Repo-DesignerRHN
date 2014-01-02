/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
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
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasGruposSalariales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaVigenciasGruposSalariales implements PersistenciaVigenciasGruposSalarialesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.persist(vigenciasGruposSalariales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.merge(vigenciasGruposSalariales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasGruposSalariales vigenciasGruposSalariales) {
        try {
            em.remove(em.merge(vigenciasGruposSalariales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaVigenciasGruposSalariales : " + e.toString());
        }
    }

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
