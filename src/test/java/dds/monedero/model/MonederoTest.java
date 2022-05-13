package dds.monedero.model;

import dds.monedero.exceptions.MaximaCantidadDepositosException;
import dds.monedero.exceptions.MaximoExtraccionDiarioException;
import dds.monedero.exceptions.MontoInvalidoException;
import dds.monedero.exceptions.MontoInvalidoException;
import dds.monedero.exceptions.SaldoMenorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonederoTest {
  private Cuenta cuenta;

  @BeforeEach
  void init() {
    cuenta = new Cuenta(0);
  }

  @Test
  void Despositar() {
    cuenta.poner(1500);
    assertEquals(cuenta.getSaldo(),1500);
  }

  @Test
  void DepositarMontoNegativoFalla() {
    assertThrows(MontoInvalidoException.class, () -> cuenta.poner(-1500));
  }

  @Test
  void Depositar() {
    assertThrows(MontoInvalidoException.class, () -> cuenta.poner(0));
  }

  @Test
  void ExtraerNegativoFalla() {
    assertThrows(MontoInvalidoException.class, () -> cuenta.sacar(-1000));
  }

  @Test
  void DepositarTresVecesSaldo() {
    cuenta.poner(1500);
    cuenta.poner(200);
    cuenta.poner(300);
    assertEquals(cuenta.getSaldo(), 2000);
  }


  @Test
  void MasDeTresDepositosFalla() {
    assertThrows(MaximaCantidadDepositosException.class, () -> {
      cuenta.poner(1500);
      cuenta.poner(456);
      cuenta.poner(1900);
      cuenta.poner(245);
    });
  }

  @Test
  void ExtraerMasQueElSaldoFalla() {
    assertThrows(SaldoMenorException.class, () -> {
      cuenta.setSaldo(90);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMasDe1000Falla() {
    assertThrows(MaximoExtraccionDiarioException.class, () -> {
      cuenta.setSaldo(5000);
      cuenta.sacar(1001);
    });
  }

  @Test
  public void ExtraerMontoNegativoFalla() {
    assertThrows(MontoInvalidoException.class, () -> cuenta.sacar(-500));
  }

}