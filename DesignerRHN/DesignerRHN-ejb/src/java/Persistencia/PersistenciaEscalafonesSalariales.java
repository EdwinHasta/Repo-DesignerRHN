/**
 * Documentación a cargo de Andres Pineda
 */
package Persistencia;

import Entidades.EscalafonesSalariales;
import InterfacePersistencia.PersistenciaEscalafonesSalarialesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla
 * 'EscalafonesSalariales' de la base de datos.
 *
 * @author Andres Pineda
 */
@Stateless
public class PersistenciaEscalafonesSalariales implements PersistenciaEscalafonesSalarialesInterface{

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(EscalafonesSalariales escalafonesSalariales) {
        try {
            em.persist(escalafonesSalariales);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (EscalafonesSalariales)");
        }
    }

    @Override
    public void editar(EscalafonesSalariales escalafonesSalariales) {
        try {
            em.merge(escalafonesSalariales);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el EscalafonesSalariales PersistenciaEscalafonesSalariales : "+e.toString());
        }
    }

    @Override
    public void borrar(EscalafonesSalariales escalafonesSalariales) {
        try {
            em.remove(em.merge(escalafonesSalariales));
        } catch (Exception e) {
            System.out.println("No se pudo borrar el EscalafonesSalariales PersistenciaEscalafonesSalariales : "+e.toString());
        }
    }

    @Override
    public List<EscalafonesSalariales> buscarEscalafones() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(EscalafonesSalariales.class));
        return em.createQuery(cq).getResultList();
    }

    @Override
    public EscalafonesSalariales buscarEscalafonSecuencia(BigInteger secEscalafon) {
        try {
            Query query = em.createNamedQuery("SELECT e FROM EscalafonesSalariales e WHERE e.secuencia=:secuencia");
            query.setParameter("secuencia", secEscalafon);
            EscalafonesSalariales escalafonesSalariales = (EscalafonesSalariales) query.getSingleResult();
            return escalafonesSalariales;
        } catch (Exception e) {
            System.err.println("Error buscarEscalafonSecuencia PersistenciaEscalafonesSalariales : "+e.toString());
            return null;
        }
    }
}
