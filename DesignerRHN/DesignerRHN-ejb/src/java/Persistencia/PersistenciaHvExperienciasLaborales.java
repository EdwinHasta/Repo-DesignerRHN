/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HvExperienciasLaborales;
import InterfacePersistencia.PersistenciaHvExperienciasLaboralesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'HvExperienciasLaborales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHvExperienciasLaborales implements PersistenciaHvExperienciasLaboralesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    /*@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;*/

    @Override
    public void crear(EntityManager em,HvExperienciasLaborales experienciasLaborales) {
        em.persist(experienciasLaborales);
    }

    @Override
    public void editar(EntityManager em,HvExperienciasLaborales experienciasLaborales) {
        em.merge(experienciasLaborales);
    }

    @Override
    public void borrar(EntityManager em,HvExperienciasLaborales experienciasLaborales) {
        em.remove(em.merge(experienciasLaborales));
    }

    @Override
    public HvExperienciasLaborales buscarHvExperienciaLaboral(EntityManager em,BigInteger secuencia) {
        try {
            return em.find(HvExperienciasLaborales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error en la PersistenciaHvExperienciasLaborales ERROR : " + e);
            return null;
        }
    }

    @Override
    public List<HvExperienciasLaborales> experienciaLaboralPersona(EntityManager em,BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hve) FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hve FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV and hve.fechadesde = (SELECT MAX(hvex.fechadesde) FROM HvExperienciasLaborales hvex WHERE hvex.hojadevida.secuencia = :secuenciaHV)");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                query.setHint("javax.persistence.cache.storeMode", "REFRESH");
                List<HvExperienciasLaborales> listaExperienciasLaborales = queryFinal.getResultList();
                return listaExperienciasLaborales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvExperienciasLaborales.experienciaLaboralPersona" + e);
            return null;
        }
    }

    @Override
    public List<HvExperienciasLaborales> experienciasLaboralesSecuenciaEmpleado(EntityManager em,BigInteger secuenciaHv) {
        try {
            Query queryFinal = em.createQuery("SELECT hve FROM HvExperienciasLaborales hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            queryFinal.setParameter("secuenciaHV", secuenciaHv);
            queryFinal.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<HvExperienciasLaborales> listaExperienciasLaborales = queryFinal.getResultList();
            return listaExperienciasLaborales;
        } catch (Exception e) {
            System.out.println("Error experienciasLaboralesSecuenciaEmpleado PersistenciaHvExperienciasLaborales : "+e.toString());
            return null;
        }
    }
}
