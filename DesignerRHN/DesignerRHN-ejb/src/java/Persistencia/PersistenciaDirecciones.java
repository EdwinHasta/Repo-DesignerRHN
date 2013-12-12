/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Direcciones;
import InterfacePersistencia.PersistenciaDireccionesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless 
 * Clase encargada de realizar operaciones sobre la tabla 'Direcciones'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaDirecciones implements PersistenciaDireccionesInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Direcciones direcciones) {
        try {
            em.merge(direcciones);
        } catch (Exception e) {
            System.out.println("Error en PersistenciaDirecciones.crear: " + e);
        }
    }

    @Override
    public void editar(Direcciones direcciones) {
        em.merge(direcciones);
    }

    @Override
    public void borrar(Direcciones direcciones) {
        em.remove(em.merge(direcciones));
    }

    @Override
    public Direcciones buscarDireccion(BigInteger secuencia) {
        return em.find(Direcciones.class, secuencia);
    }

    @Override
    public List<Direcciones> buscarDirecciones() {
        javax.persistence.criteria.CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Direcciones.class));
        return em.createQuery(cq).getResultList();
    }

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
