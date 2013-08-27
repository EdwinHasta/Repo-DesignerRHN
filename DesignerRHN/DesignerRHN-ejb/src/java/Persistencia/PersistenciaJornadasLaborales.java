package Persistencia;

import Entidades.JornadasLaborales;
import InterfacePersistencia.PersistenciaJornadasLaboralesInterface;
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
public class PersistenciaJornadasLaborales implements PersistenciaJornadasLaboralesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear JornadasLaborales.
     */
    @Override
    public void crear(JornadasLaborales jornadasLaborales) {
        try {
            em.persist(jornadasLaborales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaJornadasLaborales");
        }
    }

    /*
     *Editar JornadasLaborales. 
     */
    @Override
    public void editar(JornadasLaborales jornadasLaborales) {
        try {
            em.merge(jornadasLaborales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaJornadasLaborales");
        }
    }

    /*
     *Borrar JornadasLaborales.
     */
    @Override
    public void borrar(JornadasLaborales jornadasLaborales) {
        try {
            em.remove(em.merge(jornadasLaborales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaJornadasLaborales");
        }
    }

    /*
     *Encontrar una JornadasLaborales.
     */
    @Override
    public JornadasLaborales buscarJornadaLaboral(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(JornadasLaborales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarJornadaLaboral PersistenciaJornadasLaborales");
            return null;
        }

    }

    /*
     *Encontrar todas las JornadasLaborales
     */
    @Override
    public List<JornadasLaborales> buscarJornadasLaborales() {
        try {
            List<JornadasLaborales> jornadasLaborales = (List<JornadasLaborales>) em.createNamedQuery("JornadasLaborales.findAll").getResultList();
            return jornadasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarJornadasLaborales PersistenciaJornadasLaborales");
            return null;
        }
    }

    @Override
    public JornadasLaborales buscarJornadaLaboralSecuencia(BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT jl FROM JornadasLaborales jl WHERE jl.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            JornadasLaborales jornadasLaborales = (JornadasLaborales) query.getSingleResult();
            return jornadasLaborales;
        } catch (Exception e) {
            System.out.println("Error buscarJornadaLaboralSecuencia PersistenciaJornadasLaborales");
            JornadasLaborales jornadasLaborales = null;
            return jornadasLaborales;
        }

    }
}
