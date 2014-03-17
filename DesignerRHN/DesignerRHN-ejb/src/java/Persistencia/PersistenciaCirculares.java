/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.Circulares;
import InterfacePersistencia.PersistenciaCircularesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'Circulares' de la
 * base de datos
 *
 * @author AndresPineda
 */

@Stateless
public class PersistenciaCirculares implements PersistenciaCircularesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Circulares circulares) {
        try {
            em.persist(circulares);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaCirculares : " + e.toString());
        }
    }

    @Override
    public void editar(Circulares circulares) {
        try {
            em.merge(circulares);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaCirculares : " + e.toString());
        }
    }

    @Override
    public void borrar(Circulares circulares) {
        try {
            em.remove(em.merge(circulares));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaCirculares : " + e.toString());
        }
    }

    @Override
    public List<Circulares> buscarCirculares() {
        try {
            Query query = em.createQuery("SELECT c FROM Circulares c");
            List<Circulares> circulares = query.getResultList();
            return circulares;
        } catch (Exception e) {
            System.out.println("Error buscarCirculares PersistenciaCirculares : " + e.toString());
            return null;
        }
    }

    @Override
    public Circulares buscarCircularSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Circulares c WHERE c.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            Circulares circulares = (Circulares) query.getSingleResult();
            return circulares;
        } catch (Exception e) {
            System.out.println("Error buscarCircularSecuencia  PersistenciaCirculares : " + e.toString());
            Circulares circulares = null;
            return circulares;
        }
    }

    @Override
    public List<Circulares> buscarCircularesPorSecuenciaEmpresa(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT c FROM Circulares c WHERE c.empresa.secuencia =:secuencia");
            query.setParameter("secuencia", secuencia);
            List<Circulares> circulares = query.getResultList();
            return circulares;
        } catch (Exception e) {
            System.out.println("Error buscarCircularesPorSecuenciaEmpresa  PersistenciaCirculares : " + e.toString());
            List<Circulares> circulares = null;
            return circulares;
        }
    }
}
