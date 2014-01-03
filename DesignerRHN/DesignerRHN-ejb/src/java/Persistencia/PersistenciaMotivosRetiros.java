/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.MotivosRetiros;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br>
 * Clase encargada de realizar operaciones sobre la tabla 'MotivosRetiros'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaMotivosRetiros implements PersistenciaMotivosRetirosInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(MotivosRetiros motivosRetiros) {
        em.persist(motivosRetiros);
    }

    @Override
    public void editar(MotivosRetiros motivosRetiros) {
        em.merge(motivosRetiros);
    }

    @Override
    public void borrar(MotivosRetiros motivosRetiros) {
        em.remove(em.merge(motivosRetiros));
    }  

    @Override
    public List<MotivosRetiros> buscarMotivosRetiros() {

        List<MotivosRetiros> reformaLista = (List<MotivosRetiros>) em.createNamedQuery("MotivosRetiros.findAll").getResultList();
        return reformaLista;
    }

    @Override
    public MotivosRetiros buscarMotivoRetiroSecuencia(BigDecimal secuencia) {

        try {
            Query query = em.createQuery("SELECT mr FROM MotivosRetiros mr WHERE mr.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            MotivosRetiros motivoR = (MotivosRetiros) query.getSingleResult();
            return motivoR;
        } catch (Exception e) {
            MotivosRetiros motivoR = null;
            return motivoR;
        }

    }
}
