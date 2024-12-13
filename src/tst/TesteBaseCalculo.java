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
public class TesteBaseCalculo {
	
	private Object[][] rendimentos;
	private Object[][] dependentes;
	private Object[] contribPrevidenciaria;
	private Object[][] pensaoAlimenticia;
	private float resultadoEsperado;
	
	private IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
	}
	
	@Parameters
	public static Collection<Object[]> getParameters(){
		Object[][] parametros = new Object[][] {
			/* TESTE 1 - NORMAL */
			{
				new Object[][] { // Rendimentos
					{"Salario", true, 8000f},
					{"Aluguel", true, 2000f},
					{"Bolsa", false, 1500f},
				}, 
				new Object[][] { // Dependentes
					{"Ana", "Filha"},
				},
				new Object[] { // Contribuição previdenciaria
					500f, 1000f
				},
				new Object[][] { // Pensão alimenticia
					{"Ana", 1500f}
				},
				6810.41f  // Base de calculo esperado
			},
			/* TESTE 2 - Normal com mais dependentes */
			{
				new Object[][] { // Rendimentos
					{"Salario", true, 8000f},
					{"Aluguel", true, 2000f},
					{"Bolsa", false, 1500f},
				}, 
				new Object[][] { // Dependentes
					{"Ana", "Filha"},
					{"Julia", "Alimentanda"},
					{"Marcos", "alimentando"},
				},
				new Object[] { // Contribuição previdenciaria
					500f, 1000f
				},
				new Object[][] { // Pensão alimenticia
					{"Ana", 1500f},
					{"Julia", 1500f},
					{"Marcos", 800f},
				},
				4131.23f  // Base de calculo esperado
			},
			/* TESTE 3 - Novo dependente mas sem pensão */
			{
				new Object[][] { // Rendimentos
					{"Salario", true, 8000f},
					{"Aluguel", true, 2000f},
					{"Bolsa", false, 1500f},
				}, 
				new Object[][] { // Dependentes
					{"Ana", "Filha"},
					{"Julia", "Alimentanda"},
					{"Marcos", "alimentando"},
					{"Rose", "mãe"}, // é considerada como dependente
				},
				new Object[] { // Contribuição previdenciaria
					500f, 1000f
				},
				new Object[][] { // Pensão alimenticia
					{"Ana", 1500f},
					{"Julia", 1500f},
					{"Marcos", 800f},
					{"Rose", 1000f}, // valor da pensão não entra por ser 'mãe'
				},
				3941.64f  // Base de calculo esperado
			},
				
		};
		
		return Arrays.asList(parametros);
	}
	
	public TesteBaseCalculo(
			Object[][] rendimentos,
			Object[][] dependentes,
			Object[] contribPrevidenciaria,
			Object[][] pensaoAlimenticia,
			float resultadoEsperado) 
	{
		this.rendimentos = rendimentos;
		this.dependentes = dependentes;
		this.contribPrevidenciaria = contribPrevidenciaria;
		this.pensaoAlimenticia = pensaoAlimenticia;
		this.resultadoEsperado = resultadoEsperado;
	}

	@Test
	public void testeBaseCalculo() {
		for(Object[] r : rendimentos) {
			irpf.criarRendimento((String)r[0], (boolean)r[1], (float)r[2]);
		}
		
		for(Object[] d : dependentes) {
			irpf.cadastrarDependente((String)d[0], (String)d[1]);
		}
		
		for(Object ct : contribPrevidenciaria) {
			irpf.cadastrarContribuicaoPrevidenciaria((float)ct);
		}
		
		for(Object[] p : pensaoAlimenticia) {
			irpf.cadastrarPensaoAlimenticia((String)p[0], (float)p[1]);
		}
		
		assertEquals(resultadoEsperado, irpf.calcularBaseCalculo(), 0.001f);
	}

}
