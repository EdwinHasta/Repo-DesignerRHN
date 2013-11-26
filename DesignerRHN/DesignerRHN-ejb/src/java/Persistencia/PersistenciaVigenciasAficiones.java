package Persistencia;

import Entidades.VigenciasAficiones;
import InterfacePersistencia.PersistenciaVigenciasAficionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaVigenciasAficiones implements PersistenciaVigenciasAficionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

   
    @Override
    public void crear(VigenciasAficiones vigenciasAficiones) {
        try {
            em.merge(vigenciasAficiones);
        } catch (Exception e) {
            System.out.println("La vigencia no exite o esta reservada por lo cual no puede ser modificada (VigenciasAficiones)");
        }
    }

   
    @Override
    public void editar(VigenciasAficiones vigenciasAficiones) {
        try {
            em.merge(vigenciasAficiones);
        } catch (Exception e) {
            System.out.println("ALERTA! Error Persistencia VigenciasAficiones : "+e.toString());
        }
    }

   
    @Override
    public void borrar(VigenciasAficiones vigenciasAficiones) {
        try {
            em.remove(em.merge(vigenciasAficiones));
        } catch (Exception e) {
            System.out.println("Error Persistencia Borrar VigenciasAficiones: " + e);
        }

    }

   
    @Override
    public VigenciasAficiones buscarvigenciaAficion(Object id) {
        try {
            BigInteger in = (BigInteger) id;
            return em.find(VigenciasAficiones.class, in);
        } catch (Exception e) {
            System.out.println("Error buscarvigenciaAficion : "+e.toString());
            return null;
        }
    }

    
    @Override
    public List<VigenciasAficiones> aficionesPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(va) FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT va FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona and va.fechainicial = (SELECT MAX(vaf.fechainicial) FROM VigenciasAficiones vaf WHERE vaf.persona.secuencia = :secuenciaPersona)");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<VigenciasAficiones> listVigenciasAficiones = queryFinal.getResultList();
                return listVigenciasAficiones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaVigenciasAficiones.aficionesPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<VigenciasAficiones> aficionesTotalesSecuenciaPersona(BigInteger secuenciaP){
        try{
        Query queryFinal = em.createQuery("SELECT va FROM VigenciasAficiones va WHERE va.persona.secuencia = :secuenciaPersona;");
                queryFinal.setParameter("secuenciaPersona", secuenciaP);
                List<VigenciasAficiones> listVigenciasAficiones = queryFinal.getResultList();
                return listVigenciasAficiones;
        }catch(Exception e){
            System.out.println("Error aficionesTotalesSecuenciaPersona PersistenciaVigenciasAficiones : "+e.toString());
            return null;
        }
    }
}
