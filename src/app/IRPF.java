package app;

import java.util.ArrayList;
import java.util.List;

public class IRPF {
    private List<Rendimento> rendimentos;
    private List<Dependente> dependentes;
    private List<Deducao> deducoes;
    private float totalRendimentos;
    private float totalContribuicaoPrevidenciaria;
    private float totalPensaoAlimenticia;
    private float impostoTotal;
    private float baseDeCalculo;
    private float[] impostosPorFaixa;
    private float aliquotaEfetiva;
    private int numContribuicaoPrevidenciaria;
    private String[] nomesDependentes;
    private String[] nomesDeducoes;
    private float[] valoresDeducoes;
    private String[] parentescosDependentes;
    private int numDependentes;
    public static final boolean TRIBUTAVEL = true;
    public static final boolean NAOTRIBUTAVEL = false;

    public IRPF() {
        this.rendimentos = new ArrayList<>();
        this.dependentes = new ArrayList<>();
        this.deducoes = new ArrayList<>();
        this.totalRendimentos = 0f;
        this.totalContribuicaoPrevidenciaria = 0f;
        this.totalPensaoAlimenticia = 0f;
        this.impostoTotal = 0f;
        this.baseDeCalculo = 0f;
        this.impostosPorFaixa = new float[5];
        this.aliquotaEfetiva = 0f;
        numContribuicaoPrevidenciaria = 0;
        nomesDependentes = new String[0];
        nomesDeducoes = new String[0];
        valoresDeducoes = new float[0];
        parentescosDependentes = new String[0];
        numDependentes = 0;
    }

    // Rendimento
    public void criarRendimento(String nome, boolean tributavel, float valor) {
        Rendimento rendimento = new Rendimento(nome, tributavel, valor);
        rendimentos.add(rendimento);
        totalRendimentos += valor;
    }

    public int getNumRendimentos() {
        return rendimentos.size();
    }

    public float getTotalRendimentos() {
        return totalRendimentos;
    }

    public float getTotalRendimentosTributaveis() {
        float total = 0;
        for (Rendimento r : rendimentos) {
            if (r.isTributavel()) {
                total += r.getValor();
            }
        }
        return total;
    }

    // Dependente
    public void cadastrarDependente(String nome, String parentesco) {
        Dependente dependente = new Dependente(nome, parentesco);
        dependentes.add(dependente);
        numDependentes++;
    }


    private String[] novaDependencia(String nomeOuParentesco, String[] listaDeDependencia){
        int totalDependentes = getNumDependentes();
        String[] temp = new String[totalDependentes + 1];
        for (int i = 0; i < totalDependentes; i++) {
            temp[i] = listaDeDependencia[i];
        }
        temp[totalDependentes] = nomeOuParentesco;

        return temp;
    }

    public int getNumDependentes() {
        return dependentes.size();
    }

    public String getDependente(String nome) {
        for (Dependente d : dependentes) {
            if (d.getNome().contains(nome)) {
                return d.getNome();
            }
        }
        return null;
    }

    public String getParentesco(String dependente) {
        for (Dependente d : dependentes) {
            if (d.getNome().equalsIgnoreCase(dependente)) {
                return d.getParentesco();
            }
        }
        return null;
    }

    // Deduções
    public void cadastrarDeducaoIntegral(String nome, float valorDeducao) {
        String temp[] = new String[nomesDeducoes.length + 1];
        for (int i = 0; i < nomesDeducoes.length; i++) {
            temp[i] = nomesDeducoes[i];
        }
        temp[nomesDeducoes.length] = nome;
        nomesDeducoes = temp;

        float temp2[] = new float[valoresDeducoes.length + 1];
        for (int i = 0; i < valoresDeducoes.length; i++) {
            temp2[i] = valoresDeducoes[i];
        }
        temp2[valoresDeducoes.length] = valorDeducao;
        valoresDeducoes = temp2;
    }

    public float getDeducao() {
        float total = 0;
        for (Dependente d : dependentes) {
            total += 189.59f;
        }
        total += totalContribuicaoPrevidenciaria;

        return total;
    }

    public float getTotalOutrasDeducoes() {
        float soma = 0;
        for (float f : valoresDeducoes) {
            soma += f;
        }
        return soma;
    }

    public float getDeducaoTotal() {
        float total = 0;
        total += dependentes.size() * 189.59f;
        total += totalContribuicaoPrevidenciaria;
        total += totalPensaoAlimenticia;
        total += getTotalOutrasDeducoes();
        return total;
    }

    // Contribuições
    public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
        numContribuicaoPrevidenciaria++;
        totalContribuicaoPrevidenciaria += contribuicao;
    }

    public void cadastrarPensaoAlimenticia(String dependente, float valor) {
        String parentesco = getParentesco(dependente);
        if (parentesco != null && (parentesco.toLowerCase().contains("filh") || parentesco.toLowerCase().contains("alimentand"))) {
            totalPensaoAlimenticia += valor;
        }
    }

    // Cálculo do Imposto
    public void calcularBaseCalculo() {
        baseDeCalculo = getTotalRendimentosTributaveis() - getDeducaoTotal();
    }

    public float getBaseDeCalculo() {
        calcularBaseCalculo();
        return baseDeCalculo;
    }

    public void calcularImpostosPorFaixa() {
        CalculadoraImpostoPorFaixa calculadoraImposto = new CalculadoraImpostoPorFaixa(getBaseDeCalculo());
        impostosPorFaixa = calculadoraImposto.calcularImpostosPorFaixa();
    }

    public float[] getImpostosPorFaixa() {
        return impostosPorFaixa;
    }

    public void calcularImpostoTotal() {
        impostoTotal = 0;
        for (float f : impostosPorFaixa) {
            impostoTotal += f;
        }
    }

    public float getImpostoTotal() {
        return impostoTotal;
    }

    public void calcularAliquotaEfetiva(float rendimentosTributaveis, float impostoDevido) {
        if (rendimentosTributaveis == 0) {
            aliquotaEfetiva = 0; // Evitar divisão por zero
        } else {
            aliquotaEfetiva = (impostoDevido / rendimentosTributaveis) * 100;
        }
    }

    public float getAliquotaEfetiva() {
        return aliquotaEfetiva;
    }

    public int getNumContribuicoesPrevidenciarias() {
        return numContribuicaoPrevidenciaria;
    }

    public float getTotalContribuicoesPrevidenciarias() {
        return totalContribuicaoPrevidenciaria;
    }

    public float getDeducao(String nome) {
        for (int i = 0; i < nomesDeducoes.length; i++) {
            if (nomesDeducoes[i].toLowerCase().contains(nome.toLowerCase()))
                return valoresDeducoes[i];
        }
        return 0;
    }

    public String getOutrasDeducoes(String nome) {
        for (String d : nomesDeducoes) {
            if (d.toLowerCase().contains(nome.toLowerCase()))
                return d;
        }
        return null;
    }

    public float getTotalPensaoAlimenticia() {
        return totalPensaoAlimenticia;
    }
}
