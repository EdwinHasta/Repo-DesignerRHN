/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.FormasDtos;
import InterfacePersistencia.PersistenciaFormasDtosInterface;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PersistenciaFormasDtos implements PersistenciaFormasDtosInterface {

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    @Override
    public void crear(FormasDtos formasDtos) {
        try {
            em.merge(formasDtos);
        } catch (Exception e) {
            System.out.println("El formasDtos no exite o esta reservada por lo cual no puede ser modificada (formasDtos)");
        }
    }

    @Override
    public void editar(FormasDtos formasDtos) {
        try {
            em.merge(formasDtos);
        } catch (Exception e) {
            System.out.println("No se pudo modificar el formasDtos");
        }
    }

    @Override
    public void borrar(FormasDtos formasDtos) {
        try {
            em.remove(em.merge(formasDtos));
        } catch (Exception e) {
            System.out.println("El formasDtos no se ha podido eliminar");
        }
    }

    @Override
    public List<FormasDtos> formasDescuentos(BigInteger tipoEmbargo) {
        try {
            String sqlQuery = "SELECT SECUENCIA, DESCRIPCION FROM FORMASDTOS \n"
                    + "WHERE TIPO = 'EMBARGO'\n"
                    + "and nvl(tipoembargo, ?)= ?\n"
                    + "ORDER BY 2";
            Query query = em.createNativeQuery(sqlQuery, FormasDtos.class);
            query.setParameter(1, tipoEmbargo);
            query.setParameter(2, tipoEmbargo);
            List<FormasDtos> formasDtos = query.getResultList();
            List<FormasDtos> formasDtosResult = new ArrayList<FormasDtos>(formasDtos);
            return formasDtosResult;
        } catch (Exception e) {
            System.out.println("Error: (FormasDtos)" + e);
            return null;
        }
    }
}
