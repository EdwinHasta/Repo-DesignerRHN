/**
 * Documentación a cargo de AndresPineda
 */
package Persistencia;

import Entidades.DetallesReformasLaborales;
import InterfacePersistencia.PersistenciaDetallesReformasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'DetallesReformasLaborales' de la base de datos.
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaDetallesReformasLaborales implements PersistenciaDetallesReformasLaboralesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(DetallesReformasLaborales detallesReformasLaborales) {
        em.persist(detallesReformasLaborales);
    }

    @Override
    public void editar(DetallesReformasLaborales detallesReformasLaborales) {
        em.merge(detallesReformasLaborales);
    }

    @Override
    public void borrar(DetallesReformasLaborales detallesReformasLaborales) {
        em.remove(em.merge(detallesReformasLaborales));
    }

    @Override
    public List<DetallesReformasLaborales> buscarDetallesReformasLaborales() {
        Query query = em.createQuery("SELECT d FROM DetallesReformasLaborales d");
        List<DetallesReformasLaborales> detallesReformasLaborales = (List<DetallesReformasLaborales>) query.getResultList();
        return detallesReformasLaborales;
    }

    @Override
    public DetallesReformasLaborales buscarDetalleReformaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT d FROM DetallesReformasLaborales d WHERE d.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            DetallesReformasLaborales detallesReformasLaborales = (DetallesReformasLaborales) query.getSingleResult();
            return detallesReformasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarDetalleReformaSecuencia PersistenciaDetallesReformasLaborales : " + e.toString());
            DetallesReformasLaborales detallesReformasLaborales = null;
            return detallesReformasLaborales;
        }
    }

    @Override 
    public List<DetallesReformasLaborales> buscarDetalleReformasParaReformaSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT d FROM DetallesReformasLaborales d WHERE d.reformalaboral.secuencia=:secuencia");
            query.setParameter("secuencia", secuencia);
            List<DetallesReformasLaborales> detallesReformasLaborales = (List<DetallesReformasLaborales>) query.getResultList();
            return detallesReformasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarDetalleReformasParaReformaSecuencia PersistenciaDetallesReformasLaborales : " + e.toString());
            List<DetallesReformasLaborales> detallesReformasLaborales = null;
            return detallesReformasLaborales;
        }
    }
}
