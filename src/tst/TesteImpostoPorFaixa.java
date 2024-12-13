package tst;

import app.IRPF;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertArrayEquals;

@RunWith(Parameterized.class)
public class TesteImpostoPorFaixa {

	private float baseCalculo;
	private float[] impostosEsperados;

	private IRPF irpf;

	public TesteImpostoPorFaixa(float baseCalculo, float[] impostosEsperados) {
		this.baseCalculo = baseCalculo;
		this.impostosEsperados = impostosEsperados;
	}

	@Before
	public void setup() {
		irpf = new IRPF();
	}

	@Parameters
	public static Collection<Object[]> getParameters(){
		return Arrays.asList(new Object[][] {
			{6810.41f, new float[] {0f, 42.55f, 138.66f, 205.56f, 590.07f}},
			{4000f, new float[] {0f, 42.55f, 138.66f, 56.01f, 0f}},
			{3150f, new float[] {0f, 42.55f, 48.50f, 0f, 0f}},
			{2500f, new float[] {0f, 18.06f, 0f, 0f, 0f}},
			{2000f, new float[] {0f, 0f, 0f, 0f, 0f}},
		});
	}

	@Test
	public void testeImpostoPorFaixa() {
		irpf.criarRendimento("Base de Calculo", true, baseCalculo);
		irpf.calcularImpostosPorFaixa();
		assertArrayEquals(irpf.getImpostosPorFaixa(), impostosEsperados, 0.01f);
	}
}
