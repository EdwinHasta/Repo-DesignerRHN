/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Conceptos;
import Entidades.Empresas;
import Entidades.Terceros;
import Entidades.Unidades;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author Administrator
 */
public interface AdministrarConceptosInterface {

    public List<Conceptos> conceptosEmpresa(BigInteger secEmpresa);
    public List<Conceptos> conceptosEmpresaAtivos_Inactivos(BigInteger secEmpresa, String estado);
    public List<Empresas> listadoEmpresas();
    public List<Unidades> lovUnidades();
    public List<Terceros> lovTerceros(BigInteger secEmpresa);
    public void modificar(List<Conceptos> listConceptosModificados);
    public void borrar(Conceptos concepto);
    public void crear(Conceptos conceptos);
    public List<Conceptos> conceptosEmpresaSinPasivos(BigInteger secEmpresa);
    public void clonarConcepto(BigInteger secConceptoOrigen, BigInteger codigoConceptoNuevo, String descripcionConceptoNuevo);
}
