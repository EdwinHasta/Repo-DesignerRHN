/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package InterfaceAdministrar;

import Entidades.ClasesAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarClasesAccidentesInterface {
    public void modificarClasesAccidentes(List<ClasesAccidentes> listClasesAccidentesModificada);
    public void borrarClasesAccidentes(ClasesAccidentes tiposAccidentes);
    public void crearClasesAccidentes(ClasesAccidentes TiposAccidentes);
    public List<ClasesAccidentes> mostrarClasesAccidentes();
    public ClasesAccidentes mostrarClaseAccidente(BigInteger secClasesAccidentes);
    public BigInteger verificarSoAccidentesMedicos(BigInteger secuenciaElementos);
}
