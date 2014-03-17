/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.VigenciasMonedasBases;
import InterfacePersistencia.PersistenciaVigenciasMonedasBasesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'VigenciasMonedasBases' de la
 * base de datos
 *
 * @author AndresPineda
 */

@Stateless
public class PersistenciaVigenciasMonedasBases implements PersistenciaVigenciasMonedasBasesInterface{

      /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(VigenciasMonedasBases monedasBases) {
        try {
            em.persist(monedasBases);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaVigenciasMonedasBases : " + e.toString());
        }
    }

    @Override
    public void editar(VigenciasMonedasBases monedasBases) {
        try {
            em.merge(monedasBases);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaVigenciasMonedasBases : " + e.toString());
        }
    }

    @Override
    public void borrar(VigenciasMonedasBases monedasBases) {
        try {
            em.remove(em.merge(monedasBases));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaVigenciasMonedasBases : " + e.toString());
        }
    }

    @Override
    public List<VigenciasMonedasBases> buscarVigenciasMonedasBases() {
        try {
            Query query = em.createQuery("SELECT c FROM VigenciasMonedasBases c");
            List<VigenciasMonedasBases> monedasBases = query.getResultList();
            return monedasBases;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasMonedasBases PersistenciaVigenciasMonedasBases : " + e.toString());
            return null;
        }
    }

    @Override
    public VigenciasMonedasBases buscarVigenciaMonedaBaseSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM VigenciasMonedasBases c WHERE c.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            VigenciasMonedasBases monedasBases = (VigenciasMonedasBases) query.getSingleResult();
            return monedasBases;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciaMonedaBaseSecuencia  PersistenciaVigenciasMonedasBases : " + e.toString());
            VigenciasMonedasBases monedasBases = null;
            return monedasBases;
        }
    }

    @Override
    public List<VigenciasMonedasBases> buscarVigenciasMonedasBasesPorSecuenciaEmpresa(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM VigenciasMonedasBases c WHERE c.empresa.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            List<VigenciasMonedasBases> monedasBases = query.getResultList();
            return monedasBases;
        } catch (Exception e) {
            System.out.println("Error buscarVigenciasMonedasBasesPorSecuenciaEmpresa  PersistenciaVigenciasMonedasBases : " + e.toString());
            List<VigenciasMonedasBases> monedasBases = null;
            return monedasBases;
        }
    }
}
