/**
 * Documentación a cargo de Hugo David Sin Gutiérrez
 */
package Persistencia;

import Entidades.ActualUsuario;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
/**
 * Clase Stateless. <br>
 * Clase encargada de realizar operaciones sobre la tabla 'ActualUsuario' de la base de datos.
 * @author betelgeuse
 */
@Stateless
public class PersistenciaActualUsuario implements PersistenciaActualUsuarioInterface {
    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public ActualUsuario actualUsuarioBD() {
        try {
            Query query = em.createQuery("SELECT au FROM ActualUsuario au");
            ActualUsuario actualUsuario;
            actualUsuario = (ActualUsuario) query.getSingleResult();
            return actualUsuario;
        } catch (Exception e) {
            System.out.println("No se pudo encontrar informacion => " + e);
            return null;
        }
    }
    
    @Override
    public String actualAliasBD() {
        try {
            String sqlQuery = "SELECT au.ALIAS FROM ActualUsuario au";
            Query query = em.createNativeQuery(sqlQuery);
            String alias;
            alias = (String) query.getSingleResult();
            return alias;
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el usuario :" + e);
            return null;
        }
    }
    
    public String actualAliasBD_EM(EntityManager emg) {
        try {
            String sqlQuery = "SELECT au.ALIAS FROM ActualUsuario au";
            Query query = emg.createNativeQuery(sqlQuery);
            String alias;
            alias = (String) query.getSingleResult();
            return alias;
        } catch (Exception e) {
            System.out.println("No se pudo encontrar el usuario PersistenciaActualUsuario.actualAliasBD:" + e);
            return null;
        }
    }
}
