package app;

public class IRPF {

    public static final boolean TRIBUTAVEL = true;
    public static final boolean NAOTRIBUTAVEL = false;
    private String[] nomeRendimento;
    private boolean[] rendimentoTributavel;
    private float[] valorRendimento;
    private int numRendimentos;
    private float totalRendimentos;
    private float aliquotaEfetiva;

    private String[] nomesDependentes;
    private String[] parentescosDependentes;
    private int numDependentes;

    private int numContribuicaoPrevidenciaria;
    private float totalContribuicaoPrevidenciaria;

    private float totalPensaoAlimenticia;

    private String[] nomesDeducoes;
    private float[] valoresDeducoes;

    private float[] impostosPorFaixa;
    private float impostoTotal;

    private float baseDeCalculo;

    public IRPF() {
        nomeRendimento = new String[0];
        rendimentoTributavel = new boolean[0];
        valorRendimento = new float[0];

        nomesDependentes = new String[0];
        parentescosDependentes = new String[0];
        numDependentes = 0;

        numContribuicaoPrevidenciaria = 0;
        totalContribuicaoPrevidenciaria = 0f;

        totalPensaoAlimenticia = 0f;

        nomesDeducoes = new String[0];
        valoresDeducoes = new float[0];

        baseDeCalculo = 0f;

        impostosPorFaixa = new float[5];
        impostoTotal = 0f;
    }

    /**
     * Cadastra um rendimento na base do contribuinte, informando o nome do
     * rendimento, seu valor e se ele é tributável ou não.
     *
     * @param nome       nome do rendimento a ser cadastrado
     * @param tributavel true caso seja tributável, false caso contrário
     * @param valor      valor do rendimento a ser cadastrado
     */
    public void criarRendimento(String nome, boolean tributavel, float valor) {
        //Adicionar o nome do novo rendimento
        String[] temp = new String[nomeRendimento.length + 1];
        for (int i = 0; i < nomeRendimento.length; i++)
            temp[i] = nomeRendimento[i];
        temp[nomeRendimento.length] = nome;
        nomeRendimento = temp;

        //adicionar tributavel ou nao no vetor
        boolean[] temp2 = new boolean[rendimentoTributavel.length + 1];
        for (int i = 0; i < rendimentoTributavel.length; i++)
            temp2[i] = rendimentoTributavel[i];
        temp2[rendimentoTributavel.length] = tributavel;
        rendimentoTributavel = temp2;

        //adicionar valor rendimento ao vetor
        float[] temp3 = new float[valorRendimento.length + 1];
        for (int i = 0; i < valorRendimento.length; i++) {
            temp3[i] = valorRendimento[i];
        }
        temp3[valorRendimento.length] = valor;
        valorRendimento = temp3;

        this.numRendimentos += 1;
        this.totalRendimentos += valor;

    }

    /**
     * Retorna o número de rendimentos já cadastrados para o contribuinte
     *
     * @return numero de rendimentos
     */
    public int getNumRendimentos() {
        return numRendimentos;
    }

    /**
     * Retorna o valor total de rendimentos cadastrados para o contribuinte
     *
     * @return valor total dos rendimentos
     */
    public float getTotalRendimentos() {
        return totalRendimentos;
    }

    /**
     * Retorna o valor total de rendimentos tributáveis do contribuinte
     *
     * @return valor total dos rendimentos tributáveis
     */
    public float getTotalRendimentosTributaveis() {
        float totalRendimentosTributaveis = 0;
        for (int i = 0; i < rendimentoTributavel.length; i++) {
            if (rendimentoTributavel[i]) {
                totalRendimentosTributaveis += valorRendimento[i];
            }
        }
        return totalRendimentosTributaveis;
    }

    /**
     * Método para realizar o cadastro de um dependente, informando seu grau
     * de parentesco
     *
     * @param nome       Nome do dependente
     * @param parentesco Grau de parentesco
     */
    public void cadastrarDependente(String nome, String parentesco) {
        // adicionar dependente
        String[] temp = new String[nomesDependentes.length + 1];
        for (int i = 0; i < nomesDependentes.length; i++) {
            temp[i] = nomesDependentes[i];
        }
        temp[nomesDependentes.length] = nome;
        nomesDependentes = temp;

        String[] temp2 = new String[parentescosDependentes.length + 1];
        for (int i = 0; i < parentescosDependentes.length; i++) {
            temp2[i] = parentescosDependentes[i];
        }
        temp2[parentescosDependentes.length] = parentesco;
        parentescosDependentes = temp2;

        numDependentes++;
    }

    /**
     * Método que retorna o numero de dependentes do contribuinte
     *
     * @return numero de dependentes
     */
    public int getNumDependentes() {
        return numDependentes;
    }

    /**
     * Return o valor do total de deduções para o contribuinte
     *
     * @return valor total de deducoes
     */
    public float getDeducao() {
        float total = 0;
        for (String d : nomesDependentes) {
            total += 189.59f;
        }
        total += totalContribuicaoPrevidenciaria;

        return total;
    }

    /**
     * Cadastra um valor de contribuição previdenciária oficial
     *
     * @param contribuicao valor da contribuição previdenciária oficial
     */
    public void cadastrarContribuicaoPrevidenciaria(float contribuicao) {
        numContribuicaoPrevidenciaria++;
        totalContribuicaoPrevidenciaria += contribuicao;
    }

    /**
     * Retorna o numero total de contribuições realizadas como contribuicao
     * previdenciaria oficial
     *
     * @return numero de contribuições realizadas
     */
    public int getNumContribuicoesPrevidenciarias() {
        return numContribuicaoPrevidenciaria;
    }

    /**
     * Retorna o valor total de contribuições oficiais realizadas
     *
     * @return valor total de contribuições oficiais
     */
    public float getTotalContribuicoesPrevidenciarias() {
        return totalContribuicaoPrevidenciaria;
    }

    /**
     * Realiza busca do dependente no cadastro do contribuinte
     *
     * @param nome nome do dependente que está sendo pesquisado
     * @return nome do dependente ou null, caso nao conste na lista de dependentes
     */
    public String getDependente(String nome) {
        for (String d : nomesDependentes) {
            if (d.contains(nome))
                return d;
        }
        return null;
    }

    /**
     * Método que retorna o grau de parentesco para um dado dependente, caso ele
     * conste na lista de dependentes
     *
     * @param dependente nome do dependente
     * @return grau de parentesco, nulo caso nao exista o dependente
     */
    public String getParentesco(String dependente) {
        for (int i = 0; i < nomesDependentes.length; i++) {
            if (nomesDependentes[i].equalsIgnoreCase(dependente))
                return parentescosDependentes[i];
        }
        return null;
    }

    /**
     * Realiza o cadastro de uma pensao alimenticia para um dos dependentes do
     * contribuinte, caso ele seja um filho ou alimentando.
     *
     * @param dependente nome do dependente
     * @param valor      valor da pensao alimenticia
     */
    public void cadastrarPensaoAlimenticia(String dependente, float valor) {
        String parentesco = getParentesco(dependente);
        if (parentesco.toLowerCase().contains("filh") ||
                parentesco.toLowerCase().contains("alimentand")) {
            totalPensaoAlimenticia += valor;
        }
    }

    /**
     * Retorna o valor total pago em pensões alimentícias pelo contribuinte.
     *
     * @return valor total de pensoes alimenticias
     */
    public float getTotalPensaoAlimenticia() {
        return totalPensaoAlimenticia;
    }

    /**
     * Metodo para cadastrar deduções integrais para o contribuinte. Para cada
     * dedução é informado seu nome e valor.
     *
     * @param nome         nome da deducao
     * @param valorDeducao valor da deducao
     */
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

    /**
     * Método para pesquisar uma deducao pelo seu nome.
     *
     * @param nome do nome da deducao a ser pesquisada
     * @return nome da deducao, ou null caso na esteja cadastrada
     */
    public String getOutrasDeducoes(String nome) {
        for (String d : nomesDeducoes) {
            if (d.toLowerCase().contains(nome.toLowerCase()))
                return d;
        }
        return null;
    }

    /**
     * Obtem o valor da deducao à partir de seu nome
     *
     * @param nome nome da deducao para a qual se busca seu valor
     * @return valor da deducao
     */
    public float getDeducao(String nome) {
        for (int i = 0; i < nomesDeducoes.length; i++) {
            if (nomesDeducoes[i].toLowerCase().contains(nome.toLowerCase()))
                return valoresDeducoes[i];
        }
        return 0;
    }

    /**
     * Obtem o valor total de todas as deduções que nao sao do tipo
     * contribuicoes previdenciarias ou por dependentes
     *
     * @return valor total das outras deducoes
     */
    public float getTotalOutrasDeducoes() {
        float soma = 0;
        for (float f : valoresDeducoes) {
            soma += f;
        }
        return soma;
    }


    // NOVAS FUNCIONALIDADES PARA O TRABALHO

    /**
     * Calcula o valor total de deduções aplicáveis ao contribuinte, incluindo
     * dependentes, contribuições previdenciárias, pensões alimentícias e outras
     * deduções.
     *
     * @return valor total das deduções
     */
    public float getDeducaoTotal() {
        float total = 0;
        total += numDependentes * 189.59f;
        total += totalContribuicaoPrevidenciaria;
        total += totalPensaoAlimenticia;
        total += getTotalOutrasDeducoes();
        return total;

    }

    /**
     * Calcula a base de cálculo do imposto de renda com base nos rendimentos
     * tributáveis e deduções totais do contribuinte.
     */
    public void calcularBaseCalculo() {
        float rendimentosTributaveis = getTotalRendimentosTributaveis();
        float totalDeducoes = getDeducaoTotal();

        baseDeCalculo = rendimentosTributaveis - totalDeducoes;
    }

    /**
     * Retorna o valor final da base de cálculo após
     * feito o cálculo entre os rendimentos tributáveis
     * e as deduções
     *
     * @return valor da base de cálculo
     */
    public float getBaseDeCalculo() {
        calcularBaseCalculo();
        return baseDeCalculo;
    }

    /**
     * Calcula o valor de cada faixa do imposto de renda devido com base na tabela progressiva.
     */
    public void calcularImpostosPorFaixa() {
        float baseCalculo = getBaseDeCalculo();

        if (baseCalculo <= 2259.20) {
            impostosPorFaixa[0] = 0;
        }
        if (baseCalculo >= 2259.21) {
            impostosPorFaixa[1] = (Math.min(baseCalculo, 2826.65f) - 2259.20f) * 0.075f;
        }
        if (baseCalculo >= 2826.66) {
            impostosPorFaixa[2] = (Math.min(baseCalculo, 3751.05f) - 2826.66f) * 0.15f;
        }
        if (baseCalculo >= 3751.06) {
            impostosPorFaixa[3] = (Math.min(baseCalculo, 4664.68f) - 3751.06f) * 0.225f;
        }
        if (baseCalculo >= 4664.68) {
            impostosPorFaixa[4] = (baseCalculo - 4664.68f) * 0.275f;
        }
    }

    /**
     * Retorna os impostos de cada faixa da base de cálculo.
     *
     * @return array de impostos
     */
    public float[] getImpostosPorFaixa() {
        return impostosPorFaixa;
    }

    /**
     * Calcula o valor total do imposto de renda somando todos os valores por faixa.
     */
    public void calcularImpostoTotal() {
        for (float f : impostosPorFaixa) {
            impostoTotal += f;
        }
    }

    /**
     * Retorna os impostos por faixas somados.
     *
     * @return valor do imposto total.
     */
    public float getImpostoTotal() {
        return impostoTotal;
    }

    /**
     * Calcula a alíquota efetiva do imposto de renda com base nos rendimentos
     * tributáveis e no imposto devido.
     *
     * @param rendimentosTributaveis valor total dos rendimentos tributáveis
     * @param impostoDevido          valor do imposto devido
     * @return alíquota efetiva do imposto de renda
     */
    public void calcularAliquotaEfetiva(float rendimentosTributaveis, float impostoDevido) {
        if (rendimentosTributaveis == 0) {
            aliquotaEfetiva = 0; // Evitar divisão por zero
        } else {
            aliquotaEfetiva = (impostoDevido / rendimentosTributaveis) * 100;
        }
    }

    /**
     * Retorna o valor da alíquota efetiva calculada.
     *
     * @return valor da alíquota efetiva.
     */
    public float getAliquotaEfetiva() {
        return aliquotaEfetiva;
    }
}

