package Persistencia;

import Entidades.TiposDescansos;
import InterfacePersistencia.PersistenciaTiposDescansosInterface;
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
public class PersistenciaTiposDescansos implements PersistenciaTiposDescansosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear JornadasLaborales.
     */
    @Override
    public void crear(TiposDescansos tiposDescansos) {
        try {
            em.persist(tiposDescansos);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTiposDescansos");
        }
    }

    /*
     *Editar JornadasLaborales. 
     */
    @Override
    public void editar(TiposDescansos tiposDescansos) {
        try {
            em.merge(tiposDescansos);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTiposDescansos");
        }
    }

    /*
     *Borrar JornadasLaborales.
     */
    @Override
    public void borrar(TiposDescansos tiposDescansos) {
        try {
            em.remove(em.merge(tiposDescansos));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTiposDescansos");
        }
    }

    /*
     *Encontrar una JornadasLaborales.
     */
    @Override
    public TiposDescansos buscarTipoDescanso(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TiposDescansos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTipoDescanso PersistenciaTiposDescansos");
            return null;
        }

    }

    /*
     *Encontrar todas las JornadasLaborales
     */
    @Override
    public List<TiposDescansos> buscarTiposDescansos() {
        try {
            List<TiposDescansos> tiposDescansos = (List<TiposDescansos>) em.createNamedQuery("TiposDescansos.findAll").getResultList();
            return tiposDescansos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDescansos PersistenciaTiposDescansos");
            return null;
        }
    }

    @Override
    public TiposDescansos buscarTiposDescansosSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT tp FROM TiposDescansos tp WHERE tp.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposDescansos tiposDescansos = (TiposDescansos) query.getSingleResult();
            return tiposDescansos;
        } catch (Exception e) {
            System.out.println("Error buscarTiposDescansosSecuencia PersistenciaTiposDescansos");
            TiposDescansos tiposDescansos = null;
            return tiposDescansos;
        }

    }

}
