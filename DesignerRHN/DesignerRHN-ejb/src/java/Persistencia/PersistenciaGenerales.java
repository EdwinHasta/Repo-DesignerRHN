package Persistencia;

import Entidades.Generales;
import InterfacePersistencia.PersistenciaGeneralesInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaGenerales implements PersistenciaGeneralesInterface {

    /**
     * Atributo EntityManager. Representa la comunicación con la base de datos.
     */
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public Generales obtenerRutas() {
        try {
            Query query = em.createQuery("SELECT g FROM Generales g");
            List<Generales> listaGenerales = query.getResultList();
            if (listaGenerales != null && !listaGenerales.isEmpty()) {
                return listaGenerales.get(0);
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaGenerales.obtenerRutas " + e);
            return null;
        }
    }
}
