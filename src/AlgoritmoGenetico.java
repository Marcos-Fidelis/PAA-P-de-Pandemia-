import java.util.Scanner;

public class AlgoritmoGenetico {
    public static void main(String[] args) {
        //// Leitura dos dados

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
                        break;
                    }

                    System.out.println("\nValores do vetor (" + n + ", " + t + "):");
                    for(int i = 0; i < n; i++){
                        System.out.println(brinquedos[i][0] + " | " + brinquedos[i][1]);
                    }

                    // Algoritmo genético
                        // Criar a população inicial
                        // Entra no ciclo
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
