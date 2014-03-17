/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Bancos;
import Entidades.Ciudades;
import Entidades.Empresas;
import Entidades.EmpresasBancos;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEmpresasBancosInterface {

    public void modificarEmpresasBancos(List<EmpresasBancos> listaEmpresasBancos);

    public void borrarEmpresasBancos(List<EmpresasBancos> listaEmpresasBancos);

    public void crearEmpresasBancos(List<EmpresasBancos> listaEmpresasBancos);

    public List<EmpresasBancos> consultarEmpresasBancos();

    public EmpresasBancos consultarTipoIndicador(BigInteger secMotivoDemanda);

    public List<Bancos> consultarLOVBancos();

    public List<Ciudades> consultarLOVCiudades();

    public List<Empresas> consultarLOVEmpresas();
}
