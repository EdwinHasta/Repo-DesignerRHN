package Persistencia;

import Entidades.MotivosRetiros;
import InterfacePersistencia.PersistenciaMotivosRetirosInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author AndresPineda
 */
@Stateless
public class PersistenciaMotivosRetiros implements PersistenciaMotivosRetirosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Reforma Laboral.
     */
    @Override
    public void crear(MotivosRetiros motivosRetiros) {
        em.persist(motivosRetiros);
    }

    /*
     *Editar Reforma laboral. 
     */
    @Override
    public void editar(MotivosRetiros motivosRetiros) {
        em.merge(motivosRetiros);
    }

    /*
     *Borrar Reforma Laboral.
     */
    @Override
    public void borrar(MotivosRetiros motivosRetiros) {
        em.remove(em.merge(motivosRetiros));
    }

    /*
     *Encontrar una reforma laboral. 
     */
    @Override
    public MotivosRetiros buscarMotivoRetiro(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(MotivosRetiros.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todas las reformas. 
     */
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
