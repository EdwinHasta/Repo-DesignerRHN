package Persistencia;

import Entidades.VigenciasFormales;
import InterfacePersistencia.PersistenciaVigenciasFormalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasFormales implements PersistenciaVigenciasFormalesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
     public void crear(VigenciasFormales vigenciasFormales) {
        try {
//            System.out.println("Persona: " + vigenciasFormales.getPersona().getNombreCompleto());
            em.merge(vigenciasFormales);
        } catch (PersistenceException ex) {
            System.out.println("Error PersistenciaVigenciasFormales.crear");
        }
    }
       
     
    // Editar VigenciasFormales. 
     
    @Override
    public void editar(VigenciasFormales vigenciasFormales) {
        em.merge(vigenciasFormales);
    }

    /*
     *Borrar Ciudades.
     */
    @Override
    public void borrar(VigenciasFormales vigenciasFormales) {
        em.remove(em.merge(vigenciasFormales));
    }
    
     /*
     *Encontrar todas las Vigencias Formales.
     */
    @Override
    public List<VigenciasFormales> buscarVigenciasFormales() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(VigenciasFormales.class));
        return em.createQuery(cq).getResultList();
    }
    

    //Metodo que trae el ultimo registro agregado
    
    @Override
    public List<VigenciasFormales> educacionPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(vf) FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT vf FROM VigenciasFormales vf WHERE vf.persona.secuencia = :secuenciaPersona and vf.fechavigencia = (SELECT MAX(vfo.fechavigencia) FROM VigenciasFormales vfo WHERE vfo.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasFormales> listaVigenciasFormales = queryFinal.getResultList();
                return listaVigenciasFormales;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasFormales.educacionPersona" + e);
            return null;
        }
    }
    //METODO PARA TRAER LAS VIGENCIAS DE UNA PERSONA

    @Override
    public List<VigenciasFormales> vigenciasFormalesPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT vF FROM VigenciasFormales vF WHERE vF.persona.secuencia = :secuenciaPersona ORDER BY vF.fechavigencia DESC");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            List<VigenciasFormales> listaVigenciasFormales = query.getResultList();
            return listaVigenciasFormales;
        } catch (Exception e) {
            System.out.println("Error PersistenciaTelefonos.telefonoPersona" + e);
            return null;
        }
    }
    
    }
    
    
    
    
    
    
    
    

