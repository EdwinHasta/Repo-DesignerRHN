/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.SueldosMercados;
import InterfacePersistencia.PersistenciaSueldosMercadosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'SueldosMercados' de
 * la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaSueldosMercados implements PersistenciaSueldosMercadosInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(SueldosMercados sueldosMercados) {
        try {
            em.persist(sueldosMercados);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaSueldosMercados : " + e.toString());
        }
    }

    @Override
    public void editar(SueldosMercados sueldosMercados) {
        try {
            em.merge(sueldosMercados);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaSueldosMercados : " + e.toString());
        }
    }

    @Override
    public void borrar(SueldosMercados sueldosMercados) {
        try {
            em.remove(em.merge(sueldosMercados));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaSueldosMercados : " + e.toString());
        }
    }

    @Override
    public List<SueldosMercados> buscarSueldosMercados() {
        try {
            Query query = em.createQuery("SELECT sm FROM SueldosMercados sm ORDER BY sm.sueldomax ASC");
            List<SueldosMercados> sueldosMercados = query.getResultList();
            return sueldosMercados;
        } catch (Exception e) {
            System.out.println("Error buscarSueldosMercados PersistenciaSueldosMercados : " + e.toString());
            return null;
        }
    }

    @Override
    public SueldosMercados buscarSueldosMercadosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT sm FROM SueldosMercados sm WHERE sm.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            SueldosMercados sueldosMercados = (SueldosMercados) query.getSingleResult();
            return sueldosMercados;
        } catch (Exception e) {
            System.out.println("Error buscarSueldosMercadosSecuencia PersistenciaSueldosMercados : " + e.toString());
            SueldosMercados sueldosMercados = null;
            return sueldosMercados;
        }
    }

    @Override
    public List<SueldosMercados> buscarSueldosMercadosPorSecuenciaCargo(BigInteger secCargo) {
        try {
            Query query = em.createQuery("SELECT sm FROM SueldosMercados sm WHERE sm.cargo.secuencia=:secCargo");
            query.setParameter("secCargo", secCargo);
            List<SueldosMercados> sueldosMercados = query.getResultList();
            return sueldosMercados;
        } catch (Exception e) {
            System.out.println("Error buscarSueldosMercadosPorSecuenciaCargo PersistenciaSueldosMercados : " + e.toString());
            return null;
        }
    }

}
