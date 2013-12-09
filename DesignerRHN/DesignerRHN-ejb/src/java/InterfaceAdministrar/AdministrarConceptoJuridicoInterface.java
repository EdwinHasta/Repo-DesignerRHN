/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.ConceptosJuridicos;
import Entidades.Empresas;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface AdministrarConceptoJuridicoInterface {
    
    public List<ConceptosJuridicos> listConceptosJuridicosPorEmpresa(BigInteger secuencia);
    public void crearConceptosJuridicos(List<ConceptosJuridicos> listCJ);
    public void editarConceptosJuridicos(List<ConceptosJuridicos> listCJ);
    public void borrarConceptosJuridicos(List<ConceptosJuridicos> listCJ);
    public List<Empresas> listEmpresas();
    
}
