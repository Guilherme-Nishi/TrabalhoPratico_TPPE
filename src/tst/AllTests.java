package tst;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
				TesteCalcularAliquotaEfetiva.class,
				TesteCadastrarDependente.class,
				TesteRendimentos.class,
				TesteCalculosDeducoesDependentes.class, 
				TesteCadastroContribuicaoPrevidenciaria.class, 
				TesteCadastroPensaoAlimenticia.class, 
				TesteCadastroOutrasDeducoes.class,
				TesteBaseCalculo.class,
				TesteImpostoPorFaixa.class,
				TesteTotalImposto.class})

public class AllTests {

}
