/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfaceAdministrar;

import Entidades.Enfermedades;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface AdministrarEnfermedadesInterface {

    public void modificarEnfermedades(List<Enfermedades> listDeportesModificadas);

    public void borrarEnfermedades(Enfermedades deportes);

    public void crearEnfermedades(Enfermedades deportes);

    public List<Enfermedades> mostrarEnfermedades();

    public Enfermedades mostrarEnfermedad(BigInteger secDeportes);

    public BigInteger contadorAusentimos(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorDetallesLicencias(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorEnfermedadesPadecidas(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorSoausentismos(BigInteger secuenciaTiposAuxilios);

    public BigInteger contadorSorevisionessSistemas(BigInteger secuenciaTiposAuxilios);
}
