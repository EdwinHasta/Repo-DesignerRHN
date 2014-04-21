/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Sucursales;
import InterfacePersistencia.PersistenciaSucursalesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Sucursales'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaSucursales implements PersistenciaSucursalesInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void crear(Sucursales sucursales) {
        em.persist(sucursales);
    }

    @Override
    public void editar(Sucursales sucursales) {
        em.merge(sucursales);
    }

    @Override
    public void borrar(Sucursales sucursales) {
        em.remove(em.merge(sucursales));
    }

    @Override
    public Sucursales buscarSucursal(BigInteger secuencia) {
        try {
            return em.find(Sucursales.class, secuencia);
        } catch (Exception e) {
            System.out.println("Persistencia Sucursales "+e);
            return null;
        }
    }

    @Override
    public List<Sucursales> consultarSucursales() {
        Query query = em.createQuery("SELECT m FROM Sucursales m");
        List<Sucursales> lista = query.getResultList();
        return lista;
    }
    
     public BigInteger contarVigenciasFormasPagosSucursal(BigInteger secuencia) {
        BigInteger retorno = new BigInteger("-1");
        try {
            String sqlQuery = "SELECT COUNT(*)FROM vigenciasformaspagos WHERE sucursal = ?";
            Query query = em.createNativeQuery(sqlQuery);
            query.setParameter(1, secuencia);
            retorno = new BigInteger(query.getSingleResult().toString());
            System.out.println("Contador PersistenciaSubCategorias contarVigenciasFormasPagosSucursal persistencia " + retorno);
            return retorno;
        } catch (Exception e) {
            System.err.println("Error PERSISTENCIASUCURSALES contarVigenciasFormasPagosSucursal : " + e);
            return retorno;
        }
    }
}
