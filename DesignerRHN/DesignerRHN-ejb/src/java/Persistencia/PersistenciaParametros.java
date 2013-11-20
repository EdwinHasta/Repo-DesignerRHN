package Persistencia;

import Entidades.Parametros;
import InterfacePersistencia.PersistenciaParametrosInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaParametros implements PersistenciaParametrosInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    @Override
    public void borrar(Parametros parametro) {
        try{
            em.remove(em.merge(parametro));
        }catch(Exception e){
            System.out.println("Error PersistenciaParametros.borrar");
        }
    }
    
    @Override
    public List<Parametros> parametrosComprobantes(String usuarioBD) {
        try {
            Query query = em.createQuery("SELECT p FROM Parametros p WHERE EXISTS (SELECT pi FROM ParametrosInstancias pi, UsuariosInstancias ui WHERE pi.instancia.secuencia = ui.instancia.secuencia AND ui.usuario.alias = :usuarioBD AND pi.parametro.secuencia = p.secuencia) ORDER BY p.empleado.codigoempleado");
            query.setParameter("usuarioBD", usuarioBD);
            List<Parametros> listaParametros = query.getResultList();
            return listaParametros;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametros.parametrosComprobantes" + e);
            return null;
        }
    }
    
    public List<Parametros> empleadosParametros() {
        try {
            Query query = em.createQuery("SELECT p FROM Parametros p WHERE p.empleado IS NOT NULL");
            List<Parametros> listaParametros = query.getResultList();
            return listaParametros;
        } catch (Exception e) {
            System.out.println("Exepcion en PersistenciaParametros.empleadosParametros" + e);
            return null;
        }
    }
    
    public void borrarParametros(BigInteger secParametrosEstructuras) {
        try {
            Query query = em.createQuery("DELETE FROM Parametros p WHERE p.parametroestructura.secuencia = :secParametrosEstructuras");
            query.setParameter("secParametrosEstructuras", secParametrosEstructuras);
            query.executeUpdate();
        } catch (Exception e) {
            System.out.println("PersistenciaParametros.borrarParametros. ");
        }
    }
}
