package org.example.models;

import org.example.exceptions.DineroInsuficienteException;

import java.math.BigDecimal;
import java.util.Objects;

public class Cuenta {

    private String persona;
    private BigDecimal saldo;
    private Banco banco;

    public Cuenta(String persona, BigDecimal saldo) {
        this.saldo = saldo;
        this.persona=persona;
    }

    public String getPersona(){
        return persona;
    }

    public BigDecimal getSaldo(){
        return saldo;
    }

    public void setPersona(String persona){
        this.persona=persona;
    }

    public void setSaldo(BigDecimal saldo){
        this.saldo = saldo;
    }

    public Banco getBanco() {
        return banco;
    }

    public void setBanco(Banco banco) {
        this.banco = banco;
    }

    public void debito(BigDecimal monto){
        System.out.println("Saldo antes del débito: " + this.saldo);
        BigDecimal nuevoSaldo = this.saldo.subtract(monto);
        System.out.println("Nuevo saldo después del débito: " + nuevoSaldo);

        if(nuevoSaldo.compareTo(BigDecimal.ZERO) < 0){
            System.out.println("Lanzando DineroInsuficienteException");
            throw new DineroInsuficienteException("Dinero Insuficiente");
        }
        this.saldo = nuevoSaldo;
    }


    public void credito(BigDecimal monto){
        this.saldo = this.saldo.add(monto);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cuenta cuenta = (Cuenta) o;
        return Objects.equals(persona, cuenta.persona) && Objects.equals(saldo, cuenta.saldo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(persona, saldo);
    }
}
