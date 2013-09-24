package Persistencia;

import Entidades.Direcciones;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaDirecciones implements PersistenciaDireccionesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    /*
     * Crear Direccion.
     */
    @Override
    public void crear(Direcciones direcciones) {
        try {
            em.merge(direcciones);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaDirecciones.crear: " + e);
        }
    }
    /*
     *Editar Direccion. 
     */

    @Override
    public void editar(Direcciones direcciones) {
        em.merge(direcciones);
    }

    /*
     *Borrar Direccion.
     */
    @Override
    public void borrar(Direcciones direcciones) {
        em.remove(em.merge(direcciones));
    }

    /*
     *Encontrar una Direccion. 
     */
    @Override
    public Direcciones buscarDireccion(BigInteger id) {
        return em.find(Direcciones.class, id);
    }

    /*
     *Encontrar todas las Direcciones.
     */
    @Override
    public List<Direcciones> buscarDirecciones() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Direcciones.class));
        return em.createQuery(cq).getResultList();
    }

    /*
     * Encontrar Direccion de una Persona.
     */
    @Override
    public List<Direcciones> direccionPersona(BigInteger secuenciaPersona) {
        try {
            Query query = em.createQuery("SELECT COUNT(d) FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT d FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona and d.fechavigencia = (SELECT MAX(di.fechavigencia) FROM Direcciones di WHERE di.persona.secuencia = :secuenciaPersona) ");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<Direcciones> listaDirecciones = queryFinal.getResultList();
                return listaDirecciones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.direccionPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<Direcciones> direccionesPersona(BigInteger secuenciaPersona){
        try {
            Query query = em.createQuery("SELECT COUNT(d) FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona");
            query.setParameter("secuenciaPersona", secuenciaPersona);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT d FROM Direcciones d WHERE d.persona.secuencia = :secuenciaPersona ");
                queryFinal.setParameter("secuenciaPersona", secuenciaPersona);
                List<Direcciones> listaDirecciones = queryFinal.getResultList();
                return listaDirecciones;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDirecciones.direccionPersona" + e);
            return null;
        }
    }
    
    
    
}
