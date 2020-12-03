/**
 * @author Lucas Losinskas
 */

/*
 * Método de resolução de ajuste de curva parte do método
 */

import org.mariuszgromada.math.mxparser.*;

//==================================================================================================
// Inicio do método
public class AjusteDeCurvaClass {

    private double soma[][];
    private String texto;
    private String Funcao;
    private String latex;

    //==============================================================================================
    // Método da regressão
    public void regressao(int numero, int ordem, double m[][]) {

        //==========================================================================================
        // Variéveis
        texto = "";                             // Variável texto
        latex = "Ajuste de curvas polinômial";  // Variavel latex
        int k;
        double soma;
        double a[][] = new double[numero + 1][numero + 2];
        double mediaY;



        //==========================================================================================
        // Inicia o loop para somar
        for (int i = 0; i < ordem + 1; i++) {
            for (int j = 0; j < i + 1; j++) {
                k = i + j;
                soma = 0;

                //==================================================================================
                // loop da soma do x
                for (int l = 0; l < numero; l++) {
                    soma += Math.pow(m[l][0], k);
                }
                a[i][j] = soma;
                a[j][i] = soma;
            }

            if (i == 0) {
                a[i][i] = numero; // aii = n
            }

            soma = 0;             // Zera a soma
            for (int j = 0; j < numero; j++) {
                soma += m[j][1] * Math.pow(m[j][0], i);
            }
            a[i][ordem + 1] = soma;
        }
        this.soma = a;           // copia a matriz soma


        //==========================================================================================
        // Inicia a resolução do sistema linear
        EliminacaoClass eli = new EliminacaoClass();
        double mi[][] = new double[ordem + 1][ordem + 1];
        double x[] = new double[ordem + 1];

        for (int i = 0; i < ordem + 1; i++) {
            for (int j = 0; j < ordem + 1; j++) {
                mi[i][j] = a[i][j];
            }
            x[i] = a[i][ordem + 1];
        } //Resolve o sistema linear
        eli.eliminacao1(mi, ordem + 1, x);

        //==========================================================================================
        // Recupera os coeficientes
        double coeficientes[];
        coeficientes = eli.getX();

        //==========================================================================================
        // Matriz soma
        latex += "$$\\begin{bmatrix}";
        //==========================================================================================
        // Monta a matriz soma
        for (int i = 0; i < ordem + 1; i++) {
            for (int j = 0; j < ordem +1; j++) {
                if (i == 0 & j == 0) {
                    latex += "n = " + numero + "&";
                } else {
                    latex += "\\sum x^{" + (i + j) + "} = " + a[i][j] + "&";
                }

            }
            if (i != (numero - 1)) {
                latex += "\\\\";
            }
        }
        latex += "\\end{bmatrix} \\times \\begin{bmatrix}";

        for (int i = 0; i < ordem + 1; i++) {
            if (i == ordem) {
                latex += "a_{" + i + "} = " + coeficientes[i];
            } else {
                latex += "a_{" + i + "} = " + coeficientes[i] + "\\\\";
            }
        }
        latex += "\\end{bmatrix} = \\begin{bmatrix}";
        for (int i = 0; i < ordem + 1; i++) {
            if (i == ordem) {
                latex += "\\sum y\\cdot x^{" + i + "} = " + a[i][ordem + 1];
            } else {
                latex += "\\sum y\\cdot x^{" + i + "} = " + a[i][ordem + 1] + "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";

        //==========================================================================================
        // Escreve o polinomio
        latex += "$$f(x) = ";
        texto += "f(x) = ";
        for (int i = 0; i < ordem + 1; i++) {
            if (i == 0) {
                if (coeficientes[i] >= 0) {
                    this.Funcao = "" + Math.abs(coeficientes[i]);
                    this.texto += "" + Math.abs(coeficientes[i]);
                    this.latex += "" + Math.abs(coeficientes[i]);

                } else {
                    this.Funcao = "-" + Math.abs(coeficientes[i]);
                    this.texto += " - " + Math.abs(coeficientes[i]);
                    this.latex += " - " + Math.abs(coeficientes[i]);
                }
            } else {
                if (coeficientes[i] >= 0) {
                    this.Funcao += "+" + Math.abs(coeficientes[i]) + "*x^" + (i) + "";
                    this.latex += " + " + Math.abs(coeficientes[i]) + " \\cdot x^{" + (i) + "}";
                    this.texto += " + " + Math.abs(coeficientes[i]) + " * x^{" + (i) + "}";
                } else {
                    this.Funcao += "-" + Math.abs(coeficientes[i]) + "*x^" + (i) + "";
                    this.latex += " - " + Math.abs(coeficientes[i]) + " \\cdot x^{" + (i) + "}";
                    this.texto += " - " + Math.abs(coeficientes[i]) + " * x^{" + (i) + "}";
                }
            }

        }


        latex += "$$";
        Function f = new Function("f", Funcao, "x");
        double rQuadrado = 0;
        double desvio[] = new double[numero];

        //==========================================================================================
        // Média de y
        mediaY = 0;
        for (int i = 0; i < numero; i++) {
            mediaY += m[i][1];
        }
        mediaY = mediaY / numero;

        //==========================================================================================
        // Desvios
        double desvioTotal = 0;
        double r2;
        for (int i = 0; i < numero; i++) {
            desvio[i] = f.calculate(m[i][0]) - m[i][1];
            desvioTotal += Math.pow(m[i][1] - mediaY, 2);
            rQuadrado += Math.abs(Math.pow(desvio[i], 2));
        }
        r2 = 1 - (rQuadrado / desvioTotal);


        latex += "$$ \\begin{array}{|c|c|c|c|c|}";
        latex += "Pontos & x & y & y_{calculado}& desvio \\\\ \\\\";
        texto += "|Pontos \t|\t x \t|\t y \t|\t y_{calculado}\t|\t desvio \\\\";
        for (int i = 0; i < numero; i++) {
            latex += (i + 1) + "&" + m[i][0] + "&" + m[i][1] + "&" + f.calculate(m[i][0]) + "&" + Math.pow((m[i][1] - f.calculate(m[i][0])),2) + " \\\\";
            texto += (i + 1) + "&" + m[i][0] + "&" + m[i][1] + "&" + f.calculate(m[i][0]) + "&" + (m[i][1] - f.calculate(m[i][0]) + " \\\\");

        }
        latex += "\\end{array}$$";


        texto += "\nDesvio Total = " + rQuadrado;
        latex += "$$Desvio_{Total} = " + rQuadrado + "$$";
        latex += "$$r^2 = " + r2 + "$$";

    }

    private int pontos;

    //==============================================================================================
    //Metódo não linear
    public void AjusteNLinear(double[][] m) {

        //==========================================================================================
        // Variáveis de auxilio
        pontos = m.length;                  // Número de pontos
        double A[][] = new double[2][2];    // Matriz dos coeficientes
        double Y[] = new double[2];         // Vetor Y
        A[0][0] = pontos;                   // Inicia o ponto inicial da matriz com n

        latex = " Ajuste de curva não linear ";

        latex += "$$\\begin{bmatrix}";
        for (int i = 0; i < pontos; i++) {
            for (int j = 0; j < 2; j++) {
                switch (j) {
                    case 0:
                        latex += m[i][0] + "&";
                        break;
                    case 1:
                        latex += m[i][1];
                        break;
                }

            }
            if (i != pontos - 1) {
                latex += "\\\\";
            }
        }
        latex += "\\end{bmatrix}$$";

        latex += "Matriz";
        double soma = 0, somay = 0, soma2 = 0;
        for (int i = 0; i < 2; i++) {
            for (int k = 0; k < pontos; k++) {
                soma += m[k][0];

                if (i == 0) {
                    somay += Math.log(m[k][1]);

                } else {
                    somay += m[k][0] * Math.log(m[k][1]);
                    soma2 += Math.pow(m[k][0], 2);

                }
            }

            if (i == 0) {
                A[1][0] = soma;
                A[0][1] = soma;
            } else {
                A[1][1] = soma2;
            }


            Y[i] = somay;
            somay = 0;
            soma = 0;
        }

        latex += "$$\\begin{bmatrix}";
        latex += "n = " + A[0][0] + "& \\sum x_k = " + A[0][1] + "\\\\" +
                "\\sum x_k = " + A[1][0] + "&\\sum x^2 = " + A[1][1];
        latex += "\\end{bmatrix}\\times \\begin{bmatrix}" +
                "b \\\\ a" +
                " \\end{bmatrix}  = \\begin{bmatrix}" +
                "\\sum ln(y_k)=" + Y[0] + "\\\\" +
                "\\sum x_k \\cdot ln(y_k) = " + Y[1] +
                "\\end{bmatrix}$$";

        double pivo = -A[0][0] / A[1][0];
        double a = (Y[1] * pivo + Y[0]) / (pivo * A[1][1] + A[0][1]);
        double b = (Y[0] - A[0][1] * a) / A[0][0];
        latex += "Equação de ajuste:" +
                "$$f(x) = " + Math.exp(b) + "\\cdot e^{" + a + "x}$$";


        //==========================================================================================
        //desvio

        double rQuadrado = 0;
        double desvio[] = new double[pontos];

        //==========================================================================================
        // Média de y
        double mediaY = 0;
        for (int i = 0; i < pontos; i++) {
            mediaY += m[i][1];
        }
        mediaY = mediaY / pontos;

        //==========================================================================================
        // Desvios
        double desvioTotal = 0;
        double r2;
        for (int i = 0; i < pontos; i++) {
            desvio[i] = (Math.exp(b) * Math.exp(m[i][0] * a)) - m[i][1];
            desvioTotal += Math.pow(m[i][1] - mediaY, 2);
            rQuadrado += Math.abs(Math.pow(desvio[i], 2));
        }
        r2 = 1 - (rQuadrado / desvioTotal);


        latex += "$$ \\begin{array}{|c|c|c|c|c|}";
        latex += "Pontos & x & y & y_{calculado}& desvio \\\\ \\\\";
        texto += "|Pontos \t|\t x \t|\t y \t|\t y_{calculado}\t|\t desvio \\\\";
        for (int i = 0; i < pontos; i++) {
            latex += (i + 1) + "&" + m[i][0] + "&" + m[i][1] + "&" + Math.exp(b) * Math.exp(m[i][0] * a) + "&" + Math.pow((m[i][1] - Math.exp(b) * Math.exp(m[i][0] * a)),2) + " \\\\";
            texto += (i + 1) + "&" + m[i][0] + "&" + m[i][1] + "&" + Math.exp(b) * Math.exp(m[i][0] * a) + "&" + (m[i][1] - Math.exp(b) * Math.exp(m[i][0] * a)) + " \\\\";

        }
        latex += "\\end{array}$$";

        latex += "$$Desvio_{Total} = " + rQuadrado + "$$";
        latex += "$$r^2 = " + r2 + "$$";

    }

    //==============================================================================================
    //Linear
    public void AjusteLinear(double[][] m) {
        pontos = m.length;

        double soma = 0, somay = 0;
        double A[][] = new double[2][2];
        double Y[] = new double[2];
        A[0][0] = pontos;
        for (int i = 0; i < 2; i++) {


            for (int k = 0; k < pontos; k++) {
                soma += m[k][0];

                if (i == 0) {
                    somay = m[k][1];

                } else {
                    somay = m[k][1] * m[k][0];
                    soma += Math.pow(m[k][0], 2);

                }

            }
            if (i == 0) {
                A[1][0] = soma;
                A[0][1] = soma;
            } else {
                A[1][1] = soma;
            }


            Y[i] = somay;
            somay = 0;
            soma = 0;
        }
        EliminacaoClass eli = new EliminacaoClass();
        eli.eliminacao1(A, pontos, Y);
        double coe[];
        double coeEquacao[] = new double[2];
        coe = eli.getX();
        coeEquacao[0] = Math.exp(coe[0]);
        coeEquacao[1] = coe[1];

        //desvio
        double rQuadrado = 0;
        double desvio[] = new double[pontos];

        for (int i = 0; i < pontos; i++) {
            desvio[i] = coeEquacao[0] * Math.exp(coeEquacao[1] * m[i][0]) - m[i][1];
            rQuadrado += Math.abs(Math.pow(desvio[i], 2));
        }


    }

    public String getLatex() {
        return latex;
    }

    public void setLatex(String latex) {
        this.latex = latex;
    }

    public String getTexto() {
        return texto;
    }


    public double[][] getSoma() {
        return this.soma;
    }


    public String getFuncao() {
        return Funcao;
    }

    public void setFuncao(String Funcao) {
        this.Funcao = Funcao;
    }
}
