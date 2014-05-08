/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.Parametros;
import InterfacePersistencia.PersistenciaParametrosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless.<br> 
 * Clase encargada de realizar operaciones sobre la tabla 'Parametros'
 * de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaParametros implements PersistenciaParametrosInterface{
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
//    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
//    private EntityManager em;
    
    @Override
    public void crear(EntityManager em, Parametros parametro) {
        try {
            em.persist(parametro);
        } catch (Exception e) {
            System.out.println("Error PersistenciaParametros.crear" + e);
        }
    }

    @Override
    public void borrar(EntityManager em, Parametros parametro) {
        try{
            em.remove(em.merge(parametro));
        }catch(Exception e){
            System.out.println("Error PersistenciaParametros.borrar");
        }
    }
    
    @Override
    public List<Parametros> parametrosComprobantes(EntityManager em, String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT p FROM Parametros p WHERE EXISTS (SELECT pi FROM ParametrosInstancias pi, UsuariosInstancias ui WHERE pi.instancia.secuencia = ui.instancia.secuencia AND ui.usuario.alias = :usuarioBD AND pi.parametro.secuencia = p.secuencia) ORDER BY p.empleado.codigoempleado");
            query.setParameter("usuarioBD", usuarioBD);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            List<Parametros> listaParametros = query.getResultList();
            return listaParametros;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametros.parametrosComprobantes" + e);
            return null;
        }
    }
    
    @Override
    public List<Parametros> empleadosParametros(EntityManager em) {
        try {
            Query query = em.createQuery("SELECT p FROM Parametros p WHERE p.empleado IS NOT NULL");
            List<Parametros> listaParametros = query.getResultList();
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            return listaParametros;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametros.empleadosParametros" + e);
            return null;
        }
    }
    
    @Override
    public void borrarParametros(EntityManager em, BigInteger secParametrosEstructuras) {
        try {
            Query query = em.createQuery("DELETE FROM Parametros p WHERE p.parametroestructura.secuencia = :secParametrosEstructuras");
            query.setParameter("secParametrosEstructuras", secParametrosEstructuras);
            query.setHint("javax.persistence.cache.storeMode", "REFRESH");
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("PersistenciaParametros.borrarParametros. ");
        }
    }
}
