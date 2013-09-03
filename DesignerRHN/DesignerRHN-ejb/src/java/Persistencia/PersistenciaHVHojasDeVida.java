package Persistencia;

import Entidades.HVHojasDeVida;
import InterfacePersistencia.PersistenciaHVHojasDeVidaInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaHVHojasDeVida implements PersistenciaHVHojasDeVidaInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    /*
     *Editar empleado. 
     */
    public void editar(HVHojasDeVida hVHojasDeVida) {
        em.merge(hVHojasDeVida);
    }
    
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
