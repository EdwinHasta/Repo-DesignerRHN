package Persistencia;

import Entidades.TiposSueldos;
import InterfacePersistencia.PersistenciaTiposSueldosInterface;
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
public class PersistenciaTiposSueldos implements PersistenciaTiposSueldosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TiposSueldos tiposSueldos) {
        try{
        em.persist(tiposSueldos);
        }catch(Exception e){
            System.out.println("Error crear PersistenciaTiposSueldos");
        }
    }

   

    @Override
    public void editar(TiposSueldos tiposSueldos) {
        try{
        em.merge(tiposSueldos);
        }catch(Exception e){
            System.out.println("Error editar PersistenciaTiposSueldos");
        }
    }

   

    @Override
    public void borrar(TiposSueldos tiposSueldos) {
        try{
        em.remove(em.merge(tiposSueldos));
        }catch(Exception e){
            System.out.println("Error borrar PersistenciaTiposSueldos");
        }
    }

   
    @Override
    public TiposSueldos buscarTipoSueldo(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TiposSueldos.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTipoSueldo PersistenciaTipoSueldo");
            return null;
        }

    }

    @Override
    public List<TiposSueldos> buscarTiposSueldos() {
        try{
        List<TiposSueldos> tiposSueldos = (List<TiposSueldos>) em.createNamedQuery("TiposSueldos.findAll").getResultList();
        return tiposSueldos;
        } catch(Exception e){
            System.out.println("Error buscar lista tipos sueldos");
            return null;
        }
    }


    @Override
    public TiposSueldos buscarTipoSueldoSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT t FROM TiposSueldos t WHERE t.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposSueldos tiposSueldos = (TiposSueldos) query.getSingleResult();
            return tiposSueldos;
        } catch (Exception e) {
            System.out.println("Error buscar tipo sueldo por secuencia");
            TiposSueldos tiposSueldos = null;
            return tiposSueldos;
        }

    }
   

}
