/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Periodicidades;
import InterfacePersistencia.PersistenciaPeriodicidadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Periodicidades'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaPeriodicidades implements PersistenciaPeriodicidadesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Periodicidades periodicidades) {
        em.persist(periodicidades);
    }

    @Override
    public void editar(Periodicidades periodicidades) {
        em.merge(periodicidades);
    }

    @Override
    public void borrar(Periodicidades periodicidades) {
        em.remove(em.merge(periodicidades));
    }

    @Override
    public boolean verificarCodigoPeriodicidad(BigInteger codigoPeriodicidad) {
        try {
            Query query = em.createQuery("SELECT COUNT(p) FROM Periodicidades p WHERE p.codigo = :codigo");
            query.setParameter("codigo", codigoPeriodicidad);
            Long resultado = (Long) query.getSingleResult();
            return resultado > 0;
        } catch (Exception e) {
            System.out.println("Exepcion: " + e);
            return false;
        }
    }

    @Override
    public Periodicidades buscarPeriodicidades(BigInteger secuencia) {
        try {
            return em.find(Periodicidades.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la persistencia vigencias formas pagos ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<Periodicidades> buscarPeriodicidades() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Periodicidades.class));
        return em.createQuery(cq).getResultList();
    }
}
