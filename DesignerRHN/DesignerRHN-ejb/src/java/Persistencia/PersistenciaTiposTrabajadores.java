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
 *
 * @author user
 */

@Stateless
public class PersistenciaTiposTrabajadores implements PersistenciaTiposTrabajadoresInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
        /*
     * Crear TiposTrabajadores.
     */
    @Override
    public void crear(TiposTrabajadores tiposTrabajadores) {
        try{
        em.persist(tiposTrabajadores);
        }catch(Exception e){
            System.out.println("Error almacenar en persistencia tipos trbajadores");
        }
    }

    /*
     *Editar TiposTrabajadores. 
     */
    @Override
    public void editar(TiposTrabajadores tiposTrabajadores) {
        em.merge(tiposTrabajadores);
    }

    /*
     *Borrar TiposTrabajadores.
     */
    @Override
    public void borrar(TiposTrabajadores tiposTrabajadores) {
        try{
            em.remove(em.merge(tiposTrabajadores));
        }catch(Exception e){
            System.out.println("Error borrarTiposTrabajadores(PersistenciaTiposTrabajadores)");
        }
    }

    /*
     *Encontrar un TipoTrabajador. 
     */
    @Override 
    public TiposTrabajadores buscarTipoTrabajador(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            //return em.find(Empleados.class, id);
            return em.find(TiposTrabajadores.class, secuencia);
        } catch (Exception e) {
            return null;
        }

    }

    /*
     *Encontrar todas los TiposTrabajadores. 
     */
    @Override
    public List<TiposTrabajadores> buscarTiposTrabajadores() {

        List<TiposTrabajadores> tipoTLista = (List<TiposTrabajadores>) em.createNamedQuery("TiposTrabajadores.findAll").getResultList();
        return tipoTLista;
    }

    @Override
    public TiposTrabajadores buscarTipoTrabajadorSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT tt FROM TiposTrabajadores e WHERE tt.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TiposTrabajadores tipoT = (TiposTrabajadores) query.getSingleResult();
            return tipoT;
        } catch (Exception e) {
        }
        TiposTrabajadores tipoT = null;
        return tipoT;
    }
    
    @Override
    public TiposTrabajadores buscarTipoTrabajadorCodigo (BigDecimal codigo){
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
