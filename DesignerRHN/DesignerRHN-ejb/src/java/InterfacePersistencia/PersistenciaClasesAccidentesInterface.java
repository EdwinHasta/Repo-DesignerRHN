/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.ClasesAccidentes;
import java.math.BigInteger;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface PersistenciaClasesAccidentesInterface {

    public void crear(ClasesAccidentes clasesAccidentes);

    public void editar(ClasesAccidentes clasesAccidentes);

    public void borrar(ClasesAccidentes clasesAccidentes);

    public ClasesAccidentes buscarClaseAccidente(BigInteger secuenciaCA);

    public List<ClasesAccidentes> buscarClasesAccidentes();

    public BigInteger contadorSoAccidentesMedicos(BigInteger secuencia);
}
