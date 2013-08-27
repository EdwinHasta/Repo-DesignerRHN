package Persistencia;

import Entidades.UbicacionesGeograficas;
import InterfacePersistencia.PersistenciaUbicacionesGeograficasInterface;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaUbicacionesGeograficas implements PersistenciaUbicacionesGeograficasInterface{
    
    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<UbicacionesGeograficas> buscarUbicacionesGeograficas() {
        try {
            Query query = em.createQuery("SELECT u FROM UbicacionesGeograficas u");

            List<UbicacionesGeograficas> ubicacionesGeograficas = query.getResultList();
            return ubicacionesGeograficas;
        } catch (Exception e) {
            System.out.println("Error en Persistencia Ubicaciones Geograficas " + e);
            return null;
        }
    }
}
