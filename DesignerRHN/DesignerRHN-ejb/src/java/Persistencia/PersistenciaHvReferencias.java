/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HvReferencias;
import InterfacePersistencia.PersistenciaHvReferenciasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'HvReferencias'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHvReferencias implements PersistenciaHvReferenciasInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<HvReferencias> referenciasPersonalesPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'PERSONALES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvReferencias> listaReferenciasPersonales = queryFinal.getResultList();
                return listaReferenciasPersonales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciasHvReferencias.referenciasPersonalesPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<HvReferencias> referenciasFamiliaresPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hvr) FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hvr FROM HvReferencias hvr WHERE hvr.hojadevida.secuencia = :secuenciaHV and hvr.tipo = 'FAMILIARES'");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvReferencias> listaReferenciasPersonales = queryFinal.getResultList();
                return listaReferenciasPersonales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciasHvReferencias.referenciasPersonalesPersona" + e);
            return null;
        }
    }
}
