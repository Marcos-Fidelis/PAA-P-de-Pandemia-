import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AlgoritmoGenetico {


    public void copiaIndividuos(int[] a, int[] b, int[] c, int[] d) {
        for(int i = 0; i < a.length; i++) {
            c[i] = a[i];
            d[i] = b[i];
        }
    }

    public boolean verificaNovosIndividuos(ArrayList<int[]> individuos, int[][] brinquedos, int tempo) {
        int a[] = individuos.get(0);
        int b[] = individuos.get(1);

        int i = 0;

        int verifica_tempo_a = 0;
        int verifica_tempo_b = 0;

        while(i < a.length) {
            verifica_tempo_a = a[i]*brinquedos[i][1];
            verifica_tempo_b = b[i]*brinquedos[i][1];
            i++;
        }

        if(verifica_tempo_a > tempo || verifica_tempo_b > tempo) {
            return false;
        }

        return true;
    }

    /*
    public  ArrayList<int[]> mutação(ArrayList<int[]> filhos) {
        Random rand = new Random();
        boolean mutado = false;
        for(int i = 0; i < filhos.size(); i++) {
            int rolagem = rand.nextInt(101);
            if(rolagem >= 40) {
                while (!mutado) {
                    int mutagene1 = rand.nextInt(filhos.get(i).length + 1);
                    int mutagene2 = rand.nextInt(filhos.get(i).length + 1);
                    if (mutagene1 != mutagene2 && filhos.get(i)[mutagene1] < 0) {
                        filhos.get(i)[mutagene1] -= 1;
                        filhos.get(i)[mutagene2] += 1;
                        mutado = true;
                    }
                }
            }
        }
        return filhos;
    }
    * mantive ainda a função antiga caso a nova não funcione bem. caso funcione bem apagar esse comentario dps
    */

    // mutação acontece em cada indivíduo, acontece durante a criação de indivíduos
    public int[] mutação(int[] individuo) {
        Random rand = new Random();
        boolean mutado = false;
        int rolagem = rand.nextInt(101);
        int mutagene1, mutagene2;
        if (rolagem <= 40) {
            while (!mutado) {
                mutagene1 = rand.nextInt(individuo.length + 1);
                mutagene2 = rand.nextInt(individuo.length + 1);
                if (mutagene1 != mutagene2 && individuo[mutagene1] != 0) {
                    individuo[mutagene1] -= 1;
                    individuo[mutagene2] += 1;
                    mutado = true;
                }
            }
        }
        return individuo;
    }







    public ArrayList<int[]> crossover(int[] a, int[] b, int[][] brinquedos, int tempo, int crossoverTry) {
        Random aleatorizador = new Random();
        ArrayList<int[]> novosIndividuos = new ArrayList<int[]>();
        int numeroAleatorio = aleatorizador.nextInt(a.length);

        int c[] = new int[a.length];
        int d[] = new int[b.length];

        copiaIndividuos(a, b, c, d);

        for(int i = numeroAleatorio; i < a.length; i++) {
            int auxA = c[i];
            int auxB = d[i];
            //cada vez q um individuo é criado é rodada nele a probabilidade de mutação
            c[i] = auxB;
            c = mutação(c);
            d[i] = auxA;
            d = mutação(d);
        }

        novosIndividuos.add(c);
        novosIndividuos.add(d);

        if(verificaNovosIndividuos(novosIndividuos, brinquedos, tempo)) {
            return novosIndividuos;
        }
        else if(crossoverTry < 5){
            crossoverTry++;
            return crossover(a, b, brinquedos, tempo, crossoverTry);
        }
        else {
            return null;
        }

    }

    public int verificaAptidao (int[] individuo, int[][] elementos, int tempoMax) {
        int valor = 0;
        int peso = 0;
        for (int a = 0; a < individuo.length; a++) {
            peso += individuo[a]*elementos[a][0];
        }
        if (peso > tempoMax) {
            valor = 0;
        }
        else {
            for (int a = 0; a < individuo.length; a++) {
                valor += individuo[a]*elementos[a][1];
            }
        }
        return valor;
    }


    public static void main(String[] args) {

        Boolean fim = false; // usada para finalizar o programa

        // a classe Scanner é utilizada para ler informações passadas pelo usuário
        Scanner scanner = new Scanner(System.in);

        do {
            int n = scanner.nextInt(); // consegue o valor de N
            int t = scanner.nextInt(); // consegue o valor de T

            if (n >= 0 && n <= 100 && t >= 0 && t <=600)
            {
                if(n == 0)
                {
                    fim = true;
                }
                else
                {
                    //// Leitura dos dados

                    // [n][0] representa a duração de cada brinquedo
                    // [n][1] representa os pontos de cada brinquedo
                    int brinquedos[][] = new int[n][2];

                    Boolean parar = false;

                    // preenche o vetor de brinquedos
                    for(int i = 0; i < n; i++) {
                        int d = scanner.nextInt();
                        int p = scanner.nextInt();

                        if(d < 0 || d > 600 || p < 0 || p > 100)
                        {
                            parar = true;
                            break;
                        }
                        brinquedos[i][0] = d;
                        brinquedos[i][1] = p;
                    }

                    if(parar){
                        fim = true;
                        break;
                    }

                    System.out.println("\nValores do vetor (" + n + ", " + t + "):");
                    for(int i = 0; i < n; i++){
                        System.out.println(brinquedos[i][0] + " | " + brinquedos[i][1]);
                    }

                    //// Algoritmo genético

                    // Criar a população inicial
                    int população[][] = new int[100][n];

                    for(int i = 0; i < 100; i++){
                        for(int j = 0; j < n; j++){
                            Random rand = new Random();
                            população[i][j] = rand.nextInt(11); // Cada item pode aparecer de 0 a 10 vezes
                        }
                    }

                    //////////////////////////////
                    // Tudo que esse for faz é printar os 100 individuos que compoem a população inicial
                    for(int i = 0; i < 100; i++){
                        System.out.print((i+1) + " - [");
                        for(int j = 0; j < n; j++){
                            System.out.print(população[i][j] + ", ");
                        }
                        System.out.println("]");
                    }
                    //////////////////////////////

                    //// Entra no ciclo
                        // Aplica método de Seleção para encontrar os pais da próxima geração
                        // Utiliza os pais como parametros para os métodos de Crusamento e Mutação
                        // Verifica a condição de parada
                            // SIM: o programa encerra e retorna o melhor valor encontrado
                            // NÃO: o programa retorna para o inicio do ciclo
                }
            }
            else // caso os valores de N ou T estejam invalidos ele entra nesse else
            {
                // Eu assumi que caso ele leia uma valor invalido para N ou T o algoritmo simplesmente para
                fim = true;
            }
        }while(!fim);

        scanner.close();
    }
}
