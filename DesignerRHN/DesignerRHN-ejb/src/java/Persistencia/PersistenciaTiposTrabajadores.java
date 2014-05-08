/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.TiposTrabajadores;
import InterfacePersistencia.PersistenciaTiposTrabajadoresInterface;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'TiposTrabajadores'
 * de la base de datos.
 * @author AndresPineda
 */
@Stateless
public class PersistenciaTiposTrabajadores implements PersistenciaTiposTrabajadoresInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
/*    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
*/

    @Override
    public void crear(EntityManager em, TiposTrabajadores tiposTrabajadores) {
        try{
        em.persist(tiposTrabajadores);
        }catch(Exception e){
            System.out.println("Error almacenar en persistencia tipos trbajadores");
        }
    }

    @Override
    public void editar(EntityManager em, TiposTrabajadores tiposTrabajadores) {
        em.merge(tiposTrabajadores);
    }

    @Override
    public void borrar(EntityManager em, TiposTrabajadores tiposTrabajadores) {
        try{
            em.remove(em.merge(tiposTrabajadores));
        }catch(Exception e){
            System.out.println("Error borrarTiposTrabajadores(PersistenciaTiposTrabajadores)");
        }
    }

    @Override
    public List<TiposTrabajadores> buscarTiposTrabajadores(EntityManager em) {
        List<TiposTrabajadores> tipoTLista = (List<TiposTrabajadores>) em.createNamedQuery("TiposTrabajadores.findAll").getResultList();
        return tipoTLista;
    }

    @Override
    public TiposTrabajadores buscarTipoTrabajadorSecuencia(EntityManager em, BigInteger secuencia) {
        try {
            Query query = em.createQuery("SELECT tt FROM TiposTrabajadores e WHERE tt.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            TiposTrabajadores tipoT = (TiposTrabajadores) query.getSingleResult();
            return tipoT;
        } catch (Exception e) {
        }
        TiposTrabajadores tipoT = null;
        return tipoT;
    }
    
    @Override
    public TiposTrabajadores buscarTipoTrabajadorCodigo (EntityManager em, BigDecimal codigo){
        try{
            Query query = em.createNamedQuery("TiposTrabajadores.findByCodigo").setParameter("codigo", codigo);
             TiposTrabajadores tipoTC = (TiposTrabajadores) query.getSingleResult();
             return tipoTC;
        }
        catch(Exception e){
        }
        TiposTrabajadores tipoTC = null;
        return null;
    }
}
