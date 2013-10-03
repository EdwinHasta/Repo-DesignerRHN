package Persistencia;

import Entidades.ActualUsuario;
import InterfacePersistencia.PersistenciaActualUsuarioInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaActualUsuario implements PersistenciaActualUsuarioInterface {

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
            System.out.println("No se pudo encontrar el usuario :" + e);
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
}
