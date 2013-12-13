/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.PryPlataformas;
import InterfacePersistencia.PersistenciaPryPlataformasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'PryPlataformas'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPryPlataformas implements PersistenciaPryPlataformasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(PryPlataformas plataformas) {
        try {
            em.persist(plataformas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaPryPlataformas : " + e.toString());
        }
    }

    @Override
    public void editar(PryPlataformas plataformas) {
        try {
            em.merge(plataformas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaPryPlataformas : " + e.toString());
        }
    }

    @Override
    public void borrar(PryPlataformas plataformas) {
        try {
            em.remove(em.merge(plataformas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaPryPlataformas : " + e.toString());
        }
    }

    @Override
    public List<PryPlataformas> buscarPryPlataformas() {
        try {
            List<PryPlataformas> plataformas = (List<PryPlataformas>) em.createNamedQuery("PryPlataformas.findAll").getResultList();
            return plataformas;
        } catch (Exception e) {
            System.out.println("Error buscarPryPlataformas PersistenciaPryPlataformas : " + e.toString());
            return null;
        }
    }

    @Override
    public PryPlataformas buscarPryPlataformaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT p FROM PryPlataformas p WHERE p.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            PryPlataformas plataformas = (PryPlataformas) query.getSingleResult();
            return plataformas;
        } catch (Exception e) {
            System.out.println("Error buscarPryPlataformaSecuencia PersistenciaPryPlataformas : " + e.toString());
            PryPlataformas plataformas = null;
            return plataformas;
        }
    }
}
