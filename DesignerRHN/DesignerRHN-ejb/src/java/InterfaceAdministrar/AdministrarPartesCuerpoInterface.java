/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.PartesCuerpo;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarPartesCuerpoInterface {

    public void modificarPartesCuerpo(List<PartesCuerpo> listPartesCuerpoModificada);

    public void borrarPartesCuerpo(PartesCuerpo elementosCausasAccidentes);

    public void crearPartesCuerpo(PartesCuerpo elementosCausasAccidentes);

    public List<PartesCuerpo> mostrarPartesCuerpo();

    public PartesCuerpo mostrarParteCuerpo(BigInteger secElementosCausasAccidentes);

    public BigDecimal verificarSoAccidentesMedicos(BigInteger secuenciaElementosCausasAccidentes);

    public BigDecimal verificarBorradoDetallesExamenes(BigInteger secuenciaElementosCausasAccidentes);

    public BigDecimal verificarBorradoSoDetallesRevisiones(BigInteger secuenciaElementosCausasAccidentes);
}
