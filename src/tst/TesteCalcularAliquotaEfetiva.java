package tst;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import app.IRPF;


@RunWith(Parameterized.class)
public class TesteCalcularAliquotaEfetiva {



    private final float rendimentosTributaveis;
    private final float impostoDevido;
    private final float aliquotaEsperada;
    IRPF irpf;

    public TesteCalcularAliquotaEfetiva(float rendimentosTributaveis, float impostoDevido, float aliquotaEsperada) {
        this.rendimentosTributaveis = rendimentosTributaveis;
        this.impostoDevido = impostoDevido;
        this.aliquotaEsperada = aliquotaEsperada;
    }

    @Before
    public void setup() {
        irpf = new IRPF();
    }

    @Parameters
    public static Collection<Object[]> parametros() {
        return Arrays.asList(new Object[][]{
                {10000.00f, 987.88f, 9.8788f},
                {15000.00f, 2000.00f, 13.3333f},
                {8000.00f, 500.00f, 6.25f},
                {20000.00f, 4000.00f, 20.0f},
                {0.00f, 0.00f, 0.0f} // Caso com rendimentos e imposto zero
        });
    }

    @Test
    public void testCalcularAliquotaEfetiva() {
        // Calcula a alíquota efetiva diretamente
        irpf.calcularAliquotaEfetiva(rendimentosTributaveis, impostoDevido);
        float aliquotaEfetiva = irpf.getAliquotaEfetiva();
        assertEquals(aliquotaEsperada, aliquotaEfetiva, 0.0001);
    }

}
