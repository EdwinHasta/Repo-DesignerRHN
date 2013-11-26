package Persistencia;

import Entidades.Demandas;
import InterfacePersistencia.PersistenciaDemandasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaDemandas implements PersistenciaDemandasInterface{

@PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(Demandas demandas) {
        try {
            em.persist(demandas);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaDemandas : "+e.toString());
        }
    }

    @Override
    public void editar(Demandas demandas) {
        try {
            em.merge(demandas);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaDemandas : "+e.toString());
        }
    }

    @Override
    public void borrar(Demandas demandas) {
        try {
            em.remove(em.merge(demandas));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaDemandas : "+e.toString());
        }
    }

    @Override
    public List<Demandas> demandasPersona(BigInteger secuenciaEmpl) {
        try {
            Query query = em.createQuery("SELECT COUNT(d) FROM Demandas d WHERE d.empleado.secuencia = :secuenciaEmpl");
            query.setParameter("secuenciaEmpl", secuenciaEmpl);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT d FROM Demandas d WHERE d.empleado.secuencia = :secuenciaEmpl");
                queryFinal.setParameter("secuenciaEmpl", secuenciaEmpl);
                List<Demandas> listaDemandas = queryFinal.getResultList();
                return listaDemandas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaDemandas.demandasPersona" + e);
            return null;
        }
    }
    
    @Override
    public List<Demandas> listDemandasSecuenciaEmpleado(BigInteger secuencia){
        try{
            Query queryFinal = em.createQuery("SELECT d FROM Demandas d WHERE d.empleado.secuencia = :secuenciaEmpl");
                queryFinal.setParameter("secuenciaEmpl", secuencia);
                List<Demandas> listaDemandas = queryFinal.getResultList();
                return listaDemandas;
        }catch(Exception e){
            System.out.println("Error listDemandasSecuenciaEmpleado PersistenciaDemandas : "+e.toString());
            return null;
        }
    }
}
