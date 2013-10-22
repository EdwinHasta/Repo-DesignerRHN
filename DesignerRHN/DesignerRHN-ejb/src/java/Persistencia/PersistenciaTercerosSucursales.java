package Persistencia;

import Entidades.TercerosSucursales;
import InterfacePersistencia.PersistenciaTercerosSucursalesInterface;
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
public class PersistenciaTercerosSucursales implements PersistenciaTercerosSucursalesInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(TercerosSucursales tercerosSucursales) {
        try {
            em.persist(tercerosSucursales);
        } catch (Exception e) {
            System.out.println("Error crear PersistenciaTercerosSucursales");
        }
    }

    @Override
    public void editar(TercerosSucursales tercerosSucursales) {
        try {
            em.merge(tercerosSucursales);
        } catch (Exception e) {
            System.out.println("Error editar PersistenciaTercerosSucursales");
        }
    }

    @Override
    public void borrar(TercerosSucursales tercerosSucursales) {
        try {
            em.remove(em.merge(tercerosSucursales));
        } catch (Exception e) {
            System.out.println("Error borrar PersistenciaTercerosSucursales");
        }
    }

    @Override
    public TercerosSucursales buscarTerceroSucursal(Object id) {
        try {
            BigInteger secuencia = new BigInteger(id.toString());
            return em.find(TercerosSucursales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Error buscarTerceroSucursal PersistenciaTercerosSucursales");
            return null;
        }

    }

    @Override
    public List<TercerosSucursales> buscarTercerosSucursales() {
        try {
            List<TercerosSucursales> tercerosSucursales = (List<TercerosSucursales>) em.createNamedQuery("TercerosSucursales.findAll").getResultList();
            return tercerosSucursales;
        } catch (Exception e) {
            System.out.println("Error buscarTercerosSucursales");
            return null;
        }
    }

    @Override
    public TercerosSucursales buscarTercerosSucursalesSecuencia(BigInteger secuencia) {

        try {
            Query query = em.createQuery("SELECT ts FROM TercerosSucursales ts WHERE ts.secuencia = :secuencia");
            query.setParameter("secuencia", secuencia);
            TercerosSucursales tercerosSucursales = (TercerosSucursales) query.getSingleResult();
            return tercerosSucursales;
        } catch (Exception e) {
            System.out.println("Error buscarTercerosSucursalesSecuencia");
            TercerosSucursales tercerosSucursales = null;
            return tercerosSucursales;
        }
    }
    
    public List<TercerosSucursales> buscarTercerosSucursalesPorTerceroSecuencia(BigInteger secuencia){
        try{
            Query query = em.createQuery("SELECT ts FROM TercerosSucursales ts WHERE ts.tercero.secuencia = :secuenciaT");
            query.setParameter("secuenciaT", secuencia);
            List<TercerosSucursales> listTercerosS = query.getResultList();
            return listTercerosS;
        } catch(Exception e){
            System.out.println("Error buscarTercerosSucursalesPorTerceroSecuencia PersistenciaTerceroSurcusal : "+e.toString());
            return null;
        }
    }
}
