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
	
	private Object[][] rendimentos;
	private Object[][] dependentes;
	private Object[] contribPrevidenciaria;
	private Object[][] pensaoAlimenticia;
	private Object[][] outrasDeducoes;
	private float resultadoEsperado;
	
	private IRPF irpf;
	
	@Before
	public void setup() {
		irpf = new IRPF();
	}

	public TesteTotalImposto(
			Object[][] rendimentos,
			Object[][] dependentes,
			Object[] contribPrevidenciaria,
			Object[][] pensaoAlimenticia,
			Object[][] outrasDeducoes,
			float resultadoEsperado) 
	{
		this.rendimentos = rendimentos;
		this.dependentes = dependentes;
		this.contribPrevidenciaria = contribPrevidenciaria;
		this.pensaoAlimenticia = pensaoAlimenticia;
		this.outrasDeducoes = outrasDeducoes;
		this.resultadoEsperado = resultadoEsperado;
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
				new Object[][] { // Outras deduções
					
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
				new Object[][] { // Outras deduções
					
				},
				4131.23f  // Base de calculo esperado
			},
			/* TESTE 3 - Novo dependente mas sem pensão válida */
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
				new Object[][] { // Outras deduções
					
				},
				3941.64f  // Base de calculo esperado
			},
			/* TESTE 4 - Sem cadastro de pensão alimenticia */
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
					
				},
				new Object[][] { // Outras deduções
					
				},
				7741.64f  // Base de calculo esperado
			},
			/* TESTE 5 - Sem cadastro de dependentes */
			{
				new Object[][] { // Rendimentos
					{"Salario", true, 8000f},
					{"Aluguel", true, 2000f},
					{"Bolsa", false, 1500f},
				}, 
				new Object[][] { // Dependentes
					
				},
				new Object[] { // Contribuição previdenciaria
					500f, 1000f
				},
				new Object[][] { // Pensão alimenticia
					
				},
				new Object[][] { // Outras deduções
					
				},
				8500f  // Base de calculo esperado
			},
			/* TESTE 6 - Sem cadastro de contribuição previdenciaria */
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
					
				},
				new Object[][] { // Pensão alimenticia
					{"Ana", 1500f},
					{"Julia", 1500f},
					{"Marcos", 800f},
					{"Rose", 1000f}, // valor da pensão não entra por ser 'mãe'
				},
				new Object[][] { // Outras deduções
					
				},
				5441.64f  // Base de calculo esperado
			},
			/* TESTE 7 - Cadastro de nova dedução */
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
					{"Julia", 500f},
					{"Marcos", 800f},
				},
				new Object[][] { // Outras deduções
					{"Despesas médicas", 1000f},
					{"Educação", 800f},
				},
				3141.64f  // Base de calculo esperado
			},
		};
		
		return Arrays.asList(parametros);
	}
	
	@Test
	public void testeTotalImposto() {
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

		for(Object[] o : outrasDeducoes) {
			irpf.cadastrarDeducaoIntegral((String)o[0], (float)o[1]);
		}

		irpf.calcularImpostoTotal();

		assertEquals(resultadoEsperado, irpf.getImpostoTotal(), 0.01f);
	}
}
