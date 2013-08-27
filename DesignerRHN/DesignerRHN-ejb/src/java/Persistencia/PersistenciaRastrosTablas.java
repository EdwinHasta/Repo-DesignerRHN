package Persistencia;

import InterfacePersistencia.PersistenciaRastrosTablasInterface;
import java.math.BigInteger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaRastrosTablas implements PersistenciaRastrosTablasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;
    
    public boolean verificarRastroTabla(BigInteger secObjetoTabla) {
        try {
            Query query = em.createQuery("SELECT COUNT(rt) FROM RastrosTablas rt WHERE rt.objeto.secuencia = :secObjetoTabla");
            query.setParameter("secObjetoTabla", secObjetoTabla);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                return true;
            }else{
                return false;
            }
        } catch (Exception e) {
            System.out.println("Exepcion en verificarRastroTabla " + e);
            return false;
        }
    }
}
