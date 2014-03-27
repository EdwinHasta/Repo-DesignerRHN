/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InterfacePersistencia;

import Entidades.Declarantes;
import java.math.BigInteger;
import java.util.List;

/**
 *
 * @author user
 */
public interface PersistenciaDeclarantesInterface {

    public void crear(Declarantes declarantes);

    public void editar(Declarantes declarantes);

    public void borrar(Declarantes declarantes);

    public List<Declarantes> buscarDeclarantes();

    public Declarantes buscarDeclaranteSecuencia(BigInteger secuencia);

    public List<Declarantes> buscarDeclarantesPersona(BigInteger secPersona);

}
