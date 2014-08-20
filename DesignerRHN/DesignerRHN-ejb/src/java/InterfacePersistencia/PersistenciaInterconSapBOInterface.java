/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfacePersistencia;

import Entidades.InterconSapBO;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Administrador
 */
public interface PersistenciaInterconSapBOInterface {
    
    public void crear(EntityManager em, InterconSapBO interconSapBO);
    
    public void editar(EntityManager em, InterconSapBO interconSapBO);
    
    public void borrar(EntityManager em, InterconSapBO interconSapBO);
    
    public InterconSapBO buscarInterconSAPBOSecuencia(EntityManager em, BigInteger secuencia);
    
    public List<InterconSapBO> buscarInterconSAPBOParametroContable(EntityManager em, Date fechaInicial, Date fechaFinal) ;
}
