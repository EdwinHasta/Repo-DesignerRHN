package Persistencia;

import Entidades.Novedades;
import InterfacePersistencia.PersistenciaNovedadesInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaNovedades implements PersistenciaNovedadesInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<Novedades> novedadesParaReversar(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("SELECT n FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            List<Novedades> listNovedades = query.getResultList();
            return listNovedades;
        } catch (Exception e) {
            System.out.println("Error: (novedadesParaReversar)" + e);
            return null;
        }
    }

    public int reversarNovedades(BigInteger usuarioBD, String documentoSoporte) {
        try {
            Query query = em.createQuery("DELETE FROM Novedades n WHERE n.usuarioreporta.secuencia = :usuarioBD AND n.documentosoporte = :documentoSoporte");
            query.setParameter("usuarioBD", usuarioBD);
            query.setParameter("documentoSoporte", documentoSoporte);
            int rows = query.executeUpdate();
            return rows;
        } catch (Exception e) {
            System.out.println("No se pudo borrar el registro. (reversarNovedades)" + e);
            return 0;
        }
    }
}
