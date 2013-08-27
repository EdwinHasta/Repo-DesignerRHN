package Persistencia;

import Entidades.ObjetosDB;
import InterfacePersistencia.PersistenciaObjetosDBInterface;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaObjetosDB implements PersistenciaObjetosDBInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public ObjetosDB obtenerObjetoTabla(String nombreTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(obj) FROM ObjetosDB obj WHERE obj.nombre = :nombreTabla");
            query.setParameter("nombreTabla", nombreTabla);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query query2 = em.createQuery("SELECT obj FROM ObjetosDB obj WHERE obj.nombre = :nombreTabla");
                query2.setParameter("nombreTabla", nombreTabla);
                ObjetosDB objetosDB = (ObjetosDB) query2.getSingleResult();
                return objetosDB;
            } else {
                System.out.println("No existe la tabla en objetosDB");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en verificarRastroTabla " + e);
            return null;
        }
    }
}
