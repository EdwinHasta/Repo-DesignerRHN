/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistencia;

import Entidades.SoausentismosProrrogas;
import InterfacePersistencia.PersistenciaSoausentismosProrrogasInterface;
import java.math.BigInteger;
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
public class PersistenciaSoausentismosProrrogas implements PersistenciaSoausentismosProrrogasInterface{

    @PersistenceContext(unitName = "DesignerRHN-ejbPU")
    private EntityManager em;

    public List<SoausentismosProrrogas> prorrogas(BigInteger secEmpleado, BigInteger secuenciaCausa, BigInteger secuenciaAusentismo) {
        try {
            String sqlQuery = "SELECT soa.secuencia, soa.fechafinaus+1 finsiguiente, ca.DESCRIPCION,\n"
                    + "(select dc.CODIGO from DIAGNOSTICOSCATEGORIAS dc where dc.secuencia = soa.diagnosticocategoria) codigodiagnostico,\n"
                    + "(select dc.DESCRIPCION from DIAGNOSTICOSCATEGORIAS dc where dc.secuencia = soa.diagnosticocategoria) descripciondiagnostico,\n"
                    + "soa.fecha, soa.fechafinaus, soa.dias, soa.horas, \n"
                    + "nvl(soa.numerocertificado,'Falta # Certificado')||': '||soa.fecha||'->'||soa.fechafinaus numerocertificado\n"
                    + "FROM CAUSASAUSENTISMOS ca, SOAUSENTISMOS soa\n"
                    + "WHERE ca.secuencia = soa.causa\n"
                    + "and soa.secuencia <> nvl( ? ,0)\n"
                    + "and soa.empleado = ?\n"
                    + "and soa.causa = ?\n"
                    + "ORDER BY soa.FECHA DESC";
            Query query = em.createNativeQuery(sqlQuery, "prorrogas");
            query.setParameter(1, secuenciaAusentismo);
            query.setParameter(2, secEmpleado);
            query.setParameter(3, secuenciaCausa);
            List<SoausentismosProrrogas> prorrogas = query.getResultList();
            return prorrogas;

        } catch (Exception e) {
            System.out.println("Error: (prorrogas)" + e);
            return null;
        }
    }
}
