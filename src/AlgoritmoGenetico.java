import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class AlgoritmoGenetico {
    public static void main(String[] args) {
        //// Variaveis usadas para alterar as configurações do algoritmo genetico
        int TAM_POPULACAO = 100;

        float aux = (float)(2 * TAM_POPULACAO/3);
        int N_PAIS_MAX = Math.round(aux);

        float mutation_chance = 0.10f;

        // usada para finalizar o programa
        boolean terminar_programa = false;

        // a classe Scanner eh utilizada para ler informações passadas pelo usuario
        Scanner scanner = new Scanner(System.in);

        int n_interacoes = 0;
        do {
            int n = scanner.nextInt(); // consegue o valor de N
            int t = scanner.nextInt(); // consegue o valor de T

            if (n >= 0 && n <= 100 && t >= 0 && t <=600)
            {
                if(n == 0)
                {
                    terminar_programa = true;
                }
                else
                {
                    //// Leitura dos dados

                    // [n][0] representa a duração de cada brinquedo
                    // [n][1] representa os pontos de cada brinquedo
                    int[][] brinquedos = new int[n][2];

                    boolean entrada_invalida = false;

                    // preenche o vetor de brinquedos
                    for(int i = 0; i < n; i++) {
                        int d = scanner.nextInt();
                        int p = scanner.nextInt();

                        if(d < 0 || d > 600 || p < 0 || p > 100)
                        {
                            entrada_invalida = true;
                            break;
                        }
                        brinquedos[i][0] = d;
                        brinquedos[i][1] = p;
                    }

                    if(entrada_invalida){
                        terminar_programa = true;
                        break;
                    }

                    System.out.println("\nValores do vetor (" + n + ", " + t + "):");
                    for(int i = 0; i < n; i++){
                        System.out.println(brinquedos[i][0] + " | " + brinquedos[i][1]);
                    }

                    //// Algoritmo genetico

                    // Criar a populacao inicial
                    ArrayList<Integer[]> populacao = new ArrayList<Integer[]>();

                    for(int i = 0; i < 100; i++){
                        Integer[] ind = new Integer[n];
                        for(int j = 0; j < n; j++){
                            Random rand = new Random();
                            ind[j] = rand.nextInt(11); // Cada brinquedo pode aparecer de 0 a 10 vezes
                        }
                        populacao.add(ind);
                    }

                    //// Entra no ciclo
                    boolean populacao_final = false; // usada para finalizar o ciclo

                    int num_interacoes = 0;

                    do {
                        num_interacoes++;

                        // Aplica metodo de Selecao para encontrar os pais da proxima geracao
                        ArrayList<Integer[]> pais = new ArrayList<Integer[]>();

                        int soma_fitness = 0;
                        int contador = 0;

                        for(int i = 0; i < TAM_POPULACAO; i++){
                            soma_fitness += Fitness(populacao.get(i));
                        }
                        for(int i = 0; i < TAM_POPULACAO; i++){
                            contador++;

                            if(contador > N_PAIS_MAX){
                                break;
                            }
                            else
                            {
                                float i_fitness = Fitness(populacao.get(i));
                                float i_prop = i_fitness/soma_fitness;

                                Random rand = new Random();
                                if(i_prop <= rand.nextFloat()  && i_prop != 0){
                                    pais.add(populacao.get(i));
                                }
                            }
                        }

                        // Utiliza os pais como parametros para os metodos de Crusamento e Mutacao
                        populacao.clear();

                        // funcao de crusamento
                        // funcao de mutacao

                        if(num_interacoes >= 10){
                            populacao_final = true;
                        }
                    }while(!populacao_final);

                    int[] melhor_ind = new int[n];

                    for(int i = 0; i < n; i++){
                        melhor_ind[i] = 0;
                    }

                    for(int i = 0; i < TAM_POPULACAO; i++){
                        int melhor_fit = Fitness(melhor_ind);
                        int new_fit = Fitness(populacao.get(i));

                        if(new_fit > melhor_fit){
                            for(int j = 0; j < n; j++)
                            {
                                melhor_ind[j] = populacao.get(i)[j];
                            }
                        }
                    }

                    System.out.println("Instancia " + n_interacoes);
                    System.out.println(Fitness(melhor_ind));
                    n_interacoes++;
                }
            }
            else // caso os valores de N ou T estejam invalidos ele entra nesse else
            {
                // Eu assumi que caso ele leia uma valor invalido para N ou T o algoritmo simplesmente para
                terminar_programa = true;
            }
        }while(!terminar_programa);

        scanner.close();
    }
}
