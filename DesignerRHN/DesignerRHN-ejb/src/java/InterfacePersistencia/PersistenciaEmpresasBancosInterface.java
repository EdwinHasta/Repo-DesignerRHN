/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.EmpresasBancos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaEmpresasBancosInterface {

    public void crear(EmpresasBancos empresasBancos);

    public void editar(EmpresasBancos empresasBancos);

    public void borrar(EmpresasBancos empresasBancos);

    public List<EmpresasBancos> consultarEmpresasBancos();

    public EmpresasBancos consultarFirmaReporte(BigInteger secuencia);
}
