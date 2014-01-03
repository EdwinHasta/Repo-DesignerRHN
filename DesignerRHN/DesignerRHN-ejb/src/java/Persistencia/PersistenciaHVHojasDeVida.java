/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.HVHojasDeVida;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'HVHojasDeVida'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaHVHojasDeVida implements PersistenciaHVHojasDeVidaInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void editar(HVHojasDeVida hVHojasDeVida) {
        em.merge(hVHojasDeVida);
    }
    
    @Override
    public HVHojasDeVida hvHojaDeVidaPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT hv from HVHojasDeVida hv where hv.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            HVHojasDeVida hVHojasDeVida = (HVHojasDeVida) query.getSingleResult();
            return hVHojasDeVida;
        } catch (Exception e) {
            return null;
        }
    }
}
