package Persistencia;

import Entidades.HvEntrevistas;
import InterfacePersistencia.PersistenciaHvEntrevistasInterface;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PersistenciaHvEntrevistas implements PersistenciaHvEntrevistasInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public List<HvEntrevistas> entrevistasPersona(BigInteger secuenciaHV) {
        try {
            Query query = em.createQuery("SELECT COUNT(hve) FROM HvEntrevistas hve WHERE hve.hojadevida.secuencia = :secuenciaHV");
            query.setParameter("secuenciaHV", secuenciaHV);
            Long resultado = (Long) query.getSingleResult();
            if (resultado > 0) {
                Query queryFinal = em.createQuery("SELECT hve FROM HvEntrevistas hve WHERE hve.hojadevida.secuencia = :secuenciaHV and hve.fecha = (SELECT MAX(hven.fecha) FROM HvEntrevistas hven WHERE hven.hojadevida.secuencia = :secuenciaHV)");
                queryFinal.setParameter("secuenciaHV", secuenciaHV);
                List<HvEntrevistas> listaHvEntrevistas = queryFinal.getResultList();
                return listaHvEntrevistas;
            }
            return null;
        } catch (Exception e) {
            System.out.println("Error PersistenciaHvEntrevistas.entrevistasPersona" + e);
            return null;
        }
    }
}