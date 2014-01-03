/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Lesiones;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarLesionesInterface {

    public void modificarLesiones(List<Lesiones> listLesionesModificadas);

    public void borrarLesiones(Lesiones lesiones);

    public void crearLesiones(Lesiones lesiones);

    public List<Lesiones> mostrarLesiones();

    public Lesiones mostrarLesion(BigInteger secLesion);

    public BigInteger verificarBorradoDetallesLicensias(BigInteger secuenciaLesiones);

    public BigInteger verificarBorradoSoAccidentesDomesticos(BigInteger secuenciaVigenciasExamenesMedicos);
}
