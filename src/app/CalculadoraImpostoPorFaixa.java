package app;

public class CalculadoraImpostoPorFaixa {
    private float baseCalculo;
    private float[] impostosPorFaixa;

    public CalculadoraImpostoPorFaixa(float baseCalculo) {
        this.baseCalculo = baseCalculo;
        this.impostosPorFaixa = new float[5];
    }

    public float[] calcularImpostosPorFaixa() {
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

        return impostosPorFaixa;
    }
}