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
public class TesteTotalImposto {
	// Parâmetros de entrada
	private float rendimentosTributaveis;
	private float deducoes;
	private float impostoEsperado;

	// Construtor para o JUnit receber os parâmetros
	public TesteTotalImposto(float rendimentosTributaveis, float deducoes, float impostoEsperado) {
		this.rendimentosTributaveis = rendimentosTributaveis;
		this.deducoes = deducoes;
		this.impostoEsperado = impostoEsperado;
	}

	// Definir os parâmetros para os testes
	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{10000.00f, 3189.59f, 976.84f}, // Caso fornecido
				{15000.00f, 3189.59f, 2351.86f}, // Rendimentos mais altos
				{8000.00f, 3189.59f, 426.85f}, // Rendimentos mais baixos
				{12000.00f, 3189.59f, 1526.78f}, // Caso intermediário
				{20000.00f, 3189.59f, 3726.85f}, // Caso de grande rendimento
				{5000.00f, 3189.59f, 0.00f}, // Caso com rendimento baixo
				{10000.00f, 0.00f, 1853.99f} // Sem deduções
		});
	}


	// Teste que valida o imposto calculado
	@Test
	public void testCalculoImposto() {

		IRPF irpf = new IRPF();

		irpf.calcularBaseCalculo(rendimentosTributaveis, deducoes);
		irpf.calcularImpostosPorFaixa();
		irpf.calcularImpostoTotal();

		float totalImposto = irpf.getImpostoTotal();

		assertEquals(impostoEsperado, totalImposto, 0.1);
	}
}